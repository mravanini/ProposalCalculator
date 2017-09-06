package com.amazon.proposalcalculator.reader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.amazon.proposalcalculator.bean.Price;
import com.amazon.proposalcalculator.bean.S3Price;
import com.amazon.proposalcalculator.enums.ProductName;
import com.amazon.proposalcalculator.utils.Constants;
import com.amazon.proposalcalculator.utils.SAPS;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class S3PriceListReader {

	private final static Logger LOGGER = LogManager.getLogger();
	public static final String CSV_S3_NAME = "indexS3.csv";



	public static void main(String[] args) throws IOException {
		long now = System.currentTimeMillis();
        read(false);
		LOGGER.info("Time to read: " + (System.currentTimeMillis() - now) + " miliseconds");

	}

	public static List<S3Price> read(Boolean forceDownload) throws IOException {
		if (Constants.s3PriceList != null) {
			return Constants.s3PriceList;
		}
		HeaderColumnNameMappingStrategy<S3Price> strategy = new HeaderColumnNameMappingStrategy<S3Price>();
		strategy.setType(S3Price.class);
		CsvToBean<S3Price> csvToBean = new CsvToBean<S3Price>();
		CSVReader csvReader = createReader(forceDownload);

		LOGGER.info("Reading price list...");
		List<S3Price> beanList = csvToBean.parse(strategy, csvReader);
		Constants.s3PriceList = beanList;
		
		LOGGER.info("S3 Price List size: " + beanList.size() + " records");

		return beanList;
	}

	private static CSVReader createReader(Boolean forceDownload) throws IOException {

        FileReader fileReader;
	    File file = new File(CSV_S3_NAME);
        if (forceDownload || !file.isFile()) {//forceDownload or file does not exist
            fileReader = PriceListDownloader.download(CSV_S3_NAME, ProductName.AmazonS3);
        } else {
            fileReader = new FileReader(file);
            LOGGER.debug("S3 Price List already in folder. No need to download it");
        }

        CSVReader csvReader = new CSVReader(fileReader, ',', '\"', 5);
		// CSVReader csvReader = new CSVReader(new FileReader(csvFilename));
		return csvReader;
	}

}
