package com.amazon.proposalcalculator.reader;

import java.io.File;
import java.util.Collection;

import com.amazon.proposalcalculator.bean.ConfigInput;
import com.amazon.proposalcalculator.bean.InstanceInput;
import com.amazon.proposalcalculator.utils.Constants;
import com.ebay.xcelite.Xcelite;
import com.ebay.xcelite.reader.SheetReader;
import com.ebay.xcelite.sheet.XceliteSheet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultExcelReader {

	private final static Logger LOGGER = LogManager.getLogger();

	public static void main(String[] args) {
		new DefaultExcelReader().read();
	}

	public static void read() {
		LOGGER.info("Reading input spreadsheet - tab Servers...");
		Xcelite xcelite = new Xcelite(new File("input_sap.xlsx"));
		
		XceliteSheet sheet = xcelite.getSheet("Servers");
		SheetReader<InstanceInput> reader = sheet.getBeanReader(InstanceInput.class);
		Collection<InstanceInput> servers = reader.read();
		Constants.servers = servers;
		
	}

}
