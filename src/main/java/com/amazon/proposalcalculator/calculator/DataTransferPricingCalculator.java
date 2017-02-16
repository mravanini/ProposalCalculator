package com.amazon.proposalcalculator.calculator;

import com.amazon.proposalcalculator.bean.DataTransferInput;
import com.amazon.proposalcalculator.bean.InstanceInput;
import com.amazon.proposalcalculator.bean.Price;
import com.amazon.proposalcalculator.enums.VolumeType;
import com.amazon.proposalcalculator.exception.PricingCalculatorException;
import com.amazon.proposalcalculator.utils.Constants;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.amazon.proposalcalculator.calculator.CalculatorPredicates.*;

public class DataTransferPricingCalculator {

	public static double getDataTransferOutMonthlyPrice(DataTransferInput input) {
		if (input.getDataTransferOut() == 0) {
			return 0;
		}
		List<Price> listPrice = getPricePerUnit(region(input).and(dataTransferOut(input)));
		
		long dataTransferOut = input.getDataTransferOut();
		double dataTransferOutTotal = 0;
		for (Price price : listPrice) {
			long rangeSize = price.getEndingRangeAsLong() <= dataTransferOut ? price.getEndingRangeAsLong() - price.getStartingRangeAsLong() : input.getDataTransferOut() - price.getStartingRangeAsLong();
			dataTransferOut = dataTransferOut - rangeSize;
			double rangePrice = rangeSize * price.getPricePerUnit();
			dataTransferOutTotal = dataTransferOutTotal + rangePrice;
		}

		return dataTransferOutTotal;
	}

	private static List<Price> getPricePerUnit(Predicate<Price> p) {
		return Constants.ec2PriceList.stream().filter(p).collect(Collectors.toList());
	}

}
