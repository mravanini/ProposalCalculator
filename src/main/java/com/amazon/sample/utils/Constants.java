package com.amazon.sample.utils;

import java.util.Collection;
import java.util.List;

import com.amazon.sample.bean.Config;
import com.amazon.sample.bean.DefaultInput;
import com.amazon.sample.bean.DefaultOutput;
import com.amazon.sample.bean.Price;

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
