package com.amazon.proposalcalculator.calculator;

import com.amazon.proposalcalculator.assemblies.DefaultOutputAssembly;
import com.amazon.proposalcalculator.bean.InstanceInput;
import com.amazon.proposalcalculator.bean.InstanceOutput;
import com.amazon.proposalcalculator.bean.Price;
import com.amazon.proposalcalculator.bean.Quote;
import com.amazon.proposalcalculator.enums.QuoteName;
import com.amazon.proposalcalculator.exception.PricingCalculatorException;
import com.amazon.proposalcalculator.reader.DataTransferReader;
import com.amazon.proposalcalculator.reader.EC2PriceListReader;
import com.amazon.proposalcalculator.utils.Constants;
import com.amazon.proposalcalculator.utils.SomeMath;
import com.amazon.proposalcalculator.writer.DefaultExcelWriter;
import com.ebay.xcelite.sheet.XceliteSheet;
import com.ebay.xcelite.writer.SheetWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.amazon.proposalcalculator.calculator.CalculatorPredicates.*;
import static java.time.temporal.ChronoUnit.DAYS;

public class Calculator {

	private final static Logger LOGGER = LogManager.getLogger();

	public static void calculate() {
        LOGGER.info("Calculating prices...");
        
        Quote quote = new Quote(QuoteName.YOUR_INPUT.getName());
		calculatePrice(quote);
		
        quote = new Quote("OnDemand", null, null, null);
		calculatePrice(quote);
		
        quote = new Quote("Reserved", "1yr", "No Upfront", "standard");
		calculatePrice(quote);
		
		quote = new Quote("Reserved", "1yr", "Partial Upfront", "standard");
		calculatePrice(quote);
		
		quote = new Quote("Reserved", "1yr", "All Upfront", "standard");
		calculatePrice(quote);	
		
		quote = new Quote("Reserved", "3yr", "Partial Upfront", "standard");
		calculatePrice(quote);
		
		quote = new Quote("Reserved", "3yr", "All Upfront", "standard");
		calculatePrice(quote);	
		
        quote = new Quote("Reserved", "3yr", "No Upfront", "convertible");
		calculatePrice(quote);
		
		quote = new Quote("Reserved", "3yr", "Partial Upfront", "convertible");
		calculatePrice(quote);
		
		quote = new Quote("Reserved", "3yr", "All Upfront", "convertible");
		calculatePrice(quote);
		
		calculateDiscount();
		
		DefaultExcelWriter.write();
	}

	private static void calculateDiscount() {
		Collections.sort(Constants.quotes);
		double higherValue = Constants.quotes.get(0).getThreeYearTotal();
		for (Quote q : Constants.quotes) {
			q.setDiscount((1-(q.getThreeYearTotal()/higherValue)));
		}
	}

	private static void calculatePrice(Quote quote) {
		//LOGGER.info("Calculating " + quote.getName());
		
		for (InstanceInput input
				: Constants.servers) {
			
			if (!quote.getName().equals(QuoteName.YOUR_INPUT.getName())) {
				input.setTermType(quote.getTermType());
				input.setLeaseContractLength(quote.getLeaseContractLength());
				input.setPurchaseOption(quote.getPurchaseOption());
				input.setOfferingClass(quote.getOfferingClass());
			}

			LOGGER.debug("Calculating instance: " + input.getDescription());
			InstanceOutput output = null;

			try {

				List<Price> possibleMatches = Constants.ec2PriceList.stream().filter(
						region(input)
						.and(ec2(input))
						.and(tenancy(input))
						.and(licenceModel(input))
						.and(operatingSystem(input))
						.and(preInstalledSw(input))
						.and(termType(input))
						.and(offeringClass(input))
						.and(leaseContractLength(input))
						.and(purchaseOption(input))
						.and(saps(input))
						.and(cpu(input))
						.and(memory(input))
						.and(newGeneration(input))
						.and(sapCertifiedInstances(input))

				).collect(Collectors.toList());

				Price price = getBestPrice(possibleMatches);
				
				output = DefaultOutputAssembly.from(input, price);
				
				
				double days = 0;
				if (input.getBeginning() != null && input.getEnd() != null) {
					days = diffInDays(input.getBeginning(), input.getEnd());
				} else {
					days = Constants.HOURS_IN_A_MONTH / 24;
				}
				
				output.setComputeTotalPrice(price.getInstanceHourPrice() * days * 24 * input.getInstances() * (input.getTermType().equals("OnDemand") ? input.getMonthlyUtilization() / 100 : 1));

				output.setStorageMonthlyPrice(StoragePricingCalculator.getStorageMonthlyPrice(input));
				output.setSnapshotMonthlyPrice(StoragePricingCalculator.getSnapshotMonthlyPrice(input));
				
				output.setUpfrontFee(price.getUpfrontFee());

			} catch (PricingCalculatorException pce){
				output.setErrorMessage(pce.getMessage());
			}
			//Constants.output.add(output);
			quote.addOutput(output);
			
			double months = "3yr".equals(output.getLeaseContractLength()) ? 36 : 12;
			double monthlyUpfront = output.getUpfrontFee() / months;
			double threeYearTotal = quote.getThreeYearTotal() + monthlyUpfront
					+ output.getComputeMonthlyPrice()
					+ output.getStorageMonthlyPrice()
					+ output.getSnapshotMonthlyPrice();
			quote.setThreeYearTotal(threeYearTotal);

			double upfront = quote.getUpfront() + output.getUpfrontFee();
			quote.setUpfront(upfront);
			
			DataTransferPricingCalculator dataCalculator = new DataTransferPricingCalculator();
			double dataTransferOutMonthlyPrice = dataCalculator.getDataTransferOutMonthlyPrice(Constants.dataTransfer);
			
			double monthly = quote.getMonthly() + 
								output.getComputeMonthlyPrice() +
								output.getStorageMonthlyPrice() +
								output.getSnapshotMonthlyPrice() +
								dataTransferOutMonthlyPrice;
			
			quote.setMonthly(monthly);
		}
		quote.setThreeYearTotal(quote.getThreeYearTotal()*36);
		Constants.quotes.add(quote);
		LOGGER.info(quote.getName() + ":" + quote.getThreeYearTotal());
	}
	
	private static void setEfectivePrice(List<Price> priceList) {
		for (Price somePrice : priceList) {
			if (somePrice.getTermType().equals("OnDemand") || somePrice.getPurchaseOption().equals("No Upfront")) {
				somePrice.setEfectivePrice(somePrice.getPricePerUnit());
				somePrice.setInstanceHourPrice(somePrice.getPricePerUnit());
			} else if (somePrice.getPurchaseOption().equals("Partial Upfront")
					|| somePrice.getPurchaseOption().equals("All Upfront")) {
				if (somePrice.getPriceDescription().equals("Upfront Fee")) {
					Price hourFee = getInstanceHourFee(priceList, somePrice);
					somePrice.setEfectivePrice(somePrice.getPricePerUnit()/(12*Integer.valueOf(somePrice.getLeaseContractLength().substring(0, 1))) + hourFee.getPricePerUnit());
					somePrice.setUpfrontFee(somePrice.getPricePerUnit());
					somePrice.setInstanceHourPrice(hourFee.getPricePerUnit());
				} else {
					Price upfrontFee = getUpfrontFee(priceList, somePrice);
					somePrice.setEfectivePrice(upfrontFee.getPricePerUnit()/(12*Integer.valueOf(somePrice.getLeaseContractLength().substring(0, 1))) + somePrice.getPricePerUnit());
					somePrice.setUpfrontFee(upfrontFee.getPricePerUnit());
					somePrice.setInstanceHourPrice(somePrice.getPricePerUnit());
				}
			}
		}
	}
	
	private static Price getUpfrontFee(List<Price> priceList, Price price) {
		for (Price somePrice : priceList) {
			if (somePrice.getSku().equals(price.getSku()) && somePrice.getPriceDescription().equals("Upfront Fee")) {
				return somePrice;
			}
		}
		return null;
	}
	
	private static Price getInstanceHourFee(List<Price> priceList, Price price) {
		for (Price somePrice : priceList) {
			if (somePrice.getSku().equals(price.getSku()) && !somePrice.getPriceDescription().equals("Upfront Fee")) {
				return somePrice;
			}
		}
		return null;
	}

	private static Price getBestPrice(List<Price> prices) {
		setEfectivePrice(prices);
		
		//Price bestPrice = new Price();
		//bestPrice.setEfectivePrice(1_000_000);
		Price bestPrice = prices.get(0);
		
		for (Price price : prices) {
			//LOGGER.info(price.getOperatingSystem());
			if (price.getEfectivePrice() < bestPrice.getEfectivePrice()) {
				bestPrice = price;
			}
		}
		return bestPrice;
	}
	
	private static long diffInDays(String beginning, String end) {
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

	private static long diffInDays(Date beginning, Date end) {

		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(beginning);
		LocalDate localBeginning = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
				calendar.get(Calendar.DAY_OF_MONTH));
		calendar.setTime(end);
		LocalDate localEnd = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
				calendar.get(Calendar.DAY_OF_MONTH));
		return DAYS.between(localBeginning, localEnd.plusDays(1));//last day inclusive
	}


}
