package com.amazon.proposalcalculator.calculator;

import com.amazon.proposalcalculator.bean.InstanceInput;
import com.amazon.proposalcalculator.enums.OperatingSystem;
import com.amazon.proposalcalculator.enums.SAPInstanceType;
import com.amazon.proposalcalculator.exception.PricingCalculatorException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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

    @Test(expected = PricingCalculatorException.class)
    public void testSAPsNullAndCPUZero(){
        InstanceInput input = new InstanceInput();
        input.setSapInstanceType(SAPInstanceType.ANY_DB.name());
        input.setCpu(0);
        try {
            ValidateSAPSpecificInputs.validate(input);
        }catch (PricingCalculatorException pce){
            assertEquals(ValidateSAPSpecificInputs.CPU_OR_SAPS_MUST_BE_PROVIDED, pce.getMessage());
            throw pce;
        }
    }

    @Test
    public void testSAPsNullArchiveLogsNull(){
        InstanceInput input = new InstanceInput();
        input.setSapInstanceType(SAPInstanceType.ANY_DB.name());
        input.setCpu(20);

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
        assertEquals(20, input.getSaps().intValue());
        assertEquals(50, input.getArchiveLogsLocalBackup().intValue());
    }

    @Test (expected = PricingCalculatorException.class)
    public void testLinuxSO(){
        InstanceInput input = new InstanceInput();
        input.setSaps(20);
        input.setSapInstanceType(SAPInstanceType.APPS.name());
        input.setOperatingSystem(OperatingSystem.Linux.name());

        try {
            ValidateSAPSpecificInputs.validate(input);
        }catch (PricingCalculatorException pce){
            assertEquals(ValidateSAPSpecificInputs.SAP_INSTANCES_MUST_NOT_BE_LINUX, pce.getMessage());
            throw pce;
        }
    }
}
