package com.amazon.proposalcalculator.reader;

import org.apache.commons.cli.ParseException;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by ravanini on 29/11/16.
 */
public class ParseMainArgumentsTest {


    @Test
    public void testEmptyArguments() throws ParseException {

        String[] args = new String[]{};

        Boolean isForceDownload = ParseMainArguments.isForceDownload(args);

        assertFalse(isForceDownload);
    }

    @Test
    public void testValidForceDownloadArgument() throws ParseException {

        String[] args = new String[]{"-f"};

        Boolean forceDownload = ParseMainArguments.isForceDownload(args);

        assertTrue(forceDownload);

        args = new String[]{"-f"};

        forceDownload = ParseMainArguments.isForceDownload(args);

        assertTrue(forceDownload);

    }

    @Test(expected = ParseException.class)
    public void testInvalidArguments() throws ParseException {

        String[] args = new String[]{"-xpto"};

        Boolean isForceDownload = ParseMainArguments.isForceDownload(args);

    }
}
