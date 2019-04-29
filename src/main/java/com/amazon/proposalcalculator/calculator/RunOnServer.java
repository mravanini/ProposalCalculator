package com.amazon.proposalcalculator.calculator;

import com.amazon.proposalcalculator.bean.InstanceInput;
import com.amazon.proposalcalculator.reader.*;
import com.amazon.proposalcalculator.utils.Constants;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ravanini on 02/12/16.
 */
public class RunOnServer {

	private static final String OUTPUT_FOLDER = "sap2_output/";
	private static final String INPUT_QUEUE = "sapqin";
	private static final int TIME_BETWEEN_READS = 2000;
	private final static Logger LOGGER = LogManager.getLogger();
	private static int exponentialBackoffTime = 2000;


	public static void main(String[] args) throws Exception {
		while (true) {
			try {
				AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.US_WEST_2).build();
				AmazonSQS sqsClient = AmazonSQSClientBuilder.standard().withRegion(Regions.US_WEST_2).build();

				// initialize
				readPriceLists(args);

				// get queue
				String myQueueUrl = sqsClient.getQueueUrl(INPUT_QUEUE).getQueueUrl();
				LOGGER.info("Receiving messages from " + INPUT_QUEUE);
				while (true) {
					Thread.sleep(TIME_BETWEEN_READS);

					ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(myQueueUrl);
					List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).getMessages();
					for (Message message : messages) {
						logMessage(message);
						processRecords(sqsClient, s3Client, myQueueUrl, message);
						// TODO shouldn't we delete sqs message here??

						// todo for testing purposes only
						Integer exceptionLocation = Integer.valueOf(System.getenv("WHERE_IS_EXCEPTION"));
						// LOGGER.warn("Exception location environment = " + exceptionLocation);
						if (exceptionLocation.intValue() == 1) {
							throw new Exception("test exception handling");
						}
						// end: todo for testing purposes only

					}
				}

			} catch (Exception e) {
				LOGGER.error(
						"Error trying to read price list. Trying again in " + exponentialBackoffTime/1000 + " second(s).");
				LOGGER.error("Error Message: " + e.getMessage(), e);
				Thread.sleep(exponentialBackoffTime);
				exponentialBackoffTime = exponentialBackoffTime * 2;
				SendSNSMessage.send(e);
			}
		}
	}

	private static void readPriceLists(String[] args) throws IOException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		Constants.beginTime = System.currentTimeMillis();

		//TODO remove forceDownload or will this be treated later?
		Boolean forceDownload;
		//forceDownload = ParseMainArguments.isForceDownload(args);
		forceDownload = false;

		EC2PriceListReader.read(forceDownload);
		S3PriceListReader.read(forceDownload);

		Constants.endTime = System.currentTimeMillis();
		LOGGER.info("Reading price lists done! Took " + (Constants.endTime - Constants.beginTime) / 1000 + " seconds!");
	}

	private static void processRecords(AmazonSQS sqsClient, AmazonS3 s3Client, String myQueueUrl, Message message)
			throws IOException {
		String messageReceiptHandle = message.getReceiptHandle();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readValue(message.getBody(), JsonNode.class);
		JsonNode records = rootNode.get("Records");
		for (JsonNode record : records) {

			// get file from S3
			String inputFileS3Key = URLDecoder.decode(record.get("s3").get("object").get("key").asText(), "UTF-8");
			String bucketName = record.get("s3").get("bucket").get("name").asText();
			LOGGER.info("S3 Key : " + inputFileS3Key);
			LOGGER.info("Bucket : " + bucketName);

			S3Object s3Object = s3Client.getObject(new GetObjectRequest(bucketName, inputFileS3Key));

			try {
				//todo for testing purposes only
				Integer exceptionLocation = Integer.valueOf(System.getenv("WHERE_IS_EXCEPTION"));
				//LOGGER.warn("Exception location environment = " + exceptionLocation);
				if(exceptionLocation.intValue() == 3){
					throw new Exception("test deleting message");
				}
				//end: todo for testing purposes only

				InputStream objectData = s3Object.getObjectContent();

				String[] splitKeyName = inputFileS3Key.split("/");
				String inputFileName = splitKeyName[splitKeyName.length - 1];
				File targetFile = new File(inputFileName);
				Files.copy(objectData, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
				IOUtils.closeQuietly(objectData);

				// read spreadsheets
				//Collection<InstanceInput> instanceInputs = DefaultExcelReader.read(inputFileName);
				Collection<InstanceInput> instanceInputs = POIExcelReader.read(inputFileName);
				DataTransferReader.read(inputFileName);
				ParametersReader.read(inputFileName);

				// calculate and delete message from SQS
				//String outputFileName = getCurrentTime() + "_" + Constants.OUTPUT_FILE_NAME;
				String outputFileName = inputFileName.replace(".xlsx", "") + "output_"+ getCurrentTime() + ".xlsx";
				
				Calculator.calculate(instanceInputs, outputFileName);
				sqsClient.deleteMessage(new DeleteMessageRequest(myQueueUrl, messageReceiptHandle));


				// put file back to S3
				putFileBackToS3(s3Client, bucketName, s3Object, outputFileName);

				//todo for testing purposes only
				//LOGGER.warn("Exception location environment = " + exceptionLocation);
				if(exceptionLocation.intValue() == 2){
					throw new Exception("test exception handling");
				}
				//end: todo for testing purposes only

			}catch (Exception e){

				LOGGER.warn("Deleting message from the queue: " + myQueueUrl + ", receiptHandle: " + messageReceiptHandle);

				sqsClient.deleteMessage(new DeleteMessageRequest(myQueueUrl, messageReceiptHandle));

				LOGGER.error("Error Message: " + e.getMessage(), e);

				SendSNSMessage.send(e, bucketName, inputFileS3Key, getEmailFromMetadata(s3Object.getObjectMetadata()));

				String outputFileNameWithError = writeErrorFile(e);

				// put file back to S3
				putFileBackToS3(s3Client, bucketName, s3Object, outputFileNameWithError);

			}
//			ObjectMetadata metadata = new ObjectMetadata();
//			metadata.addUserMetadata(METAKEY, meta);
		}
	}

	private static void putFileBackToS3(AmazonS3 s3Client, String bucketName, S3Object s3Object, String outputFileName) {
		PutObjectRequest putObjectRequest = getPutObjectRequest(bucketName, outputFileName);
		putObjectRequest.setMetadata(getObjectMetadata(s3Object.getObjectMetadata()));
		s3Client.putObject(putObjectRequest);
	}

	private static String getEmailFromMetadata(ObjectMetadata s3Object) {
		return s3Object.getUserMetaDataOf(Constants.METAKEY);

	}

	private static String writeErrorFile(Exception e) throws IOException {
		String outputFileNameWithError = getCurrentTime() + "_" + Constants.OUTPUT_WITH_ERROR_FILE_NAME;
		File outputFile = new File(outputFileNameWithError);
		FileWriter writer = new FileWriter(outputFile);
		writer.write(WriteStackTrace.buildMessageForCustomers(e));
		writer.close();
		return outputFileNameWithError;
	}

	private static String getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		return sdf.format(new Date());
	}

	private static PutObjectRequest getPutObjectRequest(String bucketName, String outputFileName) {
		File outputFile = new File(outputFileName);
		String outputFileS3Key = OUTPUT_FOLDER + outputFile.getName();
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, outputFileS3Key, outputFile);
		return putObjectRequest;
	}

	private static ObjectMetadata getObjectMetadata(ObjectMetadata objectMetadata) {
		ObjectMetadata metadata = new ObjectMetadata();
		String meta = getEmailFromMetadata(objectMetadata);
		if (meta == null) {
            meta = "no email configured in the input object";//TODO throw exception?
        }
		metadata.addUserMetadata(Constants.METAKEY, meta);
		return metadata;
	}

	private static void logMessage(Message message) {
		LOGGER.info("  Message");
		LOGGER.info("    MessageId:     " + message.getMessageId());
		LOGGER.info("    Body:          " + message.getBody());
	}

}
