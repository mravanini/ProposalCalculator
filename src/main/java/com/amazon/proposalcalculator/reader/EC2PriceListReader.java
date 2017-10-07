package com.amazon.proposalcalculator.reader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.amazon.proposalcalculator.bean.Price;
import com.amazon.proposalcalculator.enums.ProductName;
import com.amazon.proposalcalculator.utils.Constants;
import com.amazon.proposalcalculator.utils.SAPS;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class EC2PriceListReader {

	private final static Logger LOGGER = LogManager.getLogger();
	public static final String CSV_EC2_NAME = "indexEC2.csv";



	public static void main(String[] args) throws IOException {
		long now = System.currentTimeMillis();
        read(false);
		LOGGER.info("Time to read: " + (System.currentTimeMillis() - now) + " milliseconds");

	}

	public static List<Price> read(Boolean forceDownload) throws IOException {
		if (Constants.ec2PriceList != null) {
			return Constants.ec2PriceList;
		}
		
		HeaderColumnNameMappingStrategy<Price> strategy = new HeaderColumnNameMappingStrategy<Price>();
		strategy.setType(Price.class);
		CsvToBean<Price> csvToBean = new CsvToBean<Price>();
		CSVReader csvReader = createReader(forceDownload);

		LOGGER.info("Reading price list...");
		List<Price> beanList = csvToBean.parse(strategy, csvReader);
		Constants.ec2PriceList = beanList;
		for (Price price : beanList) {
			if (price.getInstanceType() != null) {
				price.setSaps(SAPS.getInstance().getSAPS(price.getInstanceType()));
			}
		}
		
		LOGGER.info("EC2 Price List size: " + beanList.size() + " records");

		return beanList;
	}

	private static CSVReader createReader(Boolean forceDownload) throws IOException {

        FileReader fileReader;
	    File file = new File(CSV_EC2_NAME);
        if (forceDownload || !file.isFile()) {//forceDownload or file does not exist
            fileReader = PriceListDownloader.download(CSV_EC2_NAME, ProductName.AmazonEC2);
        } else {
            fileReader = new FileReader(file);
            LOGGER.debug("EC2 Price List already in folder. No need to download it");
        }

        CSVReader csvReader = new CSVReader(fileReader, ',', '\"', 5);
		// CSVReader csvReader = new CSVReader(new FileReader(csvFilename));
		return csvReader;
	}

}
