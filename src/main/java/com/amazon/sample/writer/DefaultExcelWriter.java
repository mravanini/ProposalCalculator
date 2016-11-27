package com.amazon.sample.writer;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.amazon.sample.bean.DefaultOutput;
import com.amazon.sample.bean.User;
import com.amazon.sample.reader.EC2PriceListReader;
import com.amazon.sample.utils.Constants;
import com.ebay.xcelite.Xcelite;
import com.ebay.xcelite.sheet.XceliteSheet;
import com.ebay.xcelite.writer.SheetWriter;

public class DefaultExcelWriter {
	
	private final static Logger LOGGER = Logger.getLogger(DefaultExcelWriter.class.getName());
	
	public static void write() {
		LOGGER.info("Writing output spreadsheet...");
		Xcelite xcelite = new Xcelite();
		XceliteSheet sheet = xcelite.createSheet("Result");
		SheetWriter<DefaultOutput> writer = sheet.getBeanWriter(DefaultOutput.class);
		// ...fill up users
		writer.write(Constants.output);
		// xcelite.write(new File("users_doc.xlsx"));
		xcelite.write(new File("output.xlsx"));
	}

}
