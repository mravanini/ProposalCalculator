package com.amazon.proposalcalculator.enums;

import com.amazon.proposalcalculator.exception.PricingCalculatorException;

/**
 * Created by ravanini on 18/02/17.
 */
public enum SAPInstanceType {

    APPS,
    ANY_DB,
    HANA_OLTP,
    HANA_OLAP,
    HANA_B1;
	
	public static boolean isHANA(String sapInstanceType){
		return isHANA(SAPInstanceType.valueOf(sapInstanceType.toUpperCase()));
	}
	
	public static boolean isHANA(SAPInstanceType sapInstanceType){
		if (sapInstanceType.equals(HANA_OLTP) || sapInstanceType.equals(HANA_OLAP) 
				|| sapInstanceType.equals(HANA_B1)){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

    public static SAPInstanceType getSAPInstanceType(String columnName){

        for(SAPInstanceType type : SAPInstanceType.values()){
            if (type.name().equalsIgnoreCase(columnName)){
                return type;
            }
        }
        throw new PricingCalculatorException("Invalid SAP Instance Type. Found = " + columnName);
    }
}
