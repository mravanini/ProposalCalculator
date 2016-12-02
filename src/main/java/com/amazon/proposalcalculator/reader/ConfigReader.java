package com.amazon.proposalcalculator.reader;

import com.amazon.proposalcalculator.bean.Config;
import com.amazon.proposalcalculator.utils.Constants;
import com.ebay.xcelite.Xcelite;
import com.ebay.xcelite.reader.SheetReader;
import com.ebay.xcelite.sheet.XceliteSheet;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Collection;

public class ConfigReader {

    private final static Logger LOGGER = Logger.getLogger(ConfigReader.class);

	public static void main(String[] args) {
		new ConfigReader().read();
	}

	public static void read() {
		LOGGER.info("Reading input spreadsheet - tab Config...");
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
