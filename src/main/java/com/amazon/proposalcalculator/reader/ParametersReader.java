package com.amazon.proposalcalculator.reader;

import java.io.File;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazon.proposalcalculator.bean.Parameter;
import com.amazon.proposalcalculator.utils.Constants;
import com.ebay.xcelite.Xcelite;
import com.ebay.xcelite.reader.SheetReader;
import com.ebay.xcelite.sheet.XceliteSheet;

public class ParametersReader {

	private final static Logger LOGGER = LogManager.getLogger();

	public static void main(String[] args) {
		new ParametersReader().read(Constants.INPUT_FILE_NAME);
	}

	public static void read(String inputFileName) {
		LOGGER.info("Reading input spreadsheet - tab Config...");
		Xcelite xcelite = new Xcelite(new File(inputFileName));
		try {
			XceliteSheet sheet = xcelite.getSheet("Parameters");
			SheetReader<Parameter> reader = sheet.getBeanReader(Parameter.class);
			Collection<Parameter> parameters = reader.read();
			Constants.parameters = parameters.iterator().next();
		} catch (Exception e) {
			Parameter parameters = new Parameter();
			Constants.parameters = parameters;
		}
	}

}
