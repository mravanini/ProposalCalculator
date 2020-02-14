package com.amazon.proposalcalculator.bean;

import java.util.ArrayList;
import java.util.Collection;

import com.amazon.proposalcalculator.enums.OfferingClass;

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
		if (leaseContractLength != null)
			sb.append("_").append(leaseContractLength.substring(0, 2).toUpperCase());
		if (purchaseOption != null)
			sb.append(justFirstLetters(purchaseOption));
		if (OfferingClass.Convertible.name().equalsIgnoreCase(offeringClass))
			sb.append("_").append(justFirstLetters(offeringClass));
		this.setName(sb.toString());
	}

	private String name;
	private String termType;
	private String leaseContractLength;
	private String purchaseOption;
	private String offeringClass;
	private double monthly;
	private double upfront;

	private double oneYrUpfront;
	private double threeYrsUpfront;

	private double monthlySupport;
	private double oneYrUpfrontSupport;
	private double threeYrsUpfrontSupport;

	private String upfrontFormula;
	private String monthlyFormula;

	private double threeYearTotal;
	private double discount;
	private boolean hasErrors;
	private Collection<InstanceOutput> output = new ArrayList<InstanceOutput>();

	private String justFirstLetters(String words) {
		StringBuilder result = new StringBuilder();
		String[] splitWords = words.split(" ");
		for (String string : splitWords) {
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

	public Collection<InstanceOutput> getOutput() {
		return output;
	}

	public void setOutput(Collection<InstanceOutput> output) {
		this.output = output;
	}

	public void addOutput(InstanceOutput output) {
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

	public boolean hasErrors() {
		return hasErrors;
	}

	public void setHasErrors(boolean hasErrors) {
		this.hasErrors = hasErrors;
	}

	@Override
	public int compareTo(Quote q) {
		return (int) Math.round(q.getThreeYearTotal() - this.getThreeYearTotal());
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getMonthly() {
		return monthly;
	}

	public void setMonthly(double monthly) {
		this.monthly = monthly;
	}

	public double getUpfront() {
		return upfront;
	}

	public void setUpfront(double upfront) {
		this.upfront = upfront;
	}

	public double getThreeYearTotal() {
		return threeYearTotal;
	}

	public void setThreeYearTotal(double threeYearTotal) {
		this.threeYearTotal = threeYearTotal;
	}

	public String getUpfrontFormula() {
		return upfrontFormula;
	}

	public void setUpfrontFormula(String upfrontFormula) {
		this.upfrontFormula = upfrontFormula;
	}

	public void setMonthlyFormula(String monthlyFormula) {
		this.monthlyFormula = monthlyFormula;
	}

	public String getMonthlyFormula() {
		return monthlyFormula;
	}

	public double getOneYrUpfront() {
		return oneYrUpfront;
	}

	public void setOneYrUpfront(double oneYrUpfront) {
		this.oneYrUpfront = oneYrUpfront;
	}

	public double getThreeYrsUpfront() {
		return threeYrsUpfront;
	}

	public void setThreeYrsUpfront(double threeYrsUpfront) {
		this.threeYrsUpfront = threeYrsUpfront;
	}

	public double getMonthlySupport() {
		return monthlySupport;
	}

	public void setMonthlySupport(double monthlySupport) {
		this.monthlySupport = monthlySupport;
	}

	public double getOneYrUpfrontSupport() {
		return oneYrUpfrontSupport;
	}

	public void setOneYrUpfrontSupport(double oneYrUpfrontSupport) {
		this.oneYrUpfrontSupport = oneYrUpfrontSupport;
	}

	public double getThreeYrsUpfrontSupport() {
		return threeYrsUpfrontSupport;
	}

	public void setThreeYrsUpfrontSupport(double threeYrsUpfrontSupport) {
		this.threeYrsUpfrontSupport = threeYrsUpfrontSupport;
	}
}
