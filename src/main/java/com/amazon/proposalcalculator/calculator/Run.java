package com.amazon.proposalcalculator.calculator;

import com.amazon.proposalcalculator.enums.ProductName;
import com.amazon.proposalcalculator.reader.ConfigReader;
import com.amazon.proposalcalculator.reader.DataTransferReader;
import com.amazon.proposalcalculator.reader.DefaultExcelReader;
import com.amazon.proposalcalculator.reader.EC2PriceListReader;
import com.amazon.proposalcalculator.reader.ParseMainArguments;
import com.amazon.proposalcalculator.reader.S3PriceListReader;
import com.amazon.proposalcalculator.utils.Constants;

import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Created by ravanini on 02/12/16.
 */
public class Run {

    private final static Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        try {
        	System.out.println(ProductName.AmazonS3.toString());
        	Constants.beginTime = System.currentTimeMillis();
            Boolean forceDownload;
            forceDownload = ParseMainArguments.isForceDownload(args);
            init(forceDownload);
            Calculator.calculate(Constants.INPUT_FILE_NAME, Constants.OUTPUT_FILE_NAME);
            Constants.endTime = System.currentTimeMillis();
            LOGGER.info("Calculation done! Took " + (Constants.endTime - Constants.beginTime)/1000 + " seconds!");
        } catch (ParseException pe) {
            System.exit(1);
        } catch (Exception e){
            LOGGER.fatal("A fatal error has occured: " , e);
            //System.err.println("An error has occured: " + e.getLocalizedMessage());
            System.exit(1);
        }
    }

    private static void init(Boolean forceDownload) throws IOException {
        EC2PriceListReader.read(forceDownload);
        S3PriceListReader.read(forceDownload);
        DefaultExcelReader.read();
        ConfigReader.read();
        DataTransferReader.read();
    }

}
