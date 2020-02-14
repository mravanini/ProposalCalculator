package com.amazon.proposalcalculator.enums;

public enum QuoteName {

	YOUR_INPUT("Your_Input");

	QuoteName(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String name;

}
