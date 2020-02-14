package com.amazon.proposalcalculator.enums;

import com.amazon.proposalcalculator.exception.PricingCalculatorException;

/**
 * Created by ravanini on 22/01/17.
 */
public enum VolumeType {

	Magnetic("Magnetic"), General_Purpose("General Purpose"), Provisioned_IOPS("Provisioned IOPS"),
	Throughput_Optimized_HDD("Throughput Optimized HDD"), Cold_HDD("Cold HDD");

	private String columnName;

	VolumeType(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnName() {
		return columnName;
	}

	public static VolumeType getVolumeType(String columnName) {

		for (VolumeType type : VolumeType.values()) {
			if (type.getColumnName().equalsIgnoreCase(columnName)) {
				return type;
			}
		}

		throw new PricingCalculatorException("Invalid type of EBS. Found = " + columnName);
	}
}
