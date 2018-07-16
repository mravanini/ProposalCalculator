package com.amazon.proposalcalculator.calculator;

import com.amazon.proposalcalculator.assemblies.InstanceOutputAssembly;
import com.amazon.proposalcalculator.bean.InstanceInput;
import com.amazon.proposalcalculator.bean.InstanceOutput;
import com.amazon.proposalcalculator.bean.MinimalHanaStorage;
import com.amazon.proposalcalculator.bean.Price;
import com.amazon.proposalcalculator.bean.Quote;
import com.amazon.proposalcalculator.enums.Environment;
import com.amazon.proposalcalculator.enums.LeaseContractLength;
import com.amazon.proposalcalculator.enums.OfferingClass;
import com.amazon.proposalcalculator.enums.PurchaseOption;
import com.amazon.proposalcalculator.enums.QuoteName;
import com.amazon.proposalcalculator.enums.SAPInstanceType;
import com.amazon.proposalcalculator.enums.TermType;
import com.amazon.proposalcalculator.utils.Constants;
import com.amazon.proposalcalculator.utils.SomeMath;
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

	private static final double MAX_MEMORY_WASTE= 1.5;
	private static final int MIN_HANA_CLUSTER_SIZE = 3;
	private static final String DATE_FORMAT = "dd/MM/yyyy";
	private static final String UPFRONT_FEE = "Upfront Fee";
	private final static Logger LOGGER = LogManager.getLogger();

	public static void calculate(Collection<InstanceInput> servers, String outputFileName) throws IOException {

		List quotes = new ArrayList<Quote>();

		ValidateInputSheet.validate(servers);

		LOGGER.info("Calculating prices...");

		Quote q1 = new Quote(QuoteName.YOUR_INPUT.getName());
		quotes.add(calculatePrice(servers, q1));

		Quote q2 = new Quote(TermType.OnDemand.name(), null, null, null);
		quotes.add(calculatePrice(servers, q2));

		Quote q3 = new Quote(TermType.Reserved.name(), 
				LeaseContractLength.ONE_YEAR.getColumnName(), 
				PurchaseOption.NO_UPFRONT.getColumnName(),
				OfferingClass.Standard.name());
		quotes.add(calculatePrice(servers, q3));

		Quote q4 = new Quote(TermType.Reserved.name(), 
				LeaseContractLength.ONE_YEAR.getColumnName(), 
				PurchaseOption.PARTIAL_UPFRONT.getColumnName(),
				OfferingClass.Standard.name());
		quotes.add(calculatePrice(servers, q4));

		Quote q5 = new Quote(TermType.Reserved.name(), 
				LeaseContractLength.ONE_YEAR.getColumnName(), 
				PurchaseOption.ALL_UPFRONT.getColumnName(),
				OfferingClass.Standard.name());
		quotes.add(calculatePrice(servers, q5));
		
		Quote q6 = new Quote(TermType.Reserved.name(), 
				LeaseContractLength.THREE_YEARS.getColumnName(),
				PurchaseOption.NO_UPFRONT.getColumnName(), 
				OfferingClass.Standard.name());
		quotes.add(calculatePrice(servers, q6));

		Quote q7 = new Quote(TermType.Reserved.name(), 
				LeaseContractLength.THREE_YEARS.getColumnName(),
				PurchaseOption.PARTIAL_UPFRONT.getColumnName(), 
				OfferingClass.Standard.name());
		quotes.add(calculatePrice(servers, q7));

		Quote q8 = new Quote(TermType.Reserved.name(), 
				LeaseContractLength.THREE_YEARS.getColumnName(), 
				PurchaseOption.ALL_UPFRONT.getColumnName(),
				OfferingClass.Standard.name());
		quotes.add(calculatePrice(servers, q8));
		
		Quote q9 = new Quote(TermType.Reserved.name(), 
				LeaseContractLength.ONE_YEAR.getColumnName(), 
				PurchaseOption.NO_UPFRONT.getColumnName(),
				OfferingClass.Convertible.name());
		quotes.add(calculatePrice(servers, q9));

		Quote q10 = new Quote(TermType.Reserved.name(), 
				LeaseContractLength.ONE_YEAR.getColumnName(),
				PurchaseOption.PARTIAL_UPFRONT.getColumnName(), 
				OfferingClass.Convertible.name());
		quotes.add(calculatePrice(servers, q10));

		Quote q11 = new Quote(TermType.Reserved.name(), 
				LeaseContractLength.ONE_YEAR.getColumnName(), 
				PurchaseOption.ALL_UPFRONT.getColumnName(),
				OfferingClass.Convertible.name());
		quotes.add(calculatePrice(servers, q11));

		Quote q12 = new Quote(TermType.Reserved.name(), 
				LeaseContractLength.THREE_YEARS.getColumnName(), 
				PurchaseOption.NO_UPFRONT.getColumnName(),
				OfferingClass.Convertible.name());
		quotes.add(calculatePrice(servers, q12));

		Quote q13 = new Quote(TermType.Reserved.name(), 
				LeaseContractLength.THREE_YEARS.getColumnName(),
				PurchaseOption.PARTIAL_UPFRONT.getColumnName(), 
				OfferingClass.Convertible.name());
		quotes.add(calculatePrice(servers, q13));

		Quote q14 = new Quote(TermType.Reserved.name(), 
				LeaseContractLength.THREE_YEARS.getColumnName(), 
				PurchaseOption.ALL_UPFRONT.getColumnName(),
				OfferingClass.Convertible.name());
		quotes.add(calculatePrice(servers, q14));
		
		calculateDiscount(quotes);

		//new DefaultExcelWriter().write(outputFileName, quotes);
		POIExcelWriter.write(outputFileName, quotes);
	}

	private static void calculateDiscount(List<Quote> quotes) {
		Collections.sort(quotes);

		double higherValue = quotes.get(0).getThreeYearTotal();

		if (higherValue > 0) {
			for (Quote q : quotes) {
				q.setDiscount((1 - (q.getThreeYearTotal() / higherValue)));
			}
		}
	}

	private static Quote calculatePrice(Collection<InstanceInput> servers, Quote quote) {
		// LOGGER.info("Calculating " + quote.getName());

		long rowNum = 1;//first line is the title

		for (InstanceInput input : servers) {

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
				quote.setHasErrors(Boolean.TRUE);
			} else {
				//find instance and break
				findMatches(quote, input, output, false);
				
				
				//why this? check
				//changing to add # of instances...
				//then calculate the price for storage at the end...
				output.setStorageMonthlyPrice(StoragePricingCalculator.getStorageMonthlyPrice(input) * input.getInstances());
				output.setSnapshotMonthlyPrice(StoragePricingCalculator.getSnapshotMonthlyPrice(input) * input.getInstances());

				output.setArchiveLogsLocalBackupMonthlyPrice(
						StoragePricingCalculator.getArchiveLogsLocalBackupMonthlyPrice(input) * input.getInstances());
				
				double s3BackupMonthlyPrice = StoragePricingCalculator.getS3BackupMonthlyPrice(input);
				output.setS3BackupMonthlyPrice(s3BackupMonthlyPrice * input.getInstances());
				
				calculateQuoteTotals(quote, output, rowNum);

			}
			// Constants.output.add(output);
			quote.addOutput(output);

		}
		
		
		
		LOGGER.info(quote.getName() 
				+ "::" + (int) quote.getOneYrUpfront() 
				+ "::" + (int) quote.getThreeYrsUpfront() 
				+ "::" + (int) quote.getUpfront() 
				+ "::" + (int) quote.getThreeYearTotal());
		
		
		//Constants.quotes.add(quote);
		
		quote.setMonthlySupport(BusinessSupportCalculator.getInstance().calculateMonthlySupport(quote.getMonthly()));
		quote.setOneYrUpfrontSupport(BusinessSupportCalculator.getInstance().calculateUpfrontSupport(quote.getOneYrUpfront(), quote.getMonthly()));
		quote.setThreeYrsUpfrontSupport(BusinessSupportCalculator.getInstance().calculateUpfrontSupport(quote.getThreeYrsUpfront(), quote.getMonthly()));
		
		quote.setThreeYearTotal((quote.getThreeYearTotal() * 36) + (quote.getMonthlySupport()*36) + (quote.getOneYrUpfrontSupport()*3) + quote.getThreeYrsUpfrontSupport());
		
		return quote;
	}

	private static void calculateQuoteTotals(Quote quote, InstanceOutput output, long rowNum) {
		
		double monthly = quote.getMonthly() + output.getComputeMonthlyPrice() + output.getStorageMonthlyPrice()
				+ output.getSnapshotMonthlyPrice() + output.getS3BackupMonthlyPrice()
				+ output.getArchiveLogsLocalBackupMonthlyPrice() /*+ dataTransferOutMonthlyPrice*/ ;
		
		quote.setMonthly(monthly);
		
		double months = 
				LeaseContractLength.THREE_YEARS.getColumnName().equals(output.getLeaseContractLength()) ? 36 : 12;
		double monthlyUpfront = output.getUpfrontFee() / months;
		
		double threeYearTotal = quote.getThreeYearTotal() + 
								monthlyUpfront + 
								output.getComputeMonthlyPrice() + 
								output.getStorageMonthlyPrice() + 
								output.getSnapshotMonthlyPrice() + 
								output.getArchiveLogsLocalBackupMonthlyPrice() +
								output.getS3BackupMonthlyPrice();
		
		quote.setThreeYearTotal(threeYearTotal);

		//mudar aqui
		double upfront = quote.getUpfront() + output.getUpfrontFee();
		quote.setUpfront(upfront);
		
		if (months == 12) {
			double oneYrUpfront = quote.getOneYrUpfront() + output.getUpfrontFee();
			quote.setOneYrUpfront(oneYrUpfront);
		} else if (months == 36) {
			double threeYrsUpfront = quote.getThreeYrsUpfront() + output.getUpfrontFee();
			quote.setThreeYrsUpfront(threeYrsUpfront);
		}
		
		
		
		//quote.setMonthlyFormula(CalculatorUsingFormula.calculateQuoteTotals(quote, output, rowNum));//TODO testing

	}

	private static void findMatches(Quote quote, InstanceInput input, 
			InstanceOutput output, boolean forceBreakInstances) {
		LOGGER.debug("findMatches:" + input.getDescription() + "::" + input.getEnvironment());
		List<Price> possibleMatches = findPossibleMatches(input, output, forceBreakInstances, quote);
		if (possibleMatches != null) {
			findBestMatch(quote, input, output, possibleMatches);
			if (SAPInstanceType.HANA_OLAP.equals(input.getSapInstanceType()) && input.getOriginalMemory() > 0) {
				double effectiveMemory = output.getInstanceMemory() * input.getInstances();
				if (effectiveMemory / input.getOriginalMemory() > MAX_MEMORY_WASTE) {
					LOGGER.debug("Recursive findMatches:" + input.getDescription());
					findMatches(quote, input, output, true);
				}
			}
		}
	}

	private static List<Price> findPossibleMatches(InstanceInput input, 
			InstanceOutput output, boolean forceBreakInstances, Quote quote) {
		LOGGER.debug("Calculating instance: " + input.getDescription());

		Predicate<Price> predicate = region(input).and(ec2(input)).and(tenancy(input)).and(licenceModel(input))
				.and(operatingSystem(input)).and(preInstalledSw(input)).and(termType(input))
				.and(offeringClass(input)).and(leaseContractLength(input)).and(purchaseOption(input))
				.and(memory(input)).and(newGeneration(input).and(burstable(input)) );
		
		//f CPU and SAPS are both provided. CPU is ignored.
		if (input.getSaps() > 0)
			predicate = predicate.and(saps(input));
		else	
			predicate = predicate.and(cpu(input));
		
		// only sap certified...
		if (input.getSapInstanceType() != null
				&& (input.getSapInstanceType().startsWith(SAPInstanceType.APPS.name())
						|| input.getSapInstanceType().startsWith(SAPInstanceType.ANY_DB.name()))) {
			predicate = predicate.and(sapProductionCertifiedInstances(input));
		}
		
		// only hana certified instances
		if (input.getSapInstanceType() != null
				&& (SAPInstanceType.HANA_OLTP.name().equals(input.getSapInstanceType())
						|| SAPInstanceType.HANA_OLAP.name().equals(input.getSapInstanceType())
						|| SAPInstanceType.HANA_B1.name().equals(input.getSapInstanceType()))) {
			if (Environment.isEquivalentToProd(input.getEnvironment())) {
				predicate = predicate.and(hanaProductionCertifiedInstances(input));
			} else {
				predicate = predicate.and(hanaDevQaInstances(input));
			}
		}

		List<Price> possibleMatches = Constants.ec2PriceList.stream().filter(predicate)
				.collect(Collectors.toList());

		if (!forceBreakInstances && possibleMatches.size() > 0) {
			return possibleMatches;
		} else if (SAPInstanceType.HANA_OLAP.name().equals(input.getSapInstanceType())) {
			breakInManyInstances(input, output);
			//output.setInstances(input.getInstances());
			
			List<Price> findPossibleMatches = findPossibleMatches(input, output, false, quote);
			return findPossibleMatches;
		} else  {
			output.setErrorMessage("Could not find a match for this server configuration.");
			quote.setHasErrors(Boolean.TRUE);
			return null;
		}
	}

	private static void breakInManyInstances(InstanceInput input, InstanceOutput output) {
		LOGGER.info("Breaking... " + input.getDescription() + "::" + input.getEnvironment() 
			+ "::" + input.getCpu() + "::" + input.getMemory());
		
		if (input.getInstances() == 1) { 
			input.setOriginalMemory(input.getMemory());
			
			//input.setOriginalCpu(input.getCpu());
			//input.setOriginalSaps(input.getSaps());
			
			//for HANA OLAP, sizing will be done only by Memory
			input.setOriginalCpu(0D);
			input.setOriginalSaps(0);
			
			input.setOriginalStorage(input.getStorage());
			input.setOriginalSnapshot(input.getSnapshot());
			input.setOriginalArchiveLogsLocalBackup(input.getArchiveLogsLocalBackup());
			input.setOriginalS3Backup(input.getS3Backup());
			
			input.setInstances(MIN_HANA_CLUSTER_SIZE);
		} else {
			input.setInstances(input.getInstances()+1);
		}
		
			input.setMemory(SomeMath.roundDouble(input.getOriginalMemory()/input.getInstances(), 2));
		
			input.setSaps(input.getOriginalSaps()/input.getInstances());
		
			input.setCpu(SomeMath.roundDouble(input.getOriginalCpu()/input.getInstances(), 2));
		
			input.setStorage(input.getOriginalStorage()/input.getInstances());
		
			input.setSnapshot(input.getOriginalSnapshot()/input.getInstances());
		
			input.setArchiveLogsLocalBackup(input.getOriginalArchiveLogsLocalBackup()/input.getInstances());
		
			input.setS3Backup(input.getOriginalS3Backup()/input.getInstances());
		
		
		output.setInstances(input.getInstances());
		
		output.setOriginalCpu(input.getOriginalCpu());
		output.setOriginalSaps(input.getOriginalSaps());
		output.setOriginalStorage(input.getOriginalStorage());
		output.setOriginalSnapshot(input.getOriginalSnapshot());
		output.setOriginalArchiveLogsLocalBackup(input.getOriginalArchiveLogsLocalBackup());
		output.setOriginalS3Backup(input.getOriginalS3Backup());
		output.setOriginalMemory(input.getOriginalMemory());
		
		output.setCpu(input.getCpu());
		output.setSaps(input.getSaps());
		output.setStorage(input.getStorage());
		output.setSnapshot(input.getSnapshot());
		output.setArchiveLogsLocalBackup(input.getArchiveLogsLocalBackup());
		output.setS3Backup(input.getS3Backup());
		output.setMemory(input.getMemory());
		
	}

	private static void findBestMatch(Quote quote, InstanceInput input, InstanceOutput output, List<Price> possibleMatches) {
		Price price = getBestPrice(possibleMatches);

		minimalStorage(output, input, price);
		
		if (input.getEnvironment().equals(Environment.DR_OPTIMIZED.toString())) {
			input.setCpu(input.getCpu() * Constants.DR_OPTIMIZED_PERCENTAGE);
            input.setMemory(input.getMemory() * Constants.DR_OPTIMIZED_PERCENTAGE);
            input.setSaps((int) (input.getSaps() * Constants.DR_OPTIMIZED_PERCENTAGE));
			possibleMatches = findPossibleMatches(input, output, false, quote);
			price = getBestPrice(possibleMatches);
		}

		setPrices(input, output, price);
	}

	private static void minimalStorage(InstanceOutput output, InstanceInput input, Price price) {
		if (input.getSapInstanceType() != null && input.getSapInstanceType().startsWith("HANA")) {
			MinimalHanaStorage minimalHanaStorage = CalculateHanaMinimalStorage.getInstance()
					.getMinimalHanaStorage(price.getInstanceType());

			if (input.getArchiveLogsLocalBackup() == 0 || (input.getArchiveLogsLocalBackup() < minimalHanaStorage.getBackupVolume())) {
				input.setArchiveLogsLocalBackup(minimalHanaStorage.getBackupVolume());
				output.setArchiveLogsLocalBackup(minimalHanaStorage.getBackupVolume());
			}

			Integer generalVolumeSize = minimalHanaStorage.getDataAndLogsVolume() + minimalHanaStorage.getRootVolume()
					+ minimalHanaStorage.getSapBinariesVolume();
			
			generalVolumeSize = generalVolumeSize + (minimalHanaStorage.getSharedVolume() / input.getInstances());
			
			if (input.getStorage() == 0 || input.getStorage() < generalVolumeSize) {
				input.setStorage(generalVolumeSize);
				output.setStorage(generalVolumeSize);
			}
		}
	}

	private static void setPrices(InstanceInput input, InstanceOutput output, Price price) {
		
		//calculate period
		double days = 0;
		if (input.getBeginning() != null && input.getEnd() != null) {
			days = diffInDays(input.getBeginning(), input.getEnd());
		} else {
			days = Constants.HOURS_IN_A_MONTH / 24;
		}

		
		if (input.getEnvironment().equals(Environment.DR_INACTIVE.toString())) {
			output.setInstanceType(Constants.STAND_BY_INSTANCE_TYPE);
			output.setInstanceVCPU(0);
			output.setInstanceMemory(0d);
			output.setInstanceSAPS(0);
		} else {
			output.setInstanceType(price.getInstanceType());
			output.setInstanceVCPU(price.getvCPU());
			output.setInstanceMemory(price.getMemory());
			output.setInstanceSAPS(price.getSaps());
			output.setComputeUnitPrice(price.getInstanceHourPrice());
			output.setUpfrontFee(price.getUpfrontFee() * input.getInstances());
			output.setComputeMonthlyPrice(
					price.getInstanceHourPrice() * Constants.HOURS_IN_A_MONTH * input.getInstances()
							* (price.getTermType().equals(TermType.OnDemand.name()) ? input.getMonthlyUtilization() : 1));
			output.setComputeTotalPrice(price.getInstanceHourPrice() * days * 24 * input.getInstances()
					* (input.getTermType().equals(TermType.OnDemand.name()) ? input.getMonthlyUtilization() : 1));
		}
		
		//Storage
		output.setStorageMonthlyPrice(StoragePricingCalculator.getStorageMonthlyPrice(input) * input.getInstances());
		output.setSnapshotMonthlyPrice(StoragePricingCalculator.getSnapshotMonthlyPrice(input) * input.getInstances());
		output.setArchiveLogsLocalBackupMonthlyPrice(
				StoragePricingCalculator.getArchiveLogsLocalBackupMonthlyPrice(input) * input.getInstances());

		//DataTransferPricingCalculator dataCalculator = new DataTransferPricingCalculator();
		//double dataTransferOutMonthlyPrice = dataCalculator.getDataTransferOutMonthlyPrice(Constants.dataTransfer);
	}

	private static void setEffectivePrice(List<Price> priceList) {
		for (Price somePrice : priceList) {
			if (somePrice.getTermType().equals(TermType.OnDemand.name()) || 
					somePrice.getPurchaseOption().equals(PurchaseOption.NO_UPFRONT.getColumnName())) {
				somePrice.setEfectivePrice(somePrice.getPricePerUnit());
				somePrice.setInstanceHourPrice(somePrice.getPricePerUnit());
			} else if (somePrice.getPurchaseOption().equals(PurchaseOption.PARTIAL_UPFRONT.getColumnName())
					|| somePrice.getPurchaseOption().equals(PurchaseOption.ALL_UPFRONT.getColumnName())) {
				if (somePrice.getPriceDescription().equals(UPFRONT_FEE)) {
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

	private static Price getUpfrontFee(List<Price> priceList, Price price) {
		for (Price somePrice : priceList) {
			if (somePrice.getSku().equals(price.getSku()) && somePrice.getPriceDescription().equals(UPFRONT_FEE)) {
				return somePrice;
			}
		}
		return null;
	}

	private static Price getInstanceHourFee(List<Price> priceList, Price price) {
		for (Price somePrice : priceList) {
			if (somePrice.getSku().equals(price.getSku()) && !somePrice.getPriceDescription().equals(UPFRONT_FEE)) {
				return somePrice;
			}
		}
		return null;
	}

	private static Price getBestPrice(List<Price> prices) {
		setEffectivePrice(prices);

		Price bestPrice = prices.get(0);

		for (Price price : prices) {
			if (price.getEfectivePrice() < bestPrice.getEfectivePrice()) {
				bestPrice = price;
			}
		}
		return bestPrice;
	}

	private static long diffInDays(String beginning, String end) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
			Date beginningDate = format.parse(beginning);
			Date endDate = format.parse(end);
			return diffInDays(beginningDate, endDate);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}

	private static long diffInDays(Date beginning, Date end) {

		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(beginning);
		LocalDate localBeginning = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
				calendar.get(Calendar.DAY_OF_MONTH));
		calendar.setTime(end);
		LocalDate localEnd = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
				calendar.get(Calendar.DAY_OF_MONTH));
		return DAYS.between(localBeginning, localEnd.plusDays(1));// last day // inclusive
	}

}
