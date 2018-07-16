package com.amazon.proposalcalculator.calculator;

//Enterprise
//10% of monthly AWS usage for the first $0–$150K
//7% of monthly AWS usage from $150K–$500K
//5% of monthly AWS usage from $500K–$1M
//3% of monthly AWS usage over $1M
public class EnterpriseSupportCalculator {
	
	public double calculateUpfrontSupport(double upfrontFee, double monthlyFee) {
		double totalSupport = calculateSupport(upfrontFee + monthlyFee);
		double monthlySupport = calculateMonthlySupport(monthlyFee);
		double upfrontSupport = totalSupport - monthlySupport;
		return upfrontSupport;
	}
	
	public double calculateMonthlySupport(double monthlyFee) {
		double total = calculateSupport(monthlyFee);
		if (total < 15000)
			total = 15000;
		return total;
	}
	
	private double calculateSupport(double monthlyFee) {
		double total = 0;
		if (monthlyFee <= 150000) {
			total = monthlyFee * 0.1;
		} else if (monthlyFee > 150000 && monthlyFee <= 500000) {
			total = (150000 * 0.1) + ((monthlyFee - 150000) * 0.07);
		} else if (monthlyFee > 500000 && monthlyFee <= 1000000) {
			total = (150000 * 0.1) + ((500000 - 150000) * 0.07) +  ((monthlyFee - 500000) * 0.05);
		} else if (monthlyFee > 1000000) {
			total = (150000 * 0.1) + ((500000 - 150000) * 0.07) +  ((1000000 - 500000) * 0.05) +  ((monthlyFee - 1000000) * 0.03);
		}
		return total;
	}
	
	public static void main(String[] args) {
		EnterpriseSupportCalculator sc = new EnterpriseSupportCalculator();
		double mensal = 12529.00;
		double calculateMonthlySupport = sc.calculateMonthlySupport(mensal);
		System.out.println("Mensal: " + calculateMonthlySupport);
		double upfront = 295669.00;
		double calculateUpfrontSupport = sc.calculateUpfrontSupport(upfront, mensal);
		System.out.println("Upfront: " + calculateUpfrontSupport);
	}

}
