package com.amazon.sample.reader;

import java.io.File;
import java.util.Collection;
import java.util.logging.Logger;

import com.amazon.sample.bean.Config;
import com.amazon.sample.bean.DefaultInput;
import com.amazon.sample.utils.Constants;
import com.ebay.xcelite.Xcelite;
import com.ebay.xcelite.reader.SheetReader;
import com.ebay.xcelite.sheet.XceliteSheet;

public class DefaultExcelReader {
	
	private final static Logger LOGGER = Logger.getLogger(DefaultExcelReader.class.getName());

	public static void main(String[] args) {
		new DefaultExcelReader().read();
	}

	public void read() {
		LOGGER.info("Reading input spreadsheet...");
		Xcelite xcelite = new Xcelite(new File("input3.xlsx"));
		
		XceliteSheet sheet = xcelite.getSheet("Servers");
		SheetReader<DefaultInput> reader = sheet.getBeanReader(DefaultInput.class);
		Collection<DefaultInput> servers = reader.read();
		Constants.servers = servers;
		
		sheet = xcelite.getSheet("Config");
		SheetReader<Config> configReader = sheet.getBeanReader(Config.class);
		Collection<Config> configs = configReader.read();
		Constants.config = configs.iterator().next();
		
	}

}
