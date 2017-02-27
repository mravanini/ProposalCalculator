package com.amazon.proposalcalculator.enums;

import com.amazon.proposalcalculator.exception.PricingCalculatorException;

/**
 * Created by ravanini on 18/02/17.
 */
public enum TermType {

    OnDemand,
    Reserved;

    public static TermType getTermType(String columnName){

        for(TermType type : TermType.values()){
            if (type.name().equalsIgnoreCase(columnName)){
                return type;

            }
        }
        throw new PricingCalculatorException("Invalid TermType. Found = " + columnName);
    }

}
