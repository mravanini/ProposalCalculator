package com.amazon.proposalcalculator.calculator;

import com.amazon.proposalcalculator.assemblies.DefaultOutputAssembly;
import com.amazon.proposalcalculator.bean.DefaultInput;
import com.amazon.proposalcalculator.bean.DefaultOutput;
import com.amazon.proposalcalculator.bean.Price;
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

		for (DefaultInput server : Constants.servers) {
			LOGGER.debug("Calculating instance: " + server.getDescription());

			List<Price> possibleMatches = Constants.ec2PriceList.stream().filter(
					 region(server)
				.and(cpuTolerance(server))
				.and(memory(server))
				.and(termType(server))
				
				.and(offeringClass(server))
				.and(leaseContractLength(server))
				.and(purchaseOption(server))
				
				.and(operatingSystem(server))
				.and(tenancy(server))
			).collect(Collectors.toList());
			
			Price price = getBestPrice(possibleMatches); 
			DefaultOutput output = DefaultOutputAssembly.from(server);
			output.setInstanceType(price.getInstanceType());
			output.setInstanceMemory(price.getMemory());
			output.setInstanceVCPU(price.getvCPU());
			output.setComputeUnitPrice(price.getPricePerUnit());
			output.setComputeMonthlyPrice(
					price.getPricePerUnit() * Constants.hoursInAMonth * server.getUsage() / 100 * server.getInstances());
			long days = diffInDays(server.getBeginning(), server.getEnd());
			output.setComputeTotalPrice(price.getPricePerUnit() * days * 24 * server.getUsage() / 100 * server.getInstances());

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
