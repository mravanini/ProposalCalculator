package com.amazon.proposalcalculator.test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.amazon.proposalcalculator.bean.Price;
import com.amazon.proposalcalculator.utils.Constants;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

public class OpenCSVTest {

	public static void main(String[] args) throws IOException {
		// testCSV();
		testMapping();
	}

	private static List<Price> testMapping() throws FileNotFoundException, IOException {
		HeaderColumnNameMappingStrategy<Price> strategy = new HeaderColumnNameMappingStrategy<Price>();
		strategy.setType(Price.class);
		CsvToBean<Price> csvToBean = new CsvToBean<Price>();
		List<Price> beanList = csvToBean.parse(strategy, createReader());
		Constants.ec2PriceList = beanList;

		long now = System.currentTimeMillis();
		List<Price> nomesIniciadosE = beanList.stream()
				.filter(p -> p.getLocation() != null && p.getLocation().startsWith("South") && p.getCurrentGeneration().equals("Yes")
						&& p.getvCPU() == 2 && p.getMemory() == 8 && p.getTermType().equals("OnDemand")
						&& p.getTermType() != null && p.getOperatingSystem() != null && p.getOperatingSystem().equals("Linux")
						&& p.getTenancy() != null && p.getTenancy().equals("Shared"))
				.collect(Collectors.toList());
		nomesIniciadosE.forEach(p -> System.out.println(p));

		return beanList;
	}

	private static CSVReader createReader() throws FileNotFoundException, IOException {
		String csvFilename = "/Users/carvaa/Desktop/index.csv";
		CSVReader csvReader = new CSVReader(new FileReader(csvFilename), ',', '\"', 5);
		// CSVReader csvReader = new CSVReader(new FileReader(csvFilename));
		return csvReader;
	}

	private static void testCSV() throws FileNotFoundException, IOException {
		String csvFilename = "/Users/carvaa/Desktop/index.csv";
		CSVReader csvReader = new CSVReader(new FileReader(csvFilename));
		String[] row = null;
		int cont = 0;
		ArrayList<Price> prices = new ArrayList<Price>();
		while ((row = csvReader.readNext()) != null) {
			cont++;
			if (cont < 7) {
				continue;
			}

			Price price = new Price();
			price.setInstanceType(row[17]);
			prices.add(price);

			/*
			 * System.out.println(row[0] + " # " + row[1] + " #  " + row[2] );
			 */
			/*
			 * StringBuffer buffer = new StringBuffer(); for (String string :
			 * row) { buffer.append(string).append(" #  "); }
			 * System.out.println(buffer.toString());
			 */
			cont++;
			// if (cont==10)
			// break;
		}
		// ...
		System.out.println(prices.size());
		csvReader.close();
	}

}
