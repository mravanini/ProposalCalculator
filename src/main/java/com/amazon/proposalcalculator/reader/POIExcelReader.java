package com.amazon.proposalcalculator.reader;

import com.amazon.proposalcalculator.bean.InstanceInput;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static com.amazon.proposalcalculator.bean.InstanceInput.SAP_INSTANCE_TYPE;
import static com.amazon.proposalcalculator.bean.InstanceInput.SAPS;

public class POIExcelReader {

	private final static Logger LOGGER = LogManager.getLogger();

	//public static void main(String[] args) {
	//	new POIExcelReader().read(Constants.INPUT_FILE_NAME);
	//}

	public static Collection<InstanceInput> read(String inputFileName) throws IOException {

		LOGGER.info("Reading input spreadsheet - tab Servers...");

		FileInputStream inputStream = new FileInputStream(new File(inputFileName));

		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet sheet = workbook.getSheetAt(0);
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		Collection<InstanceInput> instanceInputs = new ArrayList<>();
		boolean firstRow = true;
		Boolean isSAPWorkbook = null;

		Iterator<Row> iterator = sheet.iterator();

		while (iterator.hasNext()) {

			if (firstRow) {
				firstRow = false;
				isSAPWorkbook = isSAPWorkbook(iterator.next());
			} else {
				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();

				InstanceInput instanceInput = new InstanceInput();

				while (cellIterator.hasNext()) {

					Cell cell = cellIterator.next();

					switch (evaluator.evaluateInCell(cell).getCellType()) {
						//XSSFCell is for xlsx files
						case XSSFCell.CELL_TYPE_STRING:
							if (isSAPWorkbook) {
								instanceInput.setCellSAP(cell.getColumnIndex(), cell
										.getStringCellValue());
							} else {
								instanceInput.setCellGeneric(cell.getColumnIndex(), cell.getStringCellValue());
							}
							break;
						case XSSFCell.CELL_TYPE_NUMERIC:
							if (isSAPWorkbook) {
								instanceInput.setCellSAP(cell.getColumnIndex(), cell
										.getNumericCellValue());
							} else {
								instanceInput.setCellGeneric(cell.getColumnIndex(), cell.getNumericCellValue());
							}
							break;
						case XSSFCell.CELL_TYPE_BOOLEAN:
							if (isSAPWorkbook) {
								instanceInput.setCellSAP(cell.getColumnIndex(), cell
										.getBooleanCellValue());
							} else {
								instanceInput.setCellGeneric(cell.getColumnIndex(), cell.getBooleanCellValue());
							}
							break;
						case XSSFCell.CELL_TYPE_BLANK:
							break;
						//should never happen
						case XSSFCell.CELL_TYPE_FORMULA:
							throw new RuntimeException("cell formula should never happen");
					}
				}
				instanceInputs.add(instanceInput);
			}
		}
		return instanceInputs;
	}

	private static boolean isSAPWorkbook(Row firstRow){

		Iterator<Cell> cellIterator = firstRow.iterator();

		while (cellIterator.hasNext()) {
			Cell cell = cellIterator.next();

			if(cell.getStringCellValue().equalsIgnoreCase(SAPS)||
					cell.getStringCellValue().equalsIgnoreCase(SAP_INSTANCE_TYPE)){
				return true;
			}
		}
		return false;
	}

}
