package com.amazon.proposalcalculator.enums;

import com.amazon.proposalcalculator.exception.PricingCalculatorException;

/**
 * Created by ravanini on 18/02/17.
 */
public enum LeaseContractLength {

    ONE_YEAR("1yr"),
    THREE_YEARS("3yr");

    private String columnName;

    LeaseContractLength(String columnName){
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }

    public static LeaseContractLength getLeaseContractLength(String columnName) {
    	
    		columnName = columnName.replace(" ", "").toLowerCase();

        for(LeaseContractLength leaseContractLength : LeaseContractLength.values()){
            if (leaseContractLength.getColumnName().equalsIgnoreCase(columnName)){
                return leaseContractLength;
            }
        }

        throw new PricingCalculatorException("Invalid type of Lease Contract Length. Found = " + columnName);
    }

}
