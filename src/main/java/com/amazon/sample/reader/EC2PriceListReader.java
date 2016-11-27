package com.amazon.sample.reader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.amazon.sample.bean.Price;
import com.amazon.sample.utils.Constants;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import java.util.logging.Logger;

public class EC2PriceListReader {
	
	private final static Logger LOGGER = Logger.getLogger(EC2PriceListReader.class.getName());

	public static void main(String[] args) throws IOException {
		long now = System.currentTimeMillis();
		new EC2PriceListReader().read();
		LOGGER.info("Time to read: " + (System.currentTimeMillis() - now) + " miliseconds"); 
	}

	public List<Price> read() throws FileNotFoundException, IOException {
		LOGGER.info("Reading price list...");
		HeaderColumnNameMappingStrategy<Price> strategy = new HeaderColumnNameMappingStrategy<Price>();
		strategy.setType(Price.class);
		CsvToBean<Price> csvToBean = new CsvToBean<Price>();
		List<Price> beanList = csvToBean.parse(strategy, createReader());
		Constants.ec2PriceList = beanList;
		LOGGER.info("EC2 Price List size: " + beanList.size() + " records");
		return beanList;
	}

	private CSVReader createReader() throws FileNotFoundException, IOException {
		String csvFilename = "index.csv";
		CSVReader csvReader = new CSVReader(new FileReader(csvFilename), ',', '\"', 5);
		// CSVReader csvReader = new CSVReader(new FileReader(csvFilename));
		return csvReader;
	}

}
