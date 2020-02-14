package com.amazon.proposalcalculator.calculator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazon.proposalcalculator.bean.InstanceOutput;
import com.amazon.proposalcalculator.bean.Quote;

/**
 * Created by ravanini on 17/03/17.
 */
public class CalculatorUsingFormula {

	private final static Logger LOGGER = LogManager.getLogger();

	private static final int THREE_YR_DURATION = 36;
	private static final int ONE_YR_DURATION = 12;
	private static final String BEGIN_FORMULA = "(";
	private static final String END_FORMULA = ")";
	private static final String PLUS_SIGN = " + ";
	private static final String SUM = "SUM";
	private static final String SEMICOLON = " ; ";
	private static final String COLON = " , ";

	public static String calculateQuoteTotals(Quote quote, InstanceOutput output, long rowNum) {

		String sheetName = quote.getName();

		StringBuffer monthly = new StringBuffer();

		monthly.append(SUM).append(BEGIN_FORMULA)
				.append(String.format(InstanceOutput.computeMonthlyPriceColumn, sheetName, rowNum)).append(COLON)
				.append(String.format(InstanceOutput.snapshotMonthlyPriceColumn, sheetName, rowNum)).append(COLON)
				.append(String.format(InstanceOutput.s3BackupMonthlyPriceColumn, sheetName, rowNum)).append(COLON)
				.append(String.format(InstanceOutput.archiveLogsLocalBackupMonthlyPriceColumn, sheetName, rowNum))
				.append(END_FORMULA);

		LOGGER.info(monthly.toString());
		return monthly.toString();

	}

}
