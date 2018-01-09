package com.amazon.proposalcalculator.enums;

import com.amazon.proposalcalculator.exception.PricingCalculatorException;

/**
 * Created by ravanini on 18/02/17.
 */
public enum OperatingSystem {

    SUSE,
    Windows,
    RHEL,
    Linux,
    BYOL;

    public static OperatingSystem getOperatingSystem(String columnName){

        for(OperatingSystem operatingSystem : OperatingSystem.values()){
            if (operatingSystem.name().equalsIgnoreCase(columnName)){
                return operatingSystem;

            }
        }
        throw new PricingCalculatorException("Invalid Operating System. Found = " + columnName);
    }
}
