package com.amazon.proposalcalculator.bean;

import java.util.ArrayList;
import java.util.Collection;

import com.ebay.xcelite.annotations.Column;

public class Quote implements Comparable<Quote> {
	
	public Quote(String name) {
		this.name = name;
	}
	
	public Quote(String termType, String leaseContractLength, String purchaseOption, String offeringClass) {
		this.termType = termType;
		this.leaseContractLength = leaseContractLength;
		this.purchaseOption = purchaseOption;
		this.offeringClass = offeringClass;
		
		StringBuilder sb = new StringBuilder();
		sb.append(termType);
		if (leaseContractLength != null) sb.append(" ").append(leaseContractLength.substring(0,2).toUpperCase());
		if (purchaseOption != null) sb.append(justFirstLetters(purchaseOption));
		if ("convertible".equals(offeringClass)) sb.append(" ").append(justFirstLetters(offeringClass));
		this.setName(sb.toString());
	}

	private String name;
	private String termType;
	private String leaseContractLength;
	private String purchaseOption;
	private String offeringClass;
	private double value;
	private double discount;
	private Collection<DefaultOutput> output = new ArrayList<DefaultOutput>();
	
	private String justFirstLetters(String words) {
		StringBuilder result = new StringBuilder();
		String[] splitedWords = words.split(" ");
		for (String string : splitedWords) {
			result.append(string.substring(0, 1).toUpperCase());
		}
		return result.toString();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public Collection<DefaultOutput> getOutput() {
		return output;
	}
	public void setOutput(Collection<DefaultOutput> output) {
		this.output = output;
	}
	public void addOutput(DefaultOutput output) {
		this.output.add(output);
	}
	public String getTermType() {
		return termType;
	}
	public void setTermType(String termType) {
		this.termType = termType;
	}
	public String getLeaseContractLength() {
		return leaseContractLength;
	}
	public void setLeaseContractLength(String leaseContractLength) {
		this.leaseContractLength = leaseContractLength;
	}
	public String getPurchaseOption() {
		return purchaseOption;
	}
	public void setPurchaseOption(String purchaseOption) {
		this.purchaseOption = purchaseOption;
	}
	public String getOfferingClass() {
		return offeringClass;
	}
	public void setOfferingClass(String offeringClass) {
		this.offeringClass = offeringClass;
	}

	@Override
	public int compareTo(Quote q) {
		return (int) Math.round(q.getValue() - this.getValue());
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

}
