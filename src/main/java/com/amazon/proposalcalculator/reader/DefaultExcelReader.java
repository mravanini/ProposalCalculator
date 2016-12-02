package com.amazon.proposalcalculator.reader;

import java.io.File;
import java.util.Collection;

import com.amazon.proposalcalculator.bean.Config;
import com.amazon.proposalcalculator.bean.DefaultInput;
import com.amazon.proposalcalculator.utils.Constants;
import com.ebay.xcelite.Xcelite;
import com.ebay.xcelite.reader.SheetReader;
import com.ebay.xcelite.sheet.XceliteSheet;
import org.apache.log4j.Logger;

public class DefaultExcelReader {

    private final static Logger LOGGER = Logger.getLogger(DefaultExcelReader.class);

	public static void main(String[] args) {
		new DefaultExcelReader().read();
	}

	public static void read() {
		LOGGER.info("Reading input spreadsheet - tab Servers...");
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
