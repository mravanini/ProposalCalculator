package com.amazon.proposalcalculator.writer;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ebay.xcelite.Xcelite;
import com.ebay.xcelite.sheet.XceliteSheet;
import com.ebay.xcelite.writer.SheetWriter;

public class XliteTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Xcelite xcelite = new Xcelite();    
		XceliteSheet sheet = xcelite.createSheet("data_sheet");
		SheetWriter<Collection<Object>> simpleWriter = sheet.getSimpleWriter();
		List<Collection<Object>> data = new ArrayList<Collection<Object>>();
		
		ArrayList list1 = new ArrayList();
		list1.add("1");
		list1.add("2");
		
		ArrayList list2 = new ArrayList();
		list2.add("3");
		list2.add("4");
		
		data.add(list1);
		data.add(list2);
		
		// ...fill up data
		simpleWriter.write(data);   
		xcelite.write(new File("data.xlsx"));

	}

}
