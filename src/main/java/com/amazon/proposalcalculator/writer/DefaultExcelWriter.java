package com.amazon.proposalcalculator.writer;

import java.io.File;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.amazon.proposalcalculator.bean.DefaultOutput;
import com.amazon.proposalcalculator.bean.Quote;
import com.amazon.proposalcalculator.utils.Constants;
import com.ebay.xcelite.Xcelite;
import com.ebay.xcelite.sheet.XceliteSheet;
import com.ebay.xcelite.writer.SheetWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultExcelWriter {

	private final static Logger LOGGER = LogManager.getLogger();
	
	public static void write() {
		LOGGER.info("Writing output spreadsheet...");
		Xcelite xcelite = new Xcelite();
		
		
		XceliteSheet summarySheet = xcelite.createSheet("Summary");
		SheetWriter<Collection<Object>> simpleWriter = summarySheet.getSimpleWriter();
		List<Collection<Object>> data = new ArrayList<Collection<Object>>();
		
		List<Object> list1 = new ArrayList<Object>();
		list1.add("Payment");
		list1.add("Value");
		list1.add("Discount");
		data.add(list1);
		
		for (Quote quote : Constants.quotes) {
			List<Object> list2 = new ArrayList<Object>();
			list2.add(quote.getName());
			list2.add(round(quote.getValue(), 2));
			list2.add(round(quote.getDiscount(), 2) +"%");
			data.add(list2);
		}
		simpleWriter.write(data);   
		
		for (Quote quote : Constants.quotes) {
			XceliteSheet sheet = xcelite.createSheet(quote.getName());
			SheetWriter<DefaultOutput> writer = sheet.getBeanWriter(DefaultOutput.class);
			writer.write(quote.getOutput());
			LOGGER.info(quote.getName() + "-> Valor: " + quote.getValue() + "-> Desconto: " + quote.getDiscount());
		}
		
		xcelite.write(new File("output.xlsx"));
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

}
