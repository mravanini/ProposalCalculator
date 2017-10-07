package com.amazon.proposalcalculator.reader;

import com.amazon.proposalcalculator.enums.ProductName;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

/**
 * Created by ravanini on 29/11/16.
 */
public class PriceListDownloader {

    private final static Logger LOGGER = LogManager.getLogger();
    
    //private static final String URL_PRICE_LIST = "https://pricing.us-east-1.amazonaws.com/offers/v1.0/aws/%s/current/index.csv";
    private static final String URL_PRICE_LIST = "https://s3-us-west-2.amazonaws.com/carvaasaporegon/pricelist/%s";
    
    private static final int READ_TIMEOUT = 5 * 60 * 1000;
    private static final int CONNECTION_TIMEOUT = 1 * 60 * 1000;
    
    //command: curl -O https://s3-us-west-2.amazonaws.com/carvaasaporegon/pricelist/indexEC2.csv
    //command: curl -O https://s3-us-west-2.amazonaws.com/carvaasaporegon/pricelist/indexS3.csv

    public static FileReader download(String fileName, ProductName productName) throws IOException {
        LOGGER.info(String.format("Downloading price list for %s. This might take a while...", productName));
        
        //URL website = new URL(String.format(URL_PRICE_LIST, productName));
        URL website = new URL(String.format(URL_PRICE_LIST, fileName));
        
        File file = new File(fileName);
        FileUtils.copyURLToFile(website, file, CONNECTION_TIMEOUT, READ_TIMEOUT);
        LOGGER.info(String.format("Price list for %s downloaded.", productName));
        return new FileReader(file);
    }
    
}
