package com.amazon.proposalcalculator.exception;

/**
 * Created by ravanini on 22/01/17.
 */
public class PricingCalculatorException extends RuntimeException {

	public PricingCalculatorException(String msg) {
		super(msg);
	}

	public PricingCalculatorException(String msg, Throwable t) {
		super(msg, t);
	}

}
