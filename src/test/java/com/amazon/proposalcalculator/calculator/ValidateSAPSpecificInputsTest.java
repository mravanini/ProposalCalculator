package com.amazon.proposalcalculator.calculator;

import com.amazon.proposalcalculator.bean.InstanceInput;
import com.amazon.proposalcalculator.enums.Environment;
import com.amazon.proposalcalculator.enums.OperatingSystem;
import com.amazon.proposalcalculator.enums.SAPInstanceType;
import com.amazon.proposalcalculator.exception.PricingCalculatorException;
import com.amazon.proposalcalculator.utils.Constants;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

/**
 * Created by ravanini on 24/02/17.
 */
public class ValidateSAPSpecificInputsTest {


    @Test(expected = PricingCalculatorException.class)
    public void testSAPInstanceTypeNull(){
        InstanceInput input = new InstanceInput();
        try {
            ValidateSAPSpecificInputs.validate(input);
        }catch (PricingCalculatorException pce){
            assertEquals(ValidateSAPSpecificInputs.SAP_INSTANCE_TYPE_IS_A_MANDATORY_FIELD, pce.getMessage());
            throw pce;
        }
    }

//    @Test(expected = PricingCalculatorException.class)
//    public void testSAPsNullAndCPUZero(){
//        InstanceInput input = new InstanceInput();
//        input.setSapInstanceType(SAPInstanceType.ANY_DB.name());
//        input.setCpu(0D);
//        try {
//            ValidateSAPSpecificInputs.validate(input);
//        }catch (PricingCalculatorException pce){
//            assertEquals(ValidateSAPSpecificInputs.CPU_OR_SAPS_MUST_BE_PROVIDED, pce.getMessage());
//            throw pce;
//        }
//    }

    @Test
    public void testSAPsNullArchiveLogsNull(){
        InstanceInput input = new InstanceInput();
        input.setSapInstanceType(SAPInstanceType.ANY_DB.name());
        input.setCpu(20D);

        ValidateSAPSpecificInputs.validate(input);

        assertNull(input.getSaps());
        assertNull(input.getArchiveLogsLocalBackup());
    }

    @Test
    public void testAllValidValues(){
        InstanceInput input = new InstanceInput();
        input.setSapInstanceType(SAPInstanceType.APPS.name());
        input.setSaps(20);
        input.setArchiveLogsLocalBackup(50);

        ValidateSAPSpecificInputs.validate(input);

        assertEquals(SAPInstanceType.APPS.name(), input.getSapInstanceType());
        assertEquals(20, input.getSaps());
        assertEquals(50, input.getArchiveLogsLocalBackup());
    }

//    @Test (expected = PricingCalculatorException.class)
//    public void testLinuxSO(){
//        InstanceInput input = new InstanceInput();
//        input.setSaps(20);
//        input.setSapInstanceType(SAPInstanceType.APPS.name());
//        input.setOperatingSystem(OperatingSystem.Linux.name());
//
//        try {
//            ValidateSAPSpecificInputs.validate(input);
//        }catch (PricingCalculatorException pce){
//            assertEquals(ValidateSAPSpecificInputs.SAP_INSTANCES_MUST_NOT_BE_LINUX, pce.getMessage());
//            throw pce;
//        }
//    }

    @Test
    public void testDROptimizedPercentage(){
        InstanceInput input = new InstanceInput();
        input.setSaps(21);
        input.setCpu(8.0);
        input.setMemory(16.0);
        input.setEnvironment(Environment.DR_OPTIMIZED.name());
        input.setSapInstanceType(SAPInstanceType.APPS.name());
        input.setOperatingSystem(OperatingSystem.SUSE.name());

        ValidateSAPSpecificInputs.validate(input);

        assertEquals(5, input.getSaps());
        assertEquals(2d, input.getCpu());
        assertEquals(4d, input.getMemory());

    }

    @Test
    public void testDRInactivePercentage(){
        InstanceInput input = new InstanceInput();
        input.setSaps(21);
        input.setCpu(8.0);
        input.setMemory(16.0);
        input.setEnvironment(Environment.DR_INACTIVE.name());
        input.setSapInstanceType(SAPInstanceType.APPS.name());
        input.setOperatingSystem(OperatingSystem.SUSE.name());

        ValidateSAPSpecificInputs.validate(input);

        assertEquals(0, input.getSaps());
        assertEquals(0d, input.getCpu());
        assertEquals(0d, input.getMemory());

    }
}
