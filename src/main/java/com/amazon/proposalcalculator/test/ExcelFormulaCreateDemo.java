package com.amazon.proposalcalculator.test;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * This program demonstrates creating an Excel document with a formula cell.
 * @author www.codejava.net
 *
 */
public class ExcelFormulaCreateDemo {

	public static void main(String[] args) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Java Books");
		
		Object[][] bookData = {
				{"Head First Java", "Kathy Serria", 79},
				{"Effective Java", "Joshua Bloch", 36},
				{"Clean Code", "Robert martin", 42},
				{"Thinking in Java", "Bruce Eckel", 35},
		};

		int rowCount = 0;
		
		for (Object[] aBook : bookData) {
			Row row = sheet.createRow(++rowCount);
			
			int columnCount = 0;
			
			for (Object field : aBook) {
				Cell cell = row.createCell(++columnCount);
				if (field instanceof String) {
					cell.setCellValue((String) field);
				} else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
				}
			}
			
		}
		
		Row rowTotal = sheet.createRow(rowCount + 2);
		Cell cellTotalText = rowTotal.createCell(2);
		cellTotalText.setCellValue("Total:");
		
		Cell cellTotal = rowTotal.createCell(3);
		cellTotal.setCellFormula("SUM(D2:D5)");
		
		try (FileOutputStream outputStream = new FileOutputStream("JavaBooks4BeginnerPrice.xlsx")) {
			workbook.write(outputStream);
			workbook.close();
		}
	}

}
