package com.amazon.proposalcalculator.calculator;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazon.proposalcalculator.enums.ProductName;
import com.amazon.proposalcalculator.reader.ConfigReader;
import com.amazon.proposalcalculator.reader.DataTransferReader;
import com.amazon.proposalcalculator.reader.DefaultExcelReader;
import com.amazon.proposalcalculator.reader.EC2PriceListReader;
import com.amazon.proposalcalculator.reader.ParseMainArguments;
import com.amazon.proposalcalculator.reader.S3PriceListReader;
import com.amazon.proposalcalculator.utils.Constants;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;

/**
 * Created by ravanini on 02/12/16.
 */
public class RunOnServer {

    private final static Logger LOGGER = LogManager.getLogger();

    public static void main2(String[] args) {
        try {
        	System.out.println(ProductName.AmazonS3.toString());
        	Constants.beginTime = System.currentTimeMillis();
            Boolean forceDownload;
            forceDownload = ParseMainArguments.isForceDownload(args);
            init(forceDownload);
            //Calculator.calculate();
            Constants.endTime = System.currentTimeMillis();
            LOGGER.info("Calculation done! Took " + (Constants.endTime - Constants.beginTime)/1000 + " seconds!");
        } catch (Exception e){
            LOGGER.fatal("A fatal error has occured: " , e);
            //System.err.println("An error has occured: " + e.getLocalizedMessage());
            System.exit(1);
        }
    }

    private static void init(Boolean forceDownload) throws IOException {
        EC2PriceListReader.read(forceDownload);
        S3PriceListReader.read(forceDownload);
        DefaultExcelReader.read();
        ConfigReader.read();
        DataTransferReader.read();
    }
    
    public static void main(String[] args) throws Exception {
    	main2(args);

        /*
         * The ProfileCredentialsProvider will return your [default]
         * credential profile by reading from the credentials file located at
         * (/Users/carvaa/.aws/credentials).
         */
        AWSCredentials credentials = null;
        try {
            credentials = new ProfileCredentialsProvider("default").getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (/Users/carvaa/.aws/credentials), and is in valid format.",
                    e);
        }

        AmazonSQS sqs = new AmazonSQSClient(credentials);
        Region usWest2 = Region.getRegion(Regions.US_WEST_2);
        sqs.setRegion(usWest2);

        System.out.println("===========================================");
        System.out.println("Getting Started with Amazon SQS");
        System.out.println("===========================================\n");

        try {
            // Create a queue
            //System.out.println("Creating a new SQS queue called MyQueue.\n");
            String myQueueUrl = sqs.getQueueUrl("MyQueue").getQueueUrl();
            
            // List queues
            System.out.println("Listing all queues in your account.\n");
            for (String queueUrl : sqs.listQueues().getQueueUrls()) {
                System.out.println("  QueueUrl: " + queueUrl);
            }
            System.out.println();

            
            while (true) {
            
	            // Receive messages
	            System.out.println("Receiving messages from MyQueue.\n");
	            ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(myQueueUrl);
	            List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
	            for (Message message : messages) {
	                System.out.println("  Message");
	                System.out.println("    MessageId:     " + message.getMessageId());
	                System.out.println("    ReceiptHandle: " + message.getReceiptHandle());
	                System.out.println("    MD5OfBody:     " + message.getMD5OfBody());
	                System.out.println("    Body:          " + message.getBody());
	                for (Entry<String, String> entry : message.getAttributes().entrySet()) {
	                    System.out.println("  Attribute");
	                    System.out.println("    Name:  " + entry.getKey());
	                    System.out.println("    Value: " + entry.getValue());
	                }
	                String messageReceiptHandle = message.getReceiptHandle();
	            	sqs.deleteMessage(new DeleteMessageRequest(myQueueUrl, messageReceiptHandle));
	            	Calculator.calculate();
	            }
	            System.out.println();
	
	            // Delete a message
	            /*if (messages.size() > 0) {
		            System.out.println("Deleting a message.\n");
	            	String messageReceiptHandle = messages.get(0).getReceiptHandle();
	            	sqs.deleteMessage(new DeleteMessageRequest(myQueueUrl, messageReceiptHandle));
	            }*/
	            
	            Thread.sleep(3000);
            }

        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it " +
                    "to Amazon SQS, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered " +
                    "a serious internal problem while trying to communicate with SQS, such as not " +
                    "being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
    }

}
