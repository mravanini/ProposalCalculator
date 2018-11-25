package com.amazon.proposalcalculator.calculator;

import com.amazon.proposalcalculator.bean.InstanceInput;
import com.amazon.proposalcalculator.enums.Environment;
import com.amazon.proposalcalculator.enums.OperatingSystem;
import com.amazon.proposalcalculator.enums.SAPInstanceType;
import com.amazon.proposalcalculator.exception.PricingCalculatorException;
import com.amazon.proposalcalculator.utils.Constants;

/**
 * Created by ravanini on 18/02/17.
 */
public class ValidateSAPSpecificInputs {

//    static final String CPU_OR_SAPS_MUST_BE_PROVIDED = "CPU or SAPS must be provided.";
    static final String SAP_INSTANCE_TYPE_IS_A_MANDATORY_FIELD = "SAP Instance Type is a mandatory field for SAP calculations.";
//    static final String SAP_INSTANCES_MUST_NOT_BE_LINUX = "Open Source Linux is not supported for SAP workloads. Use SUSE or RHEL instead.";
    static final String ENVIRONMENT_IS_A_MANDATORY_FIELD = "Environment is a mandatory field";

    static void validate(InstanceInput input) {

        validateSAPInstanceType(input.getSapInstanceType());

        input.setSaps(fillSaps(input.getSaps()));

//        input.setArchiveLogsLocalBackup(fillArchiveLogsLocalBackup(input.getArchiveLogsLocalBackup()));

        validateMandatoryFields(input);

        input.setOperatingSystem(fillOperatingSystem(input.getOperatingSystem()));

//        validateOperatingSystemIsNotLinux(input.getOperatingSystem());

        fillSAPsCPURamOnDREnvironment(input);


    }

    private static void fillSAPsCPURamOnDREnvironment(InstanceInput input) {

    		/*
        if(Environment.DR_OPTIMIZED.name().equalsIgnoreCase(input.getEnvironment())){
            input.setCpu(input.getCpu() * Constants.DR_OPTIMIZED_PERCENTAGE);
            input.setMemory(input.getMemory() * Constants.DR_OPTIMIZED_PERCENTAGE);
            input.setSaps((int) (input.getSaps() * Constants.DR_OPTIMIZED_PERCENTAGE));
        } else if(Environment.DR_INACTIVE.name().equalsIgnoreCase(input.getEnvironment())
        		&& !SAPInstanceType.isHANA(input.getSapInstanceType())){
            input.setCpu(input.getCpu() * Constants.DR_INACTIVE_PERCENTAGE);
            input.setMemory(input.getMemory() * Constants.DR_INACTIVE_PERCENTAGE);
            input.setSaps((int) (input.getSaps() * Constants.DR_INACTIVE_PERCENTAGE));
        }
        */

    }

    private static String fillOperatingSystem(String operatingSystem) {
        if (OperatingSystem.BYOL.name().equalsIgnoreCase(operatingSystem)){
            return OperatingSystem.Linux.name();
        }
        return operatingSystem;
    }

    private static void validateSAPInstanceType(String sapInstanceType) {

        if(sapInstanceType == null){
            throw new PricingCalculatorException(SAP_INSTANCE_TYPE_IS_A_MANDATORY_FIELD);
        }
        //return SAPInstanceType.getSAPInstanceType(sapInstanceType).name();
    }

    private static Integer fillSaps(Integer saps) {
        return saps == null ? Integer.valueOf("0") : saps;
    }

//    private static Integer fillArchiveLogsLocalBackup(Integer archiveLogsLocalBackup) {
//        return (archiveLogsLocalBackup == null) ? Integer.valueOf("0") : archiveLogsLocalBackup;
//    }

//    private static void validateOperatingSystemIsNotLinux(String operatingSystem){
//        if(operatingSystem != null && OperatingSystem.Linux.name().equalsIgnoreCase(operatingSystem)){
//            throw new PricingCalculatorException(SAP_INSTANCES_MUST_NOT_BE_LINUX);
//        }
//    }

    private static void validateMandatoryFields(InstanceInput input) {
//        if (!validInteger(input.getSaps()) && !validDouble(input.getCpu())){
//            throw new PricingCalculatorException(CPU_OR_SAPS_MUST_BE_PROVIDED);
//        }

        if(input.getEnvironment() == null){
            throw new PricingCalculatorException(ENVIRONMENT_IS_A_MANDATORY_FIELD);
        }

    }

    private static boolean validInteger(Integer value){
        return value != null && value.intValue() != 0;
    }
    
    private static boolean validDouble(Double value){
        return value != null && value.doubleValue() != 0;
    }

}
