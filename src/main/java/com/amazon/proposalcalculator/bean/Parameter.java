package com.amazon.proposalcalculator.bean;

import com.ebay.xcelite.annotations.Column;

public class Parameter {
	
	@Column(name = "Support")
	private String support;

	public String getSupport() {
		return support;
	}

	public void setSupport(String support) {
		this.support = support;
	}
	

}
