package com.amazon.proposalcalculator.enums;

import com.amazon.proposalcalculator.exception.PricingCalculatorException;

/**
 * Created by ravanini on 18/02/17.
 */
public enum Tenancy {

    Shared,
    Dedicated;

    public static Tenancy getTenancy(String columnName){

        for(Tenancy tenancy : Tenancy.values()){
            if (tenancy.name().equalsIgnoreCase(columnName)){
                return tenancy;

            }
        }
        throw new PricingCalculatorException("Invalid Tenancy. Found = " + columnName);
    }
}
