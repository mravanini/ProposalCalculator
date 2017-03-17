package com.amazon.proposalcalculator.calculator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
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
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by ravanini on 02/12/16.
 */
public class RunOnServer {

	private static final String OUTPUT_FOLDER = "sap2_output/";
	private static final String INPUT_QUEUE = "sapqin";
	private static final int TIME_BETWEEN_READS = 2000;
	private final static Logger LOGGER = LogManager.getLogger();
	private static String METAKEY = "sap";

	public static void run(String[] args) {
		try {
			LOGGER.info(ProductName.AmazonS3.toString());
			Constants.beginTime = System.currentTimeMillis();
			Boolean forceDownload;
			//forceDownload = ParseMainArguments.isForceDownload(args);
            forceDownload = false;
			init(forceDownload);
			Constants.endTime = System.currentTimeMillis();
			LOGGER.info("Calculation done! Took " + (Constants.endTime - Constants.beginTime) / 1000 + " seconds!");
		} catch (Exception e) {
			LOGGER.fatal("A fatal error has occured: ", e.getLocalizedMessage());
			System.exit(1);
		}
	}

	private static void init(Boolean forceDownload) throws IOException {
		EC2PriceListReader.read(forceDownload);
		S3PriceListReader.read(forceDownload);
	}

	public static void main(String[] args) throws Exception {

		try {
			// initialize
			run(args);
			AmazonSQS sqsClient = AmazonSQSClientBuilder.standard().withRegion(Regions.US_WEST_2).build();
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.US_WEST_2).build();

			// get queue
			String myQueueUrl = sqsClient.getQueueUrl(INPUT_QUEUE).getQueueUrl();
			LOGGER.info("Receiving messages from " + INPUT_QUEUE);
			while (true) {
				Thread.sleep(TIME_BETWEEN_READS);
				try {
					// Receive messages
					//LOGGER.info("Receiving messages from " + INPUT_QUEUE);
					ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(myQueueUrl);
					List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).getMessages();
					for (Message message : messages) {
						logMessage(message);
						processRecords(sqsClient, s3Client, myQueueUrl, message);
					}
				} catch (Exception e) {
					LOGGER.info("Error Message: " + e.getMessage());
				}
			}

		} catch (Exception e) {
			LOGGER.info("Error Message: " + e.getMessage());
		}
	}

	private static void processRecords(AmazonSQS sqsClient, AmazonS3 s3Client, String myQueueUrl, Message message)
			throws IOException, JsonParseException, JsonMappingException {
		String messageReceiptHandle = message.getReceiptHandle();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readValue(message.getBody(), JsonNode.class);
		JsonNode records = rootNode.get("Records");
		for (JsonNode record : records) {
			long currentTimeMillis = System.currentTimeMillis();

			// get file from S3
			String inputFileS3Key = record.get("s3").get("object").get("key").asText();
			String bucketName = record.get("s3").get("bucket").get("name").asText();
			LOGGER.info("S3 Key : " + inputFileS3Key);
			LOGGER.info("Bucket : " + bucketName);
			S3Object object = s3Client.getObject(new GetObjectRequest(bucketName, inputFileS3Key));
			InputStream objectData = object.getObjectContent();

			String meta = object.getObjectMetadata().getUserMetaDataOf(METAKEY);
			if (meta == null) {
				meta = "no metadata on original file";
			}
			String[] splitedKeyName = inputFileS3Key.split("/");
			String inputFileName = splitedKeyName[splitedKeyName.length - 1];
			File targetFile = new File(inputFileName);
			Files.copy(objectData, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			IOUtils.closeQuietly(objectData);

			// read spreadsheets
			DefaultExcelReader.read();
			ConfigReader.read();
			DataTransferReader.read();

			// calculate and delete message from SQS
			String outputFileName = currentTimeMillis + "_" + Constants.OUTPUT_FILE_NAME;
			new Calculator().calculate(inputFileName, outputFileName);
			sqsClient.deleteMessage(new DeleteMessageRequest(myQueueUrl, messageReceiptHandle));

			// put file back to S3
			File outputFile = new File(outputFileName);
			String outputFileS3Key = OUTPUT_FOLDER + outputFile.getName();
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.addUserMetadata(METAKEY, meta);
			PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, outputFileS3Key, outputFile);
			putObjectRequest.setMetadata(metadata);
			s3Client.putObject(putObjectRequest);
		}
	}

	private static void logMessage(Message message) {
		LOGGER.info("  Message");
		LOGGER.info("    MessageId:     " + message.getMessageId());
		LOGGER.info("    Body:          " + message.getBody());
	}

}
