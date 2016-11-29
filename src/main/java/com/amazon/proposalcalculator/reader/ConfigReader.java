package com.amazon.proposalcalculator.reader;

import java.io.File;
import java.util.Collection;
import java.util.logging.Logger;

import com.amazon.proposalcalculator.bean.Config;
import com.amazon.proposalcalculator.utils.Constants;
import com.ebay.xcelite.Xcelite;
import com.ebay.xcelite.reader.SheetReader;
import com.ebay.xcelite.sheet.XceliteSheet;

public class ConfigReader {
	
	private final static Logger LOGGER = Logger.getLogger(ConfigReader.class.getName());

	public static void main(String[] args) {
		new ConfigReader().read();
	}

	public void read() {
		LOGGER.info("Reading input spreadsheet...");
		Xcelite xcelite = new Xcelite(new File("input3.xlsx"));
		XceliteSheet sheet = xcelite.getSheet("Config");
		SheetReader<Config> reader = sheet.getBeanReader(Config.class);
		Collection<Config> config = reader.read();
		Constants.config = config.iterator().next();
		//for (DefaultInput input : servers) {
			//System.out.println(input.getDescription());
		//}
	}

}
