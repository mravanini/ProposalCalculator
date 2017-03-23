package com.amazon.proposalcalculator.calculator;

import com.amazon.proposalcalculator.bean.InstanceOutput;
import com.amazon.proposalcalculator.bean.Quote;
import com.amazon.proposalcalculator.utils.SomeMath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by ravanini on 17/03/17.
 */
public class CalculatorUsingFormula {

    private final static Logger LOGGER = LogManager.getLogger();


    private static final int THREE_YR_DURATION = 36;
    private static final int ONE_YR_DURATION = 12;
    //private static final String BEGIN_FORMULA = "=(";
    private static final String BEGIN_FORMULA = "(";
    private static final String END_FORMULA = ")";
    private static final String PLUS_SIGN = " + ";
    private static final String SUM = "SUM";
    private static final String SEMICOLON = " ; ";
    private static final String COLON = " , ";





    public static String calculateQuoteTotals(Quote quote, InstanceOutput output, long rowNum) {

        String sheetName = quote.getName();
        //
//        double monthly = quote.getMonthly() + output.getComputeMonthlyPrice() + output.getStorageMonthlyPrice()
//                + output.getSnapshotMonthlyPrice() + output.getS3BackupMonthlyPrice()
//                + output.getArchiveLogsLocalBackupMonthlyPrice() /*+ dataTransferOutMonthlyPrice*/ ;
//        quote.setMonthly(monthly);
//
//        double months = "3yr".equals(output.getLeaseContractLength()) ? 36 : 12;
//        double monthlyUpfront = output.getUpfrontFee() / months;
//        double threeYearTotal = quote.getThreeYearTotal() + monthlyUpfront + output.getComputeMonthlyPrice()
//                + output.getStorageMonthlyPrice() + output.getSnapshotMonthlyPrice();
//        quote.setThreeYearTotal(threeYearTotal);
//
//        double upfront = quote.getUpfront() + output.getUpfrontFee();
//        quote.setUpfront(upfront);

        String str = String.valueOf(quote.getMonthly());
        //str = str.replace(".", ",");//TODO THIS MUST LEAVE



        StringBuffer monthly = new StringBuffer();

        monthly.append(SUM)
               .append(BEGIN_FORMULA)
               .append(str) //TODO CAN I WRITE A FORMULA OUT OF THIS?
               .append(COLON)
               .append(String.format(InstanceOutput.computeMonthlyPriceColumn,sheetName, rowNum))
               .append(END_FORMULA)
               ;

        LOGGER.info(monthly.toString());
        return monthly.toString();


                //        double monthly = quote.getMonthly()
//                + output.getComputeMonthlyPrice()
//                + output.getStorageMonthlyPrice()
//                + output.getSnapshotMonthlyPrice()
//                + output.getS3BackupMonthlyPrice()
//                + output.getArchiveLogsLocalBackupMonthlyPrice() /*+ dataTransferOutMonthlyPrice*/ ;
//
//        quote.setMonthly(monthly);
//
//        double months = "3yr".equals(output.getLeaseContractLength()) ? THREE_YR_DURATION : ONE_YR_DURATION;
//
//        double monthlyUpfront = output.getUpfrontFee() / months;
//
//        double threeYearTotal = quote.getThreeYearTotal()
//                + monthlyUpfront
//                + output.getComputeMonthlyPrice()
//                + output.getStorageMonthlyPrice()
//                + output.getSnapshotMonthlyPrice();
//
//        quote.setThreeYearTotal(threeYearTotal);
//
//        double upfront = quote.getUpfront() + output.getUpfrontFee();
//
//        quote.setUpfront(upfront);

    }




}
