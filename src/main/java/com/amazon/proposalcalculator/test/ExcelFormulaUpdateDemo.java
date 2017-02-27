package com.amazon.proposalcalculator.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * This program demonstrates reading an Excel document, add a formula
 * to the first sheet, and then save the file. 
 * @author www.codejava.net
 *
 */
public class ExcelFormulaUpdateDemo {

	public static void main(String[] args) throws IOException {
		String excelFilePath = "JavaBooks4Beginner.xlsx";
		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet sheet = workbook.getSheetAt(0);

		sheet.getRow(8).getCell(2).setCellFormula("SUM(C5:C7) + SUM(C5:C7) * 0.1");
		
		inputStream.close();
		
		FileOutputStream outputStream = new FileOutputStream(excelFilePath);
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
	}

}
