package com.amazon.proposalcalculator.calculator;

import com.amazon.proposalcalculator.assemblies.DefaultOutputAssembly;
import com.amazon.proposalcalculator.bean.DefaultInput;
import com.amazon.proposalcalculator.bean.DefaultOutput;
import com.amazon.proposalcalculator.bean.Price;
import com.amazon.proposalcalculator.exception.PricingCalculatorException;
import com.amazon.proposalcalculator.utils.Constants;
import com.amazon.proposalcalculator.writer.DefaultExcelWriter;
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
		Constants.output = new ArrayList<DefaultOutput>();
        LOGGER.info("Calculating prices...");

		for (DefaultInput input
				: Constants.servers) {

			LOGGER.debug("Calculating instance: " + input.getDescription());
			DefaultOutput output = DefaultOutputAssembly.from(input);

			try {

				List<Price> possibleMatches = Constants.ec2PriceList.stream().filter(
						region(input)
								.and(cpuTolerance(input))
								.and(memory(input))
								.and(termType(input))

								.and(offeringClass(input))
								.and(leaseContractLength(input))
								.and(purchaseOption(input))

								.and(operatingSystem(input))
								.and(tenancy(input))
				).collect(Collectors.toList());

				Price price = getBestPrice(possibleMatches);
				output.setInstanceType(price.getInstanceType());
				output.setInstanceMemory(price.getMemory());
				output.setInstanceVCPU(price.getvCPU());
				output.setComputeUnitPrice(price.getPricePerUnit());
				output.setComputeMonthlyPrice(
						price.getPricePerUnit() * Constants.hoursInAMonth * input.getCpuUsage() / 100 * input.getInstances());
				long days = diffInDays(input.getBeginning(), input.getEnd());
				output.setComputeTotalPrice(price.getPricePerUnit() * days * 24 * input.getCpuUsage() / 100 * input.getInstances());

				output.setStorageMonthlyPrice(StoragePricingCalculator.getStorageMonthlyPrice(input));
				output.setSnapshotMonthlyPrice(StoragePricingCalculator.getSnapshotMonthlyPrice(input));

			} catch (PricingCalculatorException pce){
				output.setErrorMessage(pce.getMessage());
			}
			Constants.output.add(output);

		}
		DefaultExcelWriter.write();
	}




	private static Price getBestPrice(List<Price> prices) {
		Price bestPrice = new Price();
		for (Price price : prices) {
			if (bestPrice.getPricePerUnit() == 0 || price.getPricePerUnit() < bestPrice.getPricePerUnit()) {
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
			// TODO Auto-generated catch block
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
