package com.amazon.proposalcalculator.enums;

import com.amazon.proposalcalculator.exception.PricingCalculatorException;

/**
 * Created by ravanini on 18/02/17.
 */
public enum PurchaseOption {

	NO_UPFRONT("No Upfront"), PARTIAL_UPFRONT("Partial Upfront"), ALL_UPFRONT("All Upfront");

	private String columnName;

	PurchaseOption(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnName() {
		return columnName;
	}

	public static PurchaseOption getPurchaseOption(String columnName) {

		if (columnName.equals("NoUpfront")) {
			columnName = "No Upfront";
		} else if (columnName.equals("PartialUpfront")) {
			columnName = "Partial Upfront";
		} else if (columnName.equals("AllUpfront")) {
			columnName = "All Upfront";
		}

		for (PurchaseOption purchaseOption : PurchaseOption.values()) {
			if (purchaseOption.getColumnName().equalsIgnoreCase(columnName)) {
				return purchaseOption;
			}
		}
		throw new PricingCalculatorException("Invalid type of Purchase Option. Found = " + columnName);
	}
}
