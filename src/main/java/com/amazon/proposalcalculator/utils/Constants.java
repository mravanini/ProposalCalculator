package com.amazon.proposalcalculator.utils;

import com.amazon.proposalcalculator.bean.ConfigInput;
import com.amazon.proposalcalculator.bean.DataTransferInput;
import com.amazon.proposalcalculator.bean.Price;
import com.amazon.proposalcalculator.bean.S3Price;

import java.util.Collection;
import java.util.List;

public class Constants {
	
	public static List<Price> ec2PriceList;
	public static List<S3Price> s3PriceList;
	public static double HOURS_IN_A_MONTH = 730.4375f;
	public static double GIB_TO_GB = 1.073741824f;//TODO EQUALIZE GB TO GiB
	public static ConfigInput config;
	public static DataTransferInput dataTransfer;
	public static long beginTime;
	public static long endTime;
	public static String S3EndRange = "51200";
	//public static String INPUT_FILE_NAME = "/Users/carvaa/Documents/lixo/input_sap_test.xlsx";
	public static String INPUT_FILE_NAME = "input_sap.xlsx";
	public static String OUTPUT_FILE_NAME = "output_sap.xlsx";
	public static String OUTPUT_WITH_ERROR_FILE_NAME = "error_output.txt";
	public static String METAKEY = "sap";

	//public static final String TOPIC_ARN = "arn:aws:sns:us-west-2:126421527990:proposal-calculator";
	public static final String TOPIC_ARN = "arn:aws:sns:us-west-2:879125893843:proposal-calculator";

	public static final String STANDBY_INSTANCE = "standby_instance";
}
