package com.amazon.proposalcalculator.calculator;

import com.amazon.proposalcalculator.bean.InstanceInput;
import com.amazon.proposalcalculator.enums.Region;
import com.amazon.proposalcalculator.enums.VolumeType;
import com.amazon.proposalcalculator.exception.PricingCalculatorException;
import com.amazon.proposalcalculator.utils.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;

/**
 * Created by ravanini on 18/02/17.
 */
public class FillDefaultValues {

    private final static Logger LOGGER = LogManager.getLogger();

    public static void fill(Collection<InstanceInput> servers) {

        LOGGER.info("Filling in default values...");

        for (InstanceInput input : servers) {

            try {
                fillCommonInputs(input);

                if (isSAPInputSheet(input)){
                    fillSpecificSAPInput(input);
                }else {
                    fillSpecificGeneralInput(input);
                }
            }catch (PricingCalculatorException pce){
                input.setErrorMessage(pce.getMessage());
            }
        }
    }

    private static void fillCommonInputs(InstanceInput input){
        validateDescription(input.getDescription());

        input.setRegion(validateRegion(input.getRegion()));

        input.setCpu(validateCPU(input.getCpu()));

        input.setMemory(validateMemory(input.getMemory()));

        input.setMonthlyUtilization(validateMonthlyUtilization(input.getMonthlyUtilization()));

        input.setStorage(validateStorage(input.getStorage()));

        input.setVolumeType(fillVolumeType(input.getVolumeType()));




    }

    private static String fillVolumeType(String volumeType) {
        return volumeType != null ? VolumeType.getVolumeType(volumeType).getColumnName() : VolumeType.General_Purpose.getColumnName();
    }

    private static void fillSpecificGeneralInput(InstanceInput input) {

    }

    private static void fillSpecificSAPInput(InstanceInput input) {


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

    private static boolean isSAPInputSheet(InstanceInput input){

        return input.getSaps() != null
                || input.getArchiveLogsLocalBackup() != null
                || input.getSapInstanceType() != null
        ;
    }
}
