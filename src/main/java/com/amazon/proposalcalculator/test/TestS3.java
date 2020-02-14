package com.amazon.proposalcalculator.test;

import java.io.File;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class TestS3 {

	public static void main(String[] args) {
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.US_WEST_2).build();

		long currentTimeMillis = System.currentTimeMillis();
		String outputFileName = "input_sap.xlsx";
		File outputFile = new File(outputFileName);
		String outputFileS3Key = "sap2/" + outputFile.getName();
		ObjectMetadata metadata = new ObjectMetadata();

		metadata.addUserMetadata("sap", "carvaa@amazon.com");
		String bucketName = "amitkshsaporegon";
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, outputFileS3Key, outputFile);
		putObjectRequest.setMetadata(metadata);
		s3Client.putObject(putObjectRequest);

	}

}
