package com.amazon.proposalcalculator.calculator;

import com.amazon.proposalcalculator.bean.InstanceInput;
import com.amazon.proposalcalculator.enums.Region;
import com.amazon.proposalcalculator.exception.PricingCalculatorException;

/**
 * Created by ravanini on 18/02/17.
 */
public class ValidateRowInformation {

    public static void validate(InstanceInput input) {

        if (isSAPInputSheet(input)){
            validateSAPInput(input);
        }else {
            validateGeneralInput(input);
        }
    }

    private static void validateSAPInput(InstanceInput input) {

        validateDescription(input.getDescription());

        input.setRegion(validateRegion(input.getRegion()));

        input.setCpu(validateCPU(input.getCpu()));

        input.setMemory(validateMemory(input.getMemory()));

        input.setMonthlyUtilization(validateMonthlyUtilization(input.getMonthlyUtilization()));

        input.setStorage(validateStorage(input.getStorage()));




    }

    private static Integer validateStorage(Integer storage) {
        return (storage == null) ? Integer.valueOf("0") : storage;
    }

    private static Integer validateMonthlyUtilization(Integer monthlyUtilization) {
        return (monthlyUtilization == null) ? Integer.valueOf("100") : monthlyUtilization;
    }

    private static Double validateMemory(Double memory) {
        return (memory == null) ? Integer.valueOf("0") : memory;
    }

    private static Integer validateCPU(Integer cpu) {
        return (cpu == null) ? Integer.valueOf("0") : cpu;
    }

    private static void validateDescription(String description) {
        if (description == null || description.trim().isEmpty()){
            throw new PricingCalculatorException("Description is a mandatory field.");
        }
    }

    private static String validateRegion(String region) {
        //TODO VALIDATE REGIONS reading FILE FROM S3 - GET IDEA FROM Singh, Harpreet <batrahs@amazon.com>

        if (region == null)
            throw new PricingCalculatorException("Region is a mandatory field.");
        return Region.getRegion(region).getColumnName();
    }

    private static void validateGeneralInput(InstanceInput input) {

    }

    private static boolean isSAPInputSheet(InstanceInput input){

        return input.getSaps() != null
//                || input.getArchive Logs/Local Backup
//                || input.getSAP Instance Type != null
// TODO GET THESE NEW COLUMNS
        ;

    }


}
