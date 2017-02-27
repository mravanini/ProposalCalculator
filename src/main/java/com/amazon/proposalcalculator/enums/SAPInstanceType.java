package com.amazon.proposalcalculator.enums;

import com.amazon.proposalcalculator.exception.PricingCalculatorException;

/**
 * Created by ravanini on 18/02/17.
 */
public enum SAPInstanceType {

    APPS,
    ANY_DB,
    HANA_OLTP,
    HANA_OLAP;

    public static SAPInstanceType getSAPInstanceType(String columnName){

        for(SAPInstanceType type : SAPInstanceType.values()){
            if (type.name().equalsIgnoreCase(columnName)){
                return type;
            }
        }
        throw new PricingCalculatorException("Invalid SAP Instance Type. Found = " + columnName);
    }
}
