package com.amazon.proposalcalculator.calculator;

import com.amazon.proposalcalculator.assemblies.InstanceOutputAssembly;
import com.amazon.proposalcalculator.bean.InstanceInput;
import com.amazon.proposalcalculator.bean.InstanceOutput;
import com.amazon.proposalcalculator.bean.Price;
import com.amazon.proposalcalculator.bean.Quote;
import com.amazon.proposalcalculator.enums.QuoteName;
import com.amazon.proposalcalculator.utils.Constants;
import com.amazon.proposalcalculator.writer.DefaultExcelWriter;
import com.amazon.proposalcalculator.writer.POIExcelWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.amazon.proposalcalculator.calculator.CalculatorPredicates.*;
import static java.time.temporal.ChronoUnit.DAYS;

public class Calculator {

	private final static Logger LOGGER = LogManager.getLogger();

	public void calculate(String outputFileName) throws IOException {

		//Constants.quotes = new ArrayList<Quote>();
		List quotes = new ArrayList<Quote>();

		ValidateInputSheet.validate(Constants.servers);

		LOGGER.info("Calculating prices...");

		Quote q1 = new Quote(QuoteName.YOUR_INPUT.getName());
		quotes.add(calculatePrice(q1));

		Quote q2 = new Quote("OnDemand", null, null, null);
		quotes.add(calculatePrice(q2));

		Quote q3 = new Quote("Reserved", "1yr", "No Upfront", "standard");
		quotes.add(calculatePrice(q3));

		Quote q4 = new Quote("Reserved", "1yr", "Partial Upfront", "standard");
		quotes.add(calculatePrice(q4));

		Quote q5 = new Quote("Reserved", "1yr", "All Upfront", "standard");
		quotes.add(calculatePrice(q5));

		Quote q6 = new Quote("Reserved", "3yr", "Partial Upfront", "standard");
		quotes.add(calculatePrice(q6));

		Quote q7 = new Quote("Reserved", "3yr", "All Upfront", "standard");
		quotes.add(calculatePrice(q7));

		Quote q8 = new Quote("Reserved", "3yr", "No Upfront", "convertible");
		quotes.add(calculatePrice(q8));

		Quote q9 = new Quote("Reserved", "3yr", "Partial Upfront", "convertible");
		quotes.add(calculatePrice(q9));

		Quote q10 = new Quote("Reserved", "3yr", "All Upfront", "convertible");
		quotes.add(calculatePrice(q10));

		calculateDiscount(quotes);

		//new DefaultExcelWriter().write(outputFileName, quotes);
		POIExcelWriter.write(outputFileName, quotes);
	}

	private void calculateDiscount(List<Quote> quotes) {
		Collections.sort(quotes);

		double higherValue = quotes.get(0).getThreeYearTotal();

		if (higherValue > 0) {
			for (Quote q : quotes) {
				q.setDiscount((1 - (q.getThreeYearTotal() / higherValue)));
			}
		}
	}

	private Quote calculatePrice(Quote quote) {
		// LOGGER.info("Calculating " + quote.getName());

		long rowNum = 1;//first line is the title

		for (InstanceInput input : Constants.servers) {

			rowNum++;

			// TODO fix this
			if (!quote.getName().equals(QuoteName.YOUR_INPUT.getName())) {
				input.setTermType(quote.getTermType());
				input.setLeaseContractLength(quote.getLeaseContractLength());
				input.setPurchaseOption(quote.getPurchaseOption());
				input.setOfferingClass(quote.getOfferingClass());
			}

			InstanceOutput output = InstanceOutputAssembly.from(input);

			if (input.hasErrors()) {
				output.setErrorMessage(input.getErrorMessageInput());
			} else {
				output.setStorageMonthlyPrice(StoragePricingCalculator.getStorageMonthlyPrice(input));
				output.setSnapshotMonthlyPrice(StoragePricingCalculator.getSnapshotMonthlyPrice(input));

				output.setArchiveLogsLocalBackupMonthlyPrice(
						StoragePricingCalculator.getArchiveLogsLocalBackupMonthlyPrice(input));
				double s3BackupMonthlyPrice = StoragePricingCalculator.getS3BackupMonthlyPrice(input);
				output.setS3BackupMonthlyPrice(s3BackupMonthlyPrice);

				findMatches(quote, input, output, false);
				
				calculateQuoteTotals(quote, output, rowNum);

			}
			// Constants.output.add(output);
			quote.addOutput(output);

		}
		quote.setThreeYearTotal(quote.getThreeYearTotal() * 36);
		LOGGER.info(quote.getName() + ":" + quote.getThreeYearTotal());
		//Constants.quotes.add(quote);
		return quote;
	}

	private void calculateQuoteTotals(Quote quote, InstanceOutput output, long rowNum) {
		//
		double monthly = quote.getMonthly() + output.getComputeMonthlyPrice() + output.getStorageMonthlyPrice()
				+ output.getSnapshotMonthlyPrice() + output.getS3BackupMonthlyPrice()
				+ output.getArchiveLogsLocalBackupMonthlyPrice() /*+ dataTransferOutMonthlyPrice*/ ;
		quote.setMonthly(monthly);
		
		double months = "3yr".equals(output.getLeaseContractLength()) ? 36 : 12;
		double monthlyUpfront = output.getUpfrontFee() / months;
		double threeYearTotal = quote.getThreeYearTotal() + monthlyUpfront + output.getComputeMonthlyPrice()
				+ output.getStorageMonthlyPrice() + output.getSnapshotMonthlyPrice();
		quote.setThreeYearTotal(threeYearTotal);

		double upfront = quote.getUpfront() + output.getUpfrontFee();
		quote.setUpfront(upfront);

		//quote.setMonthlyFormula(CalculatorUsingFormula.calculateQuoteTotals(quote, output, rowNum));//TODO testing

	}





	private void findMatches(Quote quote, InstanceInput input, InstanceOutput output, boolean forceBreakInstances) {
		List<Price> possibleMatches = findPossibleMatches(input, output, forceBreakInstances);
		if (possibleMatches != null) {
			findBestMatch(quote, input, output, possibleMatches);
			if ("HANA_OLAP".equals(input.getSapInstanceType()) && input.getOriginalMemory() > 0) {
				double efectiveMemory = output.getInstanceMemory() * input.getInstances();
				if (efectiveMemory / input.getOriginalMemory() > 1.1) {
					//LOGGER.info("Iterating findMatches:" + input.getDescription());
					findMatches(quote, input, output, true);
				}
			}
		}
	}

	private List<Price> findPossibleMatches(InstanceInput input, InstanceOutput output, boolean forceBreakInstances) {
		LOGGER.debug("Calculating instance: " + input.getDescription());

		Predicate<Price> predicate = region(input).and(ec2(input)).and(tenancy(input)).and(licenceModel(input))
				.and(operatingSystem(input)).and(preInstalledSw(input)).and(termType(input))
				.and(offeringClass(input)).and(leaseContractLength(input)).and(purchaseOption(input))
				.and(memory(input)).and(newGeneration(input))
				.and(sapCertifiedInstances(input));
		
		//f CPU and SAPS are both provided. CPU is ignored.
		if (input.getSaps() != null)
			predicate = predicate.and(saps(input));
		else	
			predicate = predicate.and(cpu(input));
		
		//only sap certified...
		if (input.getSapInstanceType() != null) {
			predicate = predicate.and(sapCertifiedInstances(input));
		}
		
		//only hana certified instances
		if (input.getSapInstanceType() != null && input.getSapInstanceType().startsWith("HANA")) {
			predicate = predicate.and(hanaCertifiedInstances(input));
		}

		List<Price> possibleMatches = Constants.ec2PriceList.stream().filter(predicate)
				.collect(Collectors.toList());

		if (!forceBreakInstances && possibleMatches.size() > 0) {
			return possibleMatches;
		} else if ("HANA_OLAP".equals(input.getSapInstanceType())) {
			breakInManyInstances(input);
			output.setInstances(input.getInstances());
			return findPossibleMatches(input, output, false);
		} else  {
			output.setErrorMessage("Could not find a match for this server configuration.");
			return null;
		}
	}

	private void breakInManyInstances(InstanceInput input) {
		if (input.getInstances() == 1) { 
			input.setOriginalMemory(input.getMemory());
			input.setInstances(3);
		} else {
			input.setInstances(input.getInstances()+1);
		}
		input.setMemory(input.getOriginalMemory()/input.getInstances());
	}

	private void findBestMatch(Quote quote, InstanceInput input, InstanceOutput output,
			List<Price> possibleMatches) {
			Price price = getBestPrice(possibleMatches);

			output.setInstanceType(price.getInstanceType());
			output.setInstanceVCPU(price.getvCPU());
			output.setInstanceMemory(price.getMemory());
			output.setInstanceSAPS(price.getSaps());

			output.setComputeUnitPrice(price.getInstanceHourPrice());
			output.setComputeMonthlyPrice(
					price.getInstanceHourPrice() * Constants.HOURS_IN_A_MONTH * input.getInstances()
							* (price.getTermType().equals("OnDemand") ? input.getMonthlyUtilization() : 1));

			double days = 0;
			if (input.getBeginning() != null && input.getEnd() != null) {
				days = diffInDays(input.getBeginning(), input.getEnd());
			} else {
				days = Constants.HOURS_IN_A_MONTH / 24;
			}

			output.setComputeTotalPrice(price.getInstanceHourPrice() * days * 24 * input.getInstances()
					* (input.getTermType().equals("OnDemand") ? input.getMonthlyUtilization() : 1));

			output.setStorageMonthlyPrice(StoragePricingCalculator.getStorageMonthlyPrice(input));
			output.setSnapshotMonthlyPrice(StoragePricingCalculator.getSnapshotMonthlyPrice(input));

			output.setArchiveLogsLocalBackupMonthlyPrice(
					StoragePricingCalculator.getArchiveLogsLocalBackupMonthlyPrice(input));

			output.setUpfrontFee(price.getUpfrontFee() * input.getInstances());

			DataTransferPricingCalculator dataCalculator = new DataTransferPricingCalculator();
			double dataTransferOutMonthlyPrice = dataCalculator.getDataTransferOutMonthlyPrice(Constants.dataTransfer);

			

		
	}

	private void setEfectivePrice(List<Price> priceList) {
		for (Price somePrice : priceList) {
			if (somePrice.getTermType().equals("OnDemand") || somePrice.getPurchaseOption().equals("No Upfront")) {
				somePrice.setEfectivePrice(somePrice.getPricePerUnit());
				somePrice.setInstanceHourPrice(somePrice.getPricePerUnit());
			} else if (somePrice.getPurchaseOption().equals("Partial Upfront")
					|| somePrice.getPurchaseOption().equals("All Upfront")) {
				if (somePrice.getPriceDescription().equals("Upfront Fee")) {
					Price hourFee = getInstanceHourFee(priceList, somePrice);
					somePrice.setEfectivePrice(somePrice.getPricePerUnit()
							/ (12 * Integer.valueOf(somePrice.getLeaseContractLength().substring(0, 1)))
							+ hourFee.getPricePerUnit());
					somePrice.setUpfrontFee(somePrice.getPricePerUnit());
					somePrice.setInstanceHourPrice(hourFee.getPricePerUnit());
				} else {
					Price upfrontFee = getUpfrontFee(priceList, somePrice);
					somePrice.setEfectivePrice(upfrontFee.getPricePerUnit()
							/ (12 * Integer.valueOf(somePrice.getLeaseContractLength().substring(0, 1)))
							+ somePrice.getPricePerUnit());
					somePrice.setUpfrontFee(upfrontFee.getPricePerUnit());
					somePrice.setInstanceHourPrice(somePrice.getPricePerUnit());
				}
			}
		}
	}

	private Price getUpfrontFee(List<Price> priceList, Price price) {
		for (Price somePrice : priceList) {
			if (somePrice.getSku().equals(price.getSku()) && somePrice.getPriceDescription().equals("Upfront Fee")) {
				return somePrice;
			}
		}
		return null;
	}

	private Price getInstanceHourFee(List<Price> priceList, Price price) {
		for (Price somePrice : priceList) {
			if (somePrice.getSku().equals(price.getSku()) && !somePrice.getPriceDescription().equals("Upfront Fee")) {
				return somePrice;
			}
		}
		return null;
	}

	private Price getBestPrice(List<Price> prices) {
		setEfectivePrice(prices);

		// Price bestPrice = new Price();
		// bestPrice.setEfectivePrice(1_000_000);
		Price bestPrice = prices.get(0);

		for (Price price : prices) {
			// LOGGER.info(price.getOperatingSystem());
			if (price.getEfectivePrice() < bestPrice.getEfectivePrice()) {
				bestPrice = price;
			}
		}
		return bestPrice;
	}

	private long diffInDays(String beginning, String end) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date beginningDate = format.parse(beginning);
			Date endDate = format.parse(end);
			return diffInDays(beginningDate, endDate);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}

	private long diffInDays(Date beginning, Date end) {

		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(beginning);
		LocalDate localBeginning = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
				calendar.get(Calendar.DAY_OF_MONTH));
		calendar.setTime(end);
		LocalDate localEnd = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
				calendar.get(Calendar.DAY_OF_MONTH));
		return DAYS.between(localBeginning, localEnd.plusDays(1));// last day
																	// inclusive
	}

}
