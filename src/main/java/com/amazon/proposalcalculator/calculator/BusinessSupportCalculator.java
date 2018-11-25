package com.amazon.proposalcalculator.calculator;

//Business
//10% of monthly AWS usage for the first $0–$10K
//7% of monthly AWS usage from $10K–$80K
//5% of monthly AWS usage from $80K–$250K
//3% of monthly AWS usage over $250K

public class BusinessSupportCalculator {
	
	private static BusinessSupportCalculator instance;
	
	private BusinessSupportCalculator() {
		
	}
	
	public static BusinessSupportCalculator getInstance() {
		if (instance == null)
			instance = new BusinessSupportCalculator();
		return instance;
	}
	
	public double calculateUpfrontSupport(double upfrontFee, double monthlyFee) {
		double totalSupport = calculateSupport(upfrontFee + monthlyFee);
		double monthlySupport = calculateMonthlySupport(monthlyFee);
		double upfrontSupport = totalSupport - monthlySupport;
		return upfrontSupport;
	}
	
	public double calculateMonthlySupport(double monthlyFee) {
		double total = calculateSupport(monthlyFee);
		return total;
	}
	
	private double calculateSupport(double monthlyFee) {
		double total = 0;
		if (monthlyFee <= 10000) {
			total = monthlyFee * 0.1;
		} else if (monthlyFee > 10000 && monthlyFee <= 80000) {
			total = (10000 * 0.1) + ((monthlyFee - 10000) * 0.07);
		} else if (monthlyFee > 80000 && monthlyFee <= 250000) {
			total = (10000 * 0.1) + ((80000 - 10000) * 0.07) +  ((monthlyFee - 80000) * 0.05);
		} else if (monthlyFee > 250000) {
			total = (10000 * 0.1) + ((80000 - 10000) * 0.07) +  ((250000 - 80000) * 0.05) +  ((monthlyFee - 250000) * 0.03);
		}
		if (total < 100)
			total = 100;
		return total;
	}
	
	public static void main(String[] args) {
		BusinessSupportCalculator sc = new BusinessSupportCalculator();
		double mensal = 12529.00;
		double calculateMonthlySupport = sc.calculateMonthlySupport(mensal);
		System.out.println("Mensal: " + calculateMonthlySupport);
		double upfront = 295669.00;
		double calculateUpfrontSupport = sc.calculateUpfrontSupport(upfront, mensal);
		System.out.println("Upfront: " + calculateUpfrontSupport);
	}

}
