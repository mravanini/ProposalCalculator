package com.amazon.proposalcalculator.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.amazon.proposalcalculator.bean.ConfigInput;
import com.amazon.proposalcalculator.bean.DataTransferInput;
import com.amazon.proposalcalculator.bean.InstanceInput;
import com.amazon.proposalcalculator.bean.InstanceOutput;
import com.amazon.proposalcalculator.bean.Price;
import com.amazon.proposalcalculator.bean.Quote;
import com.amazon.proposalcalculator.bean.S3Price;

public class Constants {
	
	public static List<Price> ec2PriceList;
	public static List<S3Price> s3PriceList;
	public static Collection<InstanceInput> servers;
	public static List<Quote> quotes = new ArrayList<Quote>();
	public static Collection<ConfigInput> configs;
	public static double HOURS_IN_A_MONTH = 730.4375f;
	public static double GIB_TO_GB = 1.073741824f;
	public static ConfigInput config;
	public static DataTransferInput dataTransfer;
	public static long beginTime;
	public static long endTime;

//	public static String INPUT_FILE_NAME = "input_generico.xlsx";
//	public static String OUTPUT_FILE_NAME = "output_generico.xlsx";

	public static String INPUT_FILE_NAME = "input_sap.xlsx";
	public static String OUTPUT_FILE_NAME = "output_sap.xlsx";

}
