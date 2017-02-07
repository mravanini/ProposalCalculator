package com.amazon.proposalcalculator.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SomeMath {
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
}

//OnDemand: 732.5
//Reserved 1yr No Upfront:7410.288437499999
//Reserved 1yr Partial Upfront:6572.883499999999
//Reserved 1yr All Upfront:6231.09
//Reserved 3yr Partial Upfront:5353.403812500001
//Reserved 3yr All Upfront:4851.0
//Reserved 3yr No Upfront convertible:7215.261624999999
//Reserved 3yr Partial Upfront convertible:6191.202875
//Reserved 3yr All Upfront convertible:6068.5
