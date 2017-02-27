package com.amazon.proposalcalculator.enums;

import com.amazon.proposalcalculator.exception.PricingCalculatorException;

/**
 * Created by ravanini on 18/02/17.
 */
public enum Region {


    //TODO remove this ENUM after better implemenation is done on retrieving these informations
    US_EAST_OHIO("US East (Ohio)"),
    US_WEST_OREGON("US West (Oregon)"),
    US_WEST_N_CALIFORNIA("US West (N. California)"),
    US_EAST_N_VIRGINIA("US East (N. Virginia)"),
    ASIA_PACIFIC_MUMBAI("Asia Pacific (Mumbai)"),
    ASIA_PACIFIC_SEOUL("Asia Pacific (Seoul)"),
    ASIA_PACIFIC_SINGAPORE("Asia Pacific (Singapore)"),
    ASIA_PACIFIC_SYDNEY("Asia Pacific (Sydney)"),
    ASIA_PACIFIC_TOKIO("Asia Pacific (Tokyo)"),
    GOV_CLOUD("AWS GovCloud (US)"),
    CANADA_CENTRAL("Canada (Central)"),
    CHINA_BEIJING("China (Beijing)"),
    //CHINA_NINGXIA("China (Ningxia)"),
    EU_FRANKFURT("EU (Frankfurt)"),
    EU_IRELAND("EU (Ireland)"),
    EU_LONDON( "EU (London)"),
    //EU_PARIS("EU (Paris)"),
    SOUTH_AMERICA_SAO_PAULO("South America (Sao Paulo)")
    ;

    private String columnName;

    Region(String columnName){
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }

    public static Region getRegion(String columnName){

        for(Region region : Region.values()){
            if (region.getColumnName().equalsIgnoreCase(columnName)){
                return region;
            }
        }
        throw new PricingCalculatorException("Invalid Region. Found = " + columnName);
    }
}
