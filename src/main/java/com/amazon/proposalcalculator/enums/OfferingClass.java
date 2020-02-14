package com.amazon.proposalcalculator.enums;

import com.amazon.proposalcalculator.exception.PricingCalculatorException;

/**
 * Created by ravanini on 18/02/17.
 */
public enum OfferingClass {

	Convertible, Standard;

	public static OfferingClass getOfferingClass(String columnName) {

		for (OfferingClass offeringClass : OfferingClass.values()) {
			if (offeringClass.name().equalsIgnoreCase(columnName)) {
				return offeringClass;

			}
		}
		throw new PricingCalculatorException("Invalid Offering Class. Found = " + columnName);
	}
}
