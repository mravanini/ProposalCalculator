package com.amazon.proposalcalculator.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * This program demonstrates evaluating a formula cell using the
 * FormulaEvaluator.evaluateFormulaCell(Cell) method.
 * @author www.codejava.net
 *
 */
public class ExcelFormulaEvaluatorDemo3 {

	public static void main(String[] args) throws IOException {
		String excelFilePath = "JavaBooks4Beginner.xlsx";
		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
		
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet sheet = workbook.getSheetAt(0);
		
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();		
		
		CellReference cellReference = new CellReference("C9");
		Row row = sheet.getRow(cellReference.getRow());
		Cell cell = row.getCell(cellReference.getCol());
		
		int cellType = evaluator.evaluateFormulaCell(cell);
		
		switch (cellType) {
			case Cell.CELL_TYPE_STRING:
				System.out.print(cell.getStringCellValue());
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				System.out.print(cell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_NUMERIC:
				System.out.print(cell.getNumericCellValue());
				break;
		}		
		
		
		workbook.close();
		inputStream.close();
	}

}
