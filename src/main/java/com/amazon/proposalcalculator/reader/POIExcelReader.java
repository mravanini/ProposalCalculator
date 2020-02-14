package com.amazon.proposalcalculator.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.amazon.proposalcalculator.bean.InstanceInput;
import com.amazon.proposalcalculator.enums.InstanceInputColumn;

public class POIExcelReader {

	private final static Logger LOGGER = LogManager.getLogger();

	public static Collection<InstanceInput> read(String inputFileName) throws IOException {

		LOGGER.info("Reading input spreadsheet - tab Servers...");

		FileInputStream inputStream = new FileInputStream(new File(inputFileName));

		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet sheet = workbook.getSheetAt(0);
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		Collection<InstanceInput> instanceInputs = new ArrayList<>();
		Boolean isSAPWorkbook = null;
		Map<Integer, InstanceInputColumn> columnsThatMatter = null;

		Iterator<Row> iterator = sheet.iterator();

		while (iterator.hasNext()) {

			if (isSAPWorkbook == null) {// first row
				Row firstRow = iterator.next();
				isSAPWorkbook = isSAPWorkbook(firstRow);
				columnsThatMatter = getColumnsThatMatter(firstRow);
			} else {
				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();

				InstanceInput instanceInput = new InstanceInput();

				while (cellIterator.hasNext()) {

					Cell cell = cellIterator.next();

					if (columnsThatMatter.containsKey(cell.getColumnIndex())) {

						InstanceInputColumn instanceInputColumn = columnsThatMatter.get(cell.getColumnIndex());

						switch (evaluator.evaluateInCell(cell).getCellType()) {
						// XSSFCell is for xlsx files
						case XSSFCell.CELL_TYPE_STRING:
							if (isSAPWorkbook) {
								instanceInput.setCellSAP(instanceInputColumn, cell.getStringCellValue(),
										currentRow.getRowNum());
							} else {
								instanceInput.setCellGeneric(instanceInputColumn, cell.getStringCellValue());
							}
							break;
						case XSSFCell.CELL_TYPE_NUMERIC:
							if (isSAPWorkbook) {
								instanceInput.setCellSAP(instanceInputColumn, cell.getNumericCellValue(),
										currentRow.getRowNum());
							} else {
								instanceInput.setCellGeneric(instanceInputColumn, cell.getNumericCellValue());
							}
							break;
						case XSSFCell.CELL_TYPE_BOOLEAN:
							if (isSAPWorkbook) {
								instanceInput.setCellSAP(instanceInputColumn, cell.getBooleanCellValue(),
										currentRow.getRowNum());
							} else {
								instanceInput.setCellGeneric(instanceInputColumn, cell.getBooleanCellValue());
							}
							break;
						case XSSFCell.CELL_TYPE_BLANK:
							break;
						// should never happen
						case XSSFCell.CELL_TYPE_FORMULA:
							throw new RuntimeException("cell formula should never happen");
						}
					}
				}
				instanceInputs.add(instanceInput);
			}
		}
		return instanceInputs;
	}

	private static boolean isSAPWorkbook(Row firstRow) {

		Iterator<Cell> cellIterator = firstRow.iterator();

		while (cellIterator.hasNext()) {
			Cell cell = cellIterator.next();

			if (cell.getStringCellValue().equalsIgnoreCase(InstanceInputColumn.SAPS.getColumnName()) || cell
					.getStringCellValue().equalsIgnoreCase(InstanceInputColumn.SAP_INSTANCE_TYPE.getColumnName())) {
				return true;
			}
		}
		return false;
	}

	private static Map<Integer, InstanceInputColumn> getColumnsThatMatter(Row firstRow) {

		Iterator<Cell> cellIterator = firstRow.iterator();
		Map<Integer, InstanceInputColumn> columnsThatMatter = new HashMap<>();

		while (cellIterator.hasNext()) {
			Cell cell = cellIterator.next();
			InstanceInputColumn instanceInputColumn = InstanceInputColumn.getColumnEnum(cell.getStringCellValue());
			if (instanceInputColumn != null) {
				columnsThatMatter.put(cell.getColumnIndex(), instanceInputColumn);
			}
		}
		return columnsThatMatter;
	}

}
