package com.amazon.proposalcalculator.reader;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by ravanini on 29/11/16.
 */
public class ParseMainArguments {

	private final static Logger LOGGER = LogManager.getLogger();

	private static CommandLine parse(String[] args) throws ParseException {

		LOGGER.debug("Parsing main arguments");
		Options options = new Options();

		Option forceDownload = new Option("f", "force-download", false, "force download price list");
		forceDownload.setRequired(false);
		options.addOption(forceDownload);

		CommandLineParser parser = new DefaultParser();
		CommandLine cmd;

		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("proposal-calculator [OPTIONS]", options);
			throw e;
		}

		return cmd;
	}

	public static boolean isForceDownload(String[] args) throws ParseException {

		CommandLine cmd = parse(args);
		return cmd.hasOption("f") || cmd.hasOption("forceDownload");

	}
}
