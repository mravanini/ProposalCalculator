package com.amazon.proposalcalculator.reader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.amazon.proposalcalculator.bean.ConfigInput;
import com.amazon.proposalcalculator.bean.DataTransferInput;
import com.amazon.proposalcalculator.calculator.DataTransferPricingCalculator;
import com.amazon.proposalcalculator.utils.Constants;
import com.ebay.xcelite.Xcelite;
import com.ebay.xcelite.reader.SheetReader;
import com.ebay.xcelite.sheet.XceliteSheet;
import com.ebay.xcelite.writer.SheetWriter;

public class DataTransferReader {
	
	public static void main(String[] args) throws IOException {
		EC2PriceListReader.read(false);
		DataTransferReader.read();
		DataTransferPricingCalculator dataCalculator = new DataTransferPricingCalculator();
		dataCalculator.getDataTransferOutMonthlyPrice(Constants.dataTransfer);
	}
	
	public static void read() {
		Xcelite xcelite = new Xcelite(new File("input.xlsx"));
		XceliteSheet sheet = xcelite.getSheet("Data Transfer");
		SheetReader<Collection<Object>> simpleReader = sheet.getSimpleReader();
		Collection<Collection<Object>> data = simpleReader.read();
		
		DataTransferInput dataTransferInput = new DataTransferInput();
		
		String region = (String) ((ArrayList)data.toArray()[0]).toArray()[1];
		double dataTransferOut = (Double) ((ArrayList)data.toArray()[1]).toArray()[1];
		
		dataTransferInput.setDataTransferOut((long) dataTransferOut);
		dataTransferInput.setRegion(region);
		Constants.dataTransfer = dataTransferInput;
		
	}

}
