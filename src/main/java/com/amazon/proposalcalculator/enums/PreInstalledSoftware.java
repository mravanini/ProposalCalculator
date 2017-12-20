package com.amazon.proposalcalculator.enums;

import com.amazon.proposalcalculator.exception.PricingCalculatorException;

/**
 * Created by ravanini on 18/02/17.
 */
public enum PreInstalledSoftware {

    NA("NA"),
    SQLStandard("SQL Std"),
    SQLWeb("SQL Web"),
    SQLEnterprise("SQL Ent");

    private String columnName;
    PreInstalledSoftware(String columnName){
        this.columnName = columnName;
    }
    public String getColumnName() {
        return columnName;
    }


    public static PreInstalledSoftware getPreInstalledSoftware(String columnName){

        for(PreInstalledSoftware preInstalledSoftware : PreInstalledSoftware.values()){
            if (preInstalledSoftware.getColumnName().equalsIgnoreCase(columnName)){
                return preInstalledSoftware;

            }
        }
        throw new PricingCalculatorException("Invalid Pre Installed Software. Found = " + columnName);
    }
}
