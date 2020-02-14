package com.amazon.proposalcalculator.calculator;

import com.amazon.proposalcalculator.utils.Constants;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;

/**
 * Created by ravanini on 25/04/17.
 */
public class SendSNSMessage {

	public static void send(Exception e) {

		AmazonSNS sqsClient = AmazonSNSClientBuilder.standard().withRegion(Regions.US_WEST_2).build();

		sqsClient.publish(Constants.TOPIC_ARN, WriteStackTrace.buildMessageForAdministrators(e));
	}

	public static void send(Exception e, String bucketName, String inputFileS3Key, String emailFromMetadata) {

		AmazonSNS sqsClient = AmazonSNSClientBuilder.standard().withRegion(Regions.US_WEST_2).build();

		sqsClient.publish(Constants.TOPIC_ARN,
				WriteStackTrace.buildMessageForAdministrators(e, bucketName, inputFileS3Key, emailFromMetadata));
	}
}
