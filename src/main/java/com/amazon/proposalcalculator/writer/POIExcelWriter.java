package com.amazon.proposalcalculator.writer;

import com.amazon.proposalcalculator.bean.InstanceOutput;
import com.amazon.proposalcalculator.bean.Quote;
import com.amazon.proposalcalculator.enums.LeaseContractLength;
import com.amazon.proposalcalculator.utils.SomeMath;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ravanini on 22/03/17.
 */
public class POIExcelWriter {

    private final static Logger LOGGER = LogManager.getLogger();
    public static final String ERRORS_FOUND = "Errors were found during the processing of this spreadsheet. Check the other sheets for " +
            "more details.";
    public static final int ERROR_MESSAGE_LINE = 14;

    private static List<Integer> percentageColumns;
    private static List<Integer> currencyColumns;

    private static CellStyle cellCurrencyStyle;

    private static XSSFCellStyle cellAlignCenterStyle;

    private static CellStyle percentageCellStyle;

    private static CellStyle cellDoubleStyle;

    static {
        percentageColumns = new ArrayList<>();
        percentageColumns.add(InstanceOutput.CPU_TOLERANCE);
        percentageColumns.add(InstanceOutput.MEMORY_TOLERANCE);

        currencyColumns = new ArrayList<>();
        currencyColumns.add(InstanceOutput.UPFRONT_FEE);
        currencyColumns.add(InstanceOutput.COMPUTE_UNIT_PRICE);
        currencyColumns.add(InstanceOutput.COMPUTE_MONTHLY_PRICE);
        currencyColumns.add(InstanceOutput.STORAGE_MONTHLY_PRICE);
        currencyColumns.add(InstanceOutput.SNAPSHOT_MONTHLY_PRICE);
        currencyColumns.add(InstanceOutput.ARCHIVE_LOGS_MONTHLY_PRICE);
        currencyColumns.add(InstanceOutput.S3_BACKUP_MONTHLY_PRICE);
    }


    public static void write(String outputFileName, List<Quote> quotes) throws IOException {
        LOGGER.info("Writing output spreadsheet...");

        setCellCurrencyStyle(null);
        setCellAlignCenterStyle(null);
        setPercentageCellStyle(null);
        setCellDoubleStyle(null);

        XSSFWorkbook workbook = new XSSFWorkbook();

        workbook = generateSummaryTab(quotes, workbook);

        workbook = generateQuoteTabs(quotes, workbook);
        
        try (FileOutputStream outputStream = new FileOutputStream(outputFileName)) {
            workbook.write(outputStream);
            workbook.close();
        }

    }

    private static XSSFWorkbook generateQuoteTabs(List<Quote> quotes, XSSFWorkbook workbook) {
        for (Quote quote : quotes) {
            XSSFSheet sheet = workbook.createSheet(quote.getName());

            int rowCount = 0;
            createTitleRow(workbook, sheet, rowCount, InstanceOutput.titles);

            Row row;
            for (InstanceOutput output : quote.getOutput()) {
                row = sheet.createRow(++rowCount);

                for (int columnCount = 0; columnCount < InstanceOutput.getColumnCount(); columnCount++) {

                    Object cellValue = output.getCell(columnCount);

                    if (cellValue instanceof String) {

                        setCell(row, columnCount, (String) cellValue);

                    }else if(cellValue instanceof Double){

                        if (isPercentageColumn(columnCount)){
                            setCellPercentage(row, columnCount, (Double) cellValue, workbook);
                        }else if (isCurrencyColumn(columnCount)){
                            setCellCurrency(row, columnCount, (Double) cellValue, workbook);
                        }else{
                            setCell(row,columnCount, (Double) cellValue);
                        }

                    }else if (cellValue instanceof Integer){

                        setCell(row, columnCount, (Integer) cellValue);//TODO ELE ESTÁ USANDO O SET CELL DE DOUBLE. VER O QUE DARÁ ISTO AQUI

                    }
                }
            }

            autoSizeColumns(sheet);

        }
        return workbook;
    }

    private static XSSFWorkbook generateSummaryTab(List<Quote> quotes, XSSFWorkbook workbook) {

        XSSFSheet sheet = workbook.createSheet(" Summary");

        String[] titles = {
                "Payment", "1yr Upfront", "3yr Upfront", "Monthly", "1yr Upfront Support", "3yr Upfront Support", "Monthly Support",  "3 Years Total", "Discount"
        };

        int rowCount = 0;

        createTitleRow(workbook, sheet, rowCount, titles);

        Row row;
        boolean hasErrors = Boolean.FALSE;

        for (Quote quote : quotes) {
            row = sheet.createRow(++rowCount);
            int columnCount = -1;

            setCell(row, ++columnCount, quote.getName());

            /*if (LeaseContractLength.ONE_YEAR.getColumnName().equals(quote.getLeaseContractLength())) {
                setCellCurrency(row, ++columnCount, SomeMath.round(quote.getUpfront(), 2), workbook);
                setCellCurrency(row, ++columnCount, 0, workbook);

            } else if (LeaseContractLength.THREE_YEARS.getColumnName().equals(quote.getLeaseContractLength())) {
                setCellCurrency(row, ++columnCount, 0, workbook);
                setCellCurrency(row, ++columnCount, SomeMath.round(quote.getUpfront(), 2), workbook);

            } else {
                setCellCurrency(row, ++columnCount, 0, workbook);
                setCellCurrency(row, ++columnCount, 0, workbook);

            }*/
            
            setCellCurrency(row, ++columnCount, SomeMath.round(quote.getOneYrUpfront(), 2), workbook);
            setCellCurrency(row, ++columnCount, SomeMath.round(quote.getThreeYrsUpfront(), 2), workbook);

            //setCellFormula(row, ++columnCount, quote.getMonthlyFormula(), workbook); //testing

            setCellCurrency(row, ++columnCount, SomeMath.round(quote.getMonthly(), 2), workbook);
            
            setCellCurrency(row, ++columnCount, SomeMath.round(quote.getOneYrUpfrontSupport(), 2), workbook);
            setCellCurrency(row, ++columnCount, SomeMath.round(quote.getThreeYrsUpfrontSupport(), 2), workbook);
            setCellCurrency(row, ++columnCount, SomeMath.round(quote.getMonthlySupport(), 2), workbook);
            
            setCellCurrency(row, ++columnCount, SomeMath.round(quote.getThreeYearTotal(), 2), workbook);
            setCellPercentage(row, ++columnCount, SomeMath.round(quote.getDiscount(), 4), workbook);

            if(quote.hasErrors()){
                hasErrors = Boolean.TRUE;
            }
        }

        if (hasErrors){
            printErrorMessage(workbook, sheet);
        }

        autoSizeColumns(sheet);

        return workbook;
    }

    private static void printErrorMessage(XSSFWorkbook workbook, XSSFSheet sheet) {

        XSSFFont bold = createFontBoldRed(workbook);

        Row row = sheet.createRow(ERROR_MESSAGE_LINE);
        sheet.addMergedRegion(new CellRangeAddress(ERROR_MESSAGE_LINE,ERROR_MESSAGE_LINE,0,6));

        Cell cell = row.createCell(0);

        cell.setCellValue(setFont(bold, ERRORS_FOUND));
    }


    private static boolean isCurrencyColumn(int columnCount) {
        if (currencyColumns.contains(columnCount)){
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    private static boolean isPercentageColumn(int columnCount) {

        if (percentageColumns.contains(columnCount)){
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    private static void autoSizeColumns(XSSFSheet sheet) {
        for (int x = 0; x < sheet.getRow(0).getPhysicalNumberOfCells(); x++) {
            sheet.autoSizeColumn(x);
        }
    }

    private static Row createTitleRow(XSSFWorkbook workbook, XSSFSheet sheet, int rowCount, String[] titles) {

        XSSFFont bold = createFontBold(workbook);

        Row row = sheet.createRow(rowCount);

        for (int i = 0; i < titles.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(alignCenter(workbook));
            cell.setCellValue(setFont(bold, titles[i]));
        }
        return row;
    }

    private static CellStyle alignCenter(XSSFWorkbook workbook) {
        if(cellAlignCenterStyle == null) {
            cellAlignCenterStyle = workbook.createCellStyle();
            cellAlignCenterStyle.setAlignment(HorizontalAlignment.CENTER);
        }
        return cellAlignCenterStyle;
    }

    private static void setCellPercentage(Row row, int columnCount, double rowValue, XSSFWorkbook workbook) {

        Cell cell = setCell(row, columnCount, rowValue);

        setCellPercentageStyle(cell, workbook);
    }

    private static void setCellPercentageStyle(Cell cell, XSSFWorkbook workbook) {

        if(percentageCellStyle == null) {
            percentageCellStyle = workbook.createCellStyle();
            percentageCellStyle.setDataFormat(workbook.createDataFormat().getFormat("#0%"));
        }
        cell.setCellStyle(percentageCellStyle);
    }

    private static XSSFFont createFontBold(XSSFWorkbook workbook) {
        XSSFFont bold = workbook.createFont();
        bold.setBold(Boolean.TRUE);
        return bold;
    }

    private static XSSFFont createFontBoldRed(XSSFWorkbook workbook) {
        XSSFFont boldRed = workbook.createFont();
        boldRed.setColor(HSSFColor.RED.index);
        boldRed.setBold(Boolean.TRUE);
        return boldRed;
    }



    private static XSSFRichTextString setFont(XSSFFont font, String text) {
        XSSFRichTextString richTextString = new XSSFRichTextString(text);
        richTextString.applyFont(font);
        return richTextString;
    }

    private static Cell setCell(Row row, int columnCount, String rowValue) {
        Cell cell = row.createCell(columnCount);

        cell.setCellValue(rowValue);

        return cell;
    }

    private static Cell setCell(Row row, int columnCount, double rowValue) {
        Cell cell = row.createCell(columnCount);

        cell.setCellValue(rowValue);

        return cell;
    }

    private static void setCellCurrency(Row row, int columnCount, double rowValue, XSSFWorkbook workbook) {

        Cell cell = setCell(row, columnCount, rowValue);

        cell.setCellStyle(getCurrencyCellStyle(workbook));
    }

    private static CellStyle getCurrencyCellStyle(Workbook workbook){

        if(cellCurrencyStyle == null) {
            cellCurrencyStyle = workbook.createCellStyle();

            CreationHelper ch = workbook.getCreationHelper();
            cellCurrencyStyle.setDataFormat(ch.createDataFormat().getFormat("$#,#0.00"));
        }
        return cellCurrencyStyle;
    }


//    private void setCellDecimalFormat(Row row, int columnCount, double rowValue, XSSFWorkbook workbook) {
//
//        Cell cell = row.createCell(columnCount);
//
//        CellStyle cellDoubleStyle = getCellDecimalFormatStyle(workbook);
//
//        cell.setCellValue(rowValue);
//        cell.setCellStyle(cellDoubleStyle);
//    }

    private static void setCellFormula(Row row, int columnCount, String rowValue, XSSFWorkbook workbook) {
        Cell cell = row.createCell(columnCount);

        CellStyle cellDoubleStyle = getCellDecimalFormatStyle(workbook);

        cell.setCellFormula(rowValue);
        cell.setCellStyle(cellDoubleStyle);
    }

    private static CellStyle getCellDecimalFormatStyle(XSSFWorkbook workbook) {
        if(cellDoubleStyle == null){
            cellDoubleStyle = workbook.createCellStyle();
            cellDoubleStyle.setDataFormat(
                    workbook.getCreationHelper().createDataFormat().getFormat("#############0.00"));
        }
        return cellDoubleStyle;
    }

    public static void setCellAlignCenterStyle(XSSFCellStyle cellAlignCenterStyle) {
        POIExcelWriter.cellAlignCenterStyle = cellAlignCenterStyle;
    }

    public static void setCellCurrencyStyle(CellStyle cellCurrencyStyle) {
        POIExcelWriter.cellCurrencyStyle = cellCurrencyStyle;
    }

    public static void setCellDoubleStyle(CellStyle cellDoubleStyle) {
        POIExcelWriter.cellDoubleStyle = cellDoubleStyle;
    }

    public static void setPercentageCellStyle(CellStyle percentageCellStyle) {
        POIExcelWriter.percentageCellStyle = percentageCellStyle;
    }
}
