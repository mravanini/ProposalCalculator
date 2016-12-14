package com.amazon.proposalcalculator.calculator;

import com.amazon.proposalcalculator.reader.ConfigReader;
import com.amazon.proposalcalculator.reader.DefaultExcelReader;
import com.amazon.proposalcalculator.reader.EC2PriceListReader;
import com.amazon.proposalcalculator.reader.ParseMainArguments;
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

            Boolean forceDownload;
            forceDownload = ParseMainArguments.isForceDownload(args);

            init(forceDownload);

            Calculator.calculate();
            LOGGER.info("Done!");
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
        DefaultExcelReader.read();
        ConfigReader.read();
    }

}
