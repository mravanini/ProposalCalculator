package com.amazon.proposalcalculator.reader;

import com.amazon.proposalcalculator.bean.ConfigInput;
import com.amazon.proposalcalculator.utils.Constants;
import com.ebay.xcelite.Xcelite;
import com.ebay.xcelite.reader.SheetReader;
import com.ebay.xcelite.sheet.XceliteSheet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Collection;

public class ConfigReader {

	private final static Logger LOGGER = LogManager.getLogger();

	public static void main(String[] args) {
		new ConfigReader().read();
	}

	public static void read() {
		LOGGER.info("Reading input spreadsheet - tab Config...");
		Xcelite xcelite = new Xcelite(new File(Constants.INPUT_FILE_NAME));
		try {
			XceliteSheet sheet = xcelite.getSheet("Config");
			SheetReader<ConfigInput> reader = sheet.getBeanReader(ConfigInput.class);
			Collection<ConfigInput> config = reader.read();
			Constants.config = config.iterator().next();
		} catch (Exception e) {
			ConfigInput config = new ConfigInput();
			config.setCpuTolerance(10);
			config.setMemoryTolerance(10);
			config.setMatch("CPU and Memory");
			Constants.config = config;
		}
	}

}
