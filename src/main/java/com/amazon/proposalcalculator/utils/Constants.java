package com.amazon.proposalcalculator.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.amazon.proposalcalculator.bean.Config;
import com.amazon.proposalcalculator.bean.DefaultInput;
import com.amazon.proposalcalculator.bean.DefaultOutput;
import com.amazon.proposalcalculator.bean.Price;
import com.amazon.proposalcalculator.bean.Quote;

public class Constants {
	
	public static List<Price> ec2PriceList;
	public static Collection<DefaultInput> servers;
	public static List<Quote> quotes = new ArrayList<Quote>();
	public static Collection<Config> configs;
	public static double hoursInAMonth = 730.4375f;
	public static Config config;
	public static long beginTime;
	public static long endTime;

}
