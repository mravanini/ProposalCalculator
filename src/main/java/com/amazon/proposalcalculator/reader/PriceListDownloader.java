package com.amazon.proposalcalculator.reader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazon.proposalcalculator.calculator.RunOnServer;
import com.amazon.proposalcalculator.enums.ProductName;

/**
 * Created by ravanini on 29/11/16.
 */
public class PriceListDownloader {

	private final static Logger LOGGER = LogManager.getLogger();

	private static final String LATEST_PRICE_LIST = "https://pricing.us-east-1.amazonaws.com/offers/v1.0/aws/%s/current/index.csv";
	private static final String TESTED_PRICE_LIST = "https://s3-us-west-2.amazonaws.com/proposal-calculator-local/%s";

	private static final int READ_TIMEOUT = 5 * 60 * 1000;
	private static final int CONNECTION_TIMEOUT = 1 * 60 * 1000;

	public static FileReader download(String fileName, ProductName productName) throws IOException {
		LOGGER.info(String.format("Downloading price list for %s. This might take a while...", productName));

		URL website;

		if (Thread.currentThread().getName().contains(RunOnServer.class.getName())) {
			website = new URL(String.format(TESTED_PRICE_LIST, fileName));
			LOGGER.info("Downloading tested price list");
		} else {
			website = new URL(String.format(LATEST_PRICE_LIST, productName));
			LOGGER.info("Downloading latest price list");
		}

		File file = new File(fileName);
		FileUtils.copyURLToFile(website, file, CONNECTION_TIMEOUT, READ_TIMEOUT);
		LOGGER.info(String.format("Price list for %s downloaded.", productName));
		return new FileReader(file);
	}

}
