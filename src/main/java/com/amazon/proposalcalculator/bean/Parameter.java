package com.amazon.proposalcalculator.bean;

import com.ebay.xcelite.annotations.Column;

public class Parameter {

	@Column(name = "Support")
	private String support;

	@Column(name = "Data Transfer Out (GB/Month)")
	private Long dataTransferOut;

	@Column(name = "Region")
	private String region;

	public Long getDataTransferOut() {
		return dataTransferOut;
	}

	public void setDataTransferOut(Long dataTransferOut) {
		this.dataTransferOut = dataTransferOut;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getSupport() {
		return support;
	}

	public void setSupport(String support) {
		this.support = support;
	}

}
