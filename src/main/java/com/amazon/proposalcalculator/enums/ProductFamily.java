package com.amazon.proposalcalculator.enums;

public enum ProductFamily {

	Storage("Storage"), Data_Transfer("Data Transfer");

	private String name;

	ProductFamily(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
