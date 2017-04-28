package com.amazon.proposalcalculator.calculator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.UUID;

/**
 * Created by ravanini on 26/04/17.
 */
public class WriteStackTrace {

    private final static Logger LOGGER = LogManager.getLogger();

    public static String buildMessageForAdministrators(Exception e) {

        StringBuffer stackTrace = new StringBuffer();
        stackTrace.append("error id:(");
        stackTrace.append(UUID.randomUUID().toString());
        stackTrace.append(") ");
        stackTrace.append("An error occurred when calculating input workload: \n");
        stackTrace.append(e.getLocalizedMessage());

        LOGGER.error(stackTrace);

        stackTrace.append("\n");
        stackTrace.append(Arrays.toString(e.getStackTrace()));

        return stackTrace.toString();
    }

    public static String buildMessageForCustomers(Exception e){

        StringBuffer stackTrace = new StringBuffer();
        stackTrace.append("An error occurred when calculating input workload: ");
        stackTrace.append(e.getLocalizedMessage());
        stackTrace.append("\nThe system administrator has been notified about this error.");

        LOGGER.error(stackTrace);

        return stackTrace.toString();
    }

    public static String buildMessageForAdministrators(Exception e, String bucketName, String inputFileS3Key, String emailFromMetadata) {
        StringBuffer stackTrace = new StringBuffer();
        stackTrace.append(buildMessageForAdministrators(e));
        stackTrace.append("\nBucket name: ");
        stackTrace.append(bucketName);
        stackTrace.append("\nInput file name: ");
        stackTrace.append(inputFileS3Key);
        stackTrace.append("\nUser email address: ");
        stackTrace.append(emailFromMetadata);
        return stackTrace.toString();
    }
}
