package com.amazon.proposalcalculator.enums;

/**
 * Created by ravanini on 18/02/17.
 */
public enum BillingOption {

	ON_DEMAND("On Demand (No Contract)"),
	ONE_YRNU("1 Yr No Upfront Reserved"),
	ONE_YRPU("1 Yr Partial Upfront Reserved"),
	ONE_YRAU("1 Yr All Upfront Reserved"),
	THREE_YRPU("3 Yr Partial Upfront Reserved"),
	THREE_YRAU("3 Yr All Upfront Reserved"),
	THREE_YRNUC("3 Yr No Upfront Convertible"),
	THREE_YRPUC("3 Yr Partial Upfront Convertible"),
	THREE_YRAUC("3 Yr All Upfront Convertible");

    private String name;

    BillingOption(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
