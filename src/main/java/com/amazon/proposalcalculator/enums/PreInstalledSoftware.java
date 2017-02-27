package com.amazon.proposalcalculator.enums;

import com.amazon.proposalcalculator.exception.PricingCalculatorException;

/**
 * Created by ravanini on 18/02/17.
 */
public enum PreInstalledSoftware {

    NA,
    SQLStandard,
    SQLWeb,
    SQLEnterprise;

    public static PreInstalledSoftware getPreInstalledSoftware(String columnName){

        for(PreInstalledSoftware preInstalledSoftware : PreInstalledSoftware.values()){
            if (preInstalledSoftware.name().equalsIgnoreCase(columnName)){
                return preInstalledSoftware;

            }
        }
        throw new PricingCalculatorException("Invalid Pre Installed Software. Found = " + columnName);
    }
}
