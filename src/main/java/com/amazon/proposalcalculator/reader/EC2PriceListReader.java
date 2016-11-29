package com.amazon.proposalcalculator.reader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.amazon.proposalcalculator.bean.Price;
import com.amazon.proposalcalculator.enums.ProductName;
import com.amazon.proposalcalculator.utils.Constants;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import java.util.logging.Logger;

public class EC2PriceListReader {
	
	private final static Logger LOGGER = Logger.getLogger(EC2PriceListReader.class.getName());
	private static final String CLASS_NAME = EC2PriceListReader.class.getName();
	public static final String CSV_EC2_NAME = "indexEC2.csv";



	public static void main(String[] args) {
		long now = System.currentTimeMillis();
		try {
			new EC2PriceListReader().read(false);
		}catch (IOException ioe){
			LOGGER.throwing(CLASS_NAME, "main", ioe);
		}
		LOGGER.info("Time to read: " + (System.currentTimeMillis() - now) + " miliseconds");

	}

	public List<Price> read(Boolean forceDownload) throws IOException {
		HeaderColumnNameMappingStrategy<Price> strategy = new HeaderColumnNameMappingStrategy<Price>();
		strategy.setType(Price.class);
		CsvToBean<Price> csvToBean = new CsvToBean<Price>();
		CSVReader csvReader = createReader(forceDownload);

		LOGGER.info("Reading price list...");
		List<Price> beanList = csvToBean.parse(strategy, csvReader);
		Constants.ec2PriceList = beanList;
		LOGGER.info("EC2 Price List size: " + beanList.size() + " records");

		return beanList;
	}

	private CSVReader createReader(Boolean forceDownload) throws IOException {

        FileReader fileReader = null;
	    File file = new File(CSV_EC2_NAME);

	    if(forceDownload || !file.isFile()){
            fileReader = PriceListDownloader.download(CSV_EC2_NAME, ProductName.AmazonEC2);
        }else {
            fileReader = new FileReader(CSV_EC2_NAME);
            LOGGER.fine("EC2 Price List already in folder. No need to download it");
        }

		CSVReader csvReader = new CSVReader(fileReader, ',', '\"', 5);
		// CSVReader csvReader = new CSVReader(new FileReader(csvFilename));
		return csvReader;
	}

}
