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

public class Constants {
	
	public static List<Price> ec2PriceList;
	public static Collection<InstanceInput> servers;
	public static List<Quote> quotes = new ArrayList<Quote>();
	public static Collection<ConfigInput> configs;
	public static double HOURS_IN_A_MONTH = 730.4375f;
	public static ConfigInput config;
	public static DataTransferInput dataTransfer;
	public static long beginTime;
	public static long endTime;

}
