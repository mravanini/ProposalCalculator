package com.amazon.proposalcalculator.reader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazon.proposalcalculator.bean.Price;
import com.amazon.proposalcalculator.enums.ProductName;
import com.amazon.proposalcalculator.utils.Constants;
import com.amazon.proposalcalculator.utils.SAPS;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

public class EC2PriceListReader {

	private final static Logger LOGGER = LogManager.getLogger();
	public static final String CSV_EC2_NAME = "indexEC2.csv";

	public static void main(String[] args) throws IOException, NoSuchMethodException, InstantiationException,
			IllegalAccessException, InvocationTargetException {
		long now = System.currentTimeMillis();
		read(false);
		LOGGER.info("Time to read: " + (System.currentTimeMillis() - now) + " milliseconds");

	}

	public static List<Price> read(Boolean forceDownload) throws IOException, InvocationTargetException,
			NoSuchMethodException, InstantiationException, IllegalAccessException {
		if (Constants.ec2PriceList != null) {
			return Constants.ec2PriceList;
		}

		HeaderColumnNameMappingStrategy<Price> strategy = new HeaderColumnNameMappingStrategy<Price>();
		strategy.setType(Price.class);
		CsvToBean<Price> csvToBean = new CsvToBean<Price>();
		CSVReader csvReader = createReader(forceDownload);

		LOGGER.info("Reading price list...");
		List<Price> beanList = csvToBean.parse(strategy, csvReader);

		// Generate standBy (0 vCPU, 0 GiB) instances
		beanList = StandByInstances.generate(beanList);

		List<Price> beanList2 = new ArrayList<Price>();

		for (Price price : beanList) {
			if (price.getInstanceType() != null) {
				Integer saps = SAPS.getInstance().getSAPS(price.getInstanceType());
				price.setSaps(saps);
			}

			if ("3 yr".equals(price.getLeaseContractLength())) {
				price.setLeaseContractLength("3yr");
			} else if ("1 yr".equals(price.getLeaseContractLength())) {
				price.setLeaseContractLength("1yr");
			}

			if ("NoUpfront".equals(price.getPurchaseOption())) {
				price.setPurchaseOption("No Upfront");
			} else if ("AllUpfront".equals(price.getPurchaseOption())) {
				price.setPurchaseOption("All Upfront");
			} else if ("PartialUpfront".equals(price.getPurchaseOption())) {
				price.setPurchaseOption("Partial Upfront");
			}

			// adicionando somente instancias...
			if ((price.getTermType() != null && price.getTermType().equals("OnDemand") && price.getPricePerUnit() > 0)
					|| (price.getTermType() != null && price.getTermType().equals("Reserved"))) {
				beanList2.add(price);

				if (false && price.getPricePerUnit() > 0 && price.getInstanceType() != null
						&& price.getLocation().startsWith("US West") && (price.getInstanceType().startsWith("u-6tb1")
								|| price.getInstanceType().startsWith("x1e.32"))) {
					LOGGER.info("Price tabajara: " + price.toString());
				}

			}

		}

		// System.exit(0);

		Constants.ec2PriceList = beanList2;

		LOGGER.info("EC2 Price List size: " + beanList.size() + " records");

		return beanList;
	}

	private static CSVReader createReader(Boolean forceDownload) throws IOException {

		FileReader fileReader;
		File file = new File(CSV_EC2_NAME);
		if (forceDownload || !file.isFile()) {// forceDownload or file does not exist
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
