package com.amazon.proposalcalculator.utils;

import java.util.List;

import com.amazon.proposalcalculator.bean.DataTransferInput;
import com.amazon.proposalcalculator.bean.Parameter;
import com.amazon.proposalcalculator.bean.Price;
import com.amazon.proposalcalculator.bean.S3Price;

public class Constants {

	public static List<Price> ec2PriceList;
	public static List<S3Price> s3PriceList;
	public static double HOURS_IN_A_MONTH = 730.4375f;
	public static double GIB_TO_GB = 1.073741824f;// TODO EQUALIZE GB TO GiB
	public static Parameter parameters;
	public static DataTransferInput dataTransfer;
	public static long beginTime;
	public static long endTime;
	public static String S3EndRange = "51200";
	public static long maxMemoryVM = 4100;

	public static String INPUT_FILE_NAME = "input_sap_test.xlsx";
	public static String OUTPUT_FILE_NAME = INPUT_FILE_NAME.split("\\.")[0] + "_output_" + ".xlsx";

	public static String OUTPUT_WITH_ERROR_FILE_NAME = "error_output.txt";
	public static String METAKEY = "sap";

	public static final String TOPIC_ARN = "arn:aws:sns:us-west-2:879125893843:proposal-calculator";

	public static final String STAND_BY_INSTANCE_TYPE = "standby_instance";
	public static final String STAND_BY_INSTANCE_TYPE_TEMPLATE = "c4.large";
	public static final String STAND_BY_INSTANCE_TENANCY_TEMPLATE = "Shared";
	public static final double DR_OPTIMIZED_PERCENTAGE = 0.25;
	public static final double DR_INACTIVE_PERCENTAGE = 0;

	public static final double MAX_MEMORY_WASTE = 1.5;
	public static final int MIN_HANA_CLUSTER_SIZE = 3;
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	public static final String UPFRONT_FEE = "Upfront Fee";

}
