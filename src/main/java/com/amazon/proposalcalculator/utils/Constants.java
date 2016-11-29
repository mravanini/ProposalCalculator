package com.amazon.proposalcalculator.utils;

import java.util.Collection;
import java.util.List;

import com.amazon.proposalcalculator.bean.Config;
import com.amazon.proposalcalculator.bean.DefaultInput;
import com.amazon.proposalcalculator.bean.DefaultOutput;
import com.amazon.proposalcalculator.bean.Price;

public class Constants {
	
	public static List<Price> ec2PriceList;
	public static Collection<DefaultInput> servers;
	public static Collection<DefaultOutput> output;
	public static Collection<Config> configs;
	//public static float hoursInAMonth = 730.5f;
	public static float hoursInAMonth = 744;
	//public static float cpuTolerance = 10;
	//public static float memoryTolerance = 10;
	public static Config config;

}
