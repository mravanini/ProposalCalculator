package com.amazon.proposalcalculator.calculator;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazon.proposalcalculator.bean.InstanceInput;
import com.amazon.proposalcalculator.reader.DataTransferReader;
import com.amazon.proposalcalculator.reader.EC2PriceListReader;
import com.amazon.proposalcalculator.reader.POIExcelReader;
import com.amazon.proposalcalculator.reader.ParametersReader;
import com.amazon.proposalcalculator.reader.S3PriceListReader;
import com.amazon.proposalcalculator.utils.Constants;

/**
 * Created by ravanini on 02/12/16.
 */

public class Run {

	private final static Logger LOGGER = LogManager.getLogger();
	private static int exponentialBackoffTime = 1000;

	public static void main(String[] args) {
		try {
			Thread.currentThread().setName(RunOnServer.class.getName());
			Constants.beginTime = System.currentTimeMillis();
			Boolean forceDownload;

			forceDownload = false;

			Collection<InstanceInput> instanceInputs = init(forceDownload);

			Calculator.calculate(instanceInputs, Constants.OUTPUT_FILE_NAME);
			Constants.endTime = System.currentTimeMillis();
			LOGGER.info("Calculation done! Took " + (Constants.endTime - Constants.beginTime) / 1000 + " seconds!");

		} catch (Exception e) {
			LOGGER.fatal("A fatal error has occured: ", e);
			System.exit(1);
		}
	}

	private static Collection<InstanceInput> init(Boolean forceDownload) throws Exception, IOException,
			NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		EC2PriceListReader.read(forceDownload);
		S3PriceListReader.read(forceDownload);

		Collection<InstanceInput> servers = POIExcelReader.read(Constants.INPUT_FILE_NAME);
		ParametersReader.read(Constants.INPUT_FILE_NAME);
		DataTransferReader.read(Constants.INPUT_FILE_NAME);

		return servers;
	}

}
