package com.amazon.proposalcalculator.calculator;

import com.amazon.proposalcalculator.bean.InstanceInput;
import com.amazon.proposalcalculator.enums.ProductName;
import com.amazon.proposalcalculator.reader.ConfigReader;
import com.amazon.proposalcalculator.reader.DataTransferReader;
import com.amazon.proposalcalculator.reader.DefaultExcelReader;
import com.amazon.proposalcalculator.reader.EC2PriceListReader;
import com.amazon.proposalcalculator.reader.S3PriceListReader;
import com.amazon.proposalcalculator.utils.Constants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Collection;

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
            //forceDownload = ParseMainArguments.isForceDownload(args);
            forceDownload = false;
            Collection<InstanceInput> instanceInputs = init(forceDownload);
            Calculator.calculate(instanceInputs, Constants.OUTPUT_FILE_NAME);
            Constants.endTime = System.currentTimeMillis();
            LOGGER.info("Calculation done! Took " + (Constants.endTime - Constants.beginTime)/1000 + " seconds!");
        } catch (Exception e){
            LOGGER.fatal("A fatal error has occured: " , e);
            //System.err.println("An error has occured: " + e.getLocalizedMessage());
            System.exit(1);
        }
    }

    private static Collection<InstanceInput> init(Boolean forceDownload) throws IOException {
        EC2PriceListReader.read(forceDownload);
        S3PriceListReader.read(forceDownload);
        Collection<InstanceInput> instanceInputs = DefaultExcelReader.read();
        ConfigReader.read();
        DataTransferReader.read();
        return instanceInputs;
    }

}
