package com.amazon.proposalcalculator.calculator;

import com.amazon.proposalcalculator.bean.InstanceInput;
import com.amazon.proposalcalculator.enums.*;
import com.amazon.proposalcalculator.exception.PricingCalculatorException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by ravanini on 19/02/17.
 */
public class ValidateCommonInputsTest {

    @Test(expected = PricingCalculatorException.class)
    public void testValidateDescriptionException(){
        InstanceInput input = new InstanceInput();
        try {
            ValidateCommonInputs.validate(input);
        }catch (PricingCalculatorException pce){
            assertEquals(ValidateCommonInputs.DESCRIPTION_IS_A_MANDATORY_FIELD, pce.getMessage());
            throw pce;
        }
    }

    @Test(expected = PricingCalculatorException.class)
    public void testValidateRegionException(){
        InstanceInput input = new InstanceInput();
        input.setDescription("Server1");
        try {
            ValidateCommonInputs.validate(input);
        }catch (PricingCalculatorException pce){
            assertEquals(ValidateCommonInputs.REGION_IS_A_MANDATORY_FIELD, pce.getMessage());
            throw pce;
        }
    }

    @Test(expected = PricingCalculatorException.class)
    public void testValidateRegionBogusException(){
        InstanceInput input = new InstanceInput();
        input.setDescription("Server1");
        String bogusRegion = "Bogus Region Name";
        input.setRegion(bogusRegion);
        try {
            ValidateCommonInputs.validate(input);
        }catch (PricingCalculatorException pce){
            assertEquals("Invalid Region. Found = " + bogusRegion, pce.getMessage());
            throw pce;
        }
    }

    @Test(expected = PricingCalculatorException.class)
    public void testValidateOperatingSystemException(){
        InstanceInput input = new InstanceInput();
        input.setDescription("Server1");
        input.setRegion(Region.SOUTH_AMERICA_SAO_PAULO.getColumnName());
        try {
            ValidateCommonInputs.validate(input);
        }catch (PricingCalculatorException pce){
            assertEquals(ValidateCommonInputs.OPERATING_SYSTEM_IS_MANDATORY, pce.getMessage());
            throw pce;
        }
    }

    @Test
    public void testValidateCPUValidValue(){
        InstanceInput input = buildInstanceInput();
        input.setCpu(4D);
        ValidateCommonInputs.validate(input);
        assertEquals(4, input.getCpu());
    }

    @Test
    public void testValidateCPUToleranceNullValue(){
        InstanceInput input = buildInstanceInput();
        ValidateCommonInputs.validate(input);
        assertEquals(0d, input.getCpuTolerance(), 0);
    }

    @Test
    public void testValidateCPUToleranceValidValue(){
        InstanceInput input = buildInstanceInput();
        input.setCpuTolerance(20d);
        ValidateCommonInputs.validate(input);
        assertEquals(20d, input.getCpuTolerance(), 0);
    }


    @Test
    public void testValidateMemoryValidValue(){
        InstanceInput input = buildInstanceInput();
        input.setMemory(1024d);
        ValidateCommonInputs.validate(input);
        assertEquals(1024d, input.getMemory(), 0);
    }

    @Test
    public void testValidateMemoryToleranceValidValue(){
        InstanceInput input = buildInstanceInput();
        input.setMemoryTolerance(20d);
        ValidateCommonInputs.validate(input);
        assertEquals(20d, input.getMemoryTolerance(), 0);
    }

    @Test
    public void testValidateMontlyUtilizationNullValue(){
        InstanceInput input = buildInstanceInput();
        ValidateCommonInputs.validate(input);
        assertEquals(1, input.getMonthlyUtilization());
    }

    @Test
    public void testValidateMontlyUtilizationValidValue(){
        InstanceInput input = buildInstanceInput();
        input.setMonthlyUtilization(0.7);
        ValidateCommonInputs.validate(input);
        assertEquals(0.7d, input.getMonthlyUtilization());
    }

    @Test
    public void testValidateStorageValidValue(){
        InstanceInput input = buildInstanceInput();
        input.setStorage(30);
        ValidateCommonInputs.validate(input);
        assertEquals(30, input.getStorage());
    }

    @Test
    public void testValidateVolumeTypeNullValue(){
        InstanceInput input = buildInstanceInput();
        ValidateCommonInputs.validate(input);
        assertEquals(VolumeType.General_Purpose.getColumnName(), input.getVolumeType());
    }

    @Test
    public void testValidateVolumeTypeValidValue(){
        InstanceInput input = buildInstanceInput();
        input.setVolumeType(VolumeType.Magnetic.getColumnName());
        ValidateCommonInputs.validate(input);
        assertEquals(VolumeType.Magnetic.getColumnName(), input.getVolumeType());
    }

    @Test
    public void testValidateIOPsNullValue(){
        InstanceInput input = buildInstanceInput();
        ValidateCommonInputs.validate(input);
        assertEquals(0, input.getIops());
    }

    @Test
    public void testValidateIOPsValidValue(){
        InstanceInput input = buildInstanceInput();
        input.setIops(100);
        ValidateCommonInputs.validate(input);
        assertEquals(100, input.getIops());
    }

    @Test
    public void testValidateSnapshotValidValue(){
        InstanceInput input = buildInstanceInput();
        input.setSnapshot(20);
        ValidateCommonInputs.validate(input);
        assertEquals(20, input.getSnapshot());
    }

    @Test
    public void testValidateS3BackupValidValue(){
        InstanceInput input = buildInstanceInput();
        input.setS3Backup(200);
        ValidateCommonInputs.validate(input);
        assertEquals(200, input.getS3Backup());
    }

    @Test
    public void testValidateTermTypeNullValue(){
        InstanceInput input = buildInstanceInput();
        ValidateCommonInputs.validate(input);
        assertEquals(TermType.OnDemand.name(), input.getTermType());
    }

    @Test
    public void testValidateTermTypeValidValue(){
        InstanceInput input = buildInstanceInput();

        input.setTermType(TermType.Reserved.name());
        input.setPurchaseOption(PurchaseOption.ALL_UPFRONT.getColumnName());
        input.setLeaseContractLength(LeaseContractLength.ONE_YEAR.getColumnName());
        ValidateCommonInputs.validate(input);

        assertEquals(TermType.Reserved.name(), input.getTermType());
    }

    @Test(expected = PricingCalculatorException.class)
    public void testValidateLeaseContractLengthNullWhenReservedValue(){
        InstanceInput input = buildInstanceInput();
        try {
            input.setTermType(TermType.Reserved.name());
            ValidateCommonInputs.validate(input);
        }catch (PricingCalculatorException pce){
            assertEquals(ValidateCommonInputs.WHEN_RESERVED_LEASE_CONTRACT_LENGTH_IS_MANDATORY, pce.getMessage());
            throw pce;
        }
    }

    @Test
    public void testValidateLeaseContractLengthValidValueWhenReserved(){
        InstanceInput input = buildInstanceInput();
        input.setTermType(TermType.Reserved.name());
        input.setLeaseContractLength(LeaseContractLength.ONE_YEAR.getColumnName());
        input.setPurchaseOption(PurchaseOption.ALL_UPFRONT.getColumnName());
        ValidateCommonInputs.validate(input);
        assertEquals(LeaseContractLength.ONE_YEAR.getColumnName(), input.getLeaseContractLength());
    }

    @Test(expected = PricingCalculatorException.class)
    public void testValidatePurchaseOptionNullWhenReservedValue(){
        InstanceInput input = buildInstanceInput();
        try {
            input.setTermType(TermType.Reserved.name());
            input.setLeaseContractLength(LeaseContractLength.ONE_YEAR.getColumnName());
            ValidateCommonInputs.validate(input);
        }catch (PricingCalculatorException pce){
            assertEquals(ValidateCommonInputs.WHEN_RESERVED_PURCHASE_OPTION_IS_MANDATORY, pce.getMessage());
            throw pce;
        }
    }

    @Test
    public void testValidatePurchaseOptionValidValueWhenReserved(){
        InstanceInput input = buildInstanceInput();
        input.setTermType(TermType.Reserved.name());
        input.setLeaseContractLength(LeaseContractLength.ONE_YEAR.getColumnName());
        input.setPurchaseOption(PurchaseOption.ALL_UPFRONT.getColumnName());
        ValidateCommonInputs.validate(input);
        assertEquals(PurchaseOption.ALL_UPFRONT.getColumnName(), input.getPurchaseOption());
    }


    @Test
    public void testValidateOfferingClassNullValueReserved(){
        InstanceInput input = buildInstanceInput();
        input.setTermType(TermType.Reserved.name());
        input.setLeaseContractLength(LeaseContractLength.ONE_YEAR.getColumnName());
        input.setPurchaseOption(PurchaseOption.ALL_UPFRONT.getColumnName());
        ValidateCommonInputs.validate(input);
        assertEquals(OfferingClass.Standard.name(), input.getOfferingClass());
    }

    @Test
    public void testValidateOfferingClassValidValue(){
        InstanceInput input = buildInstanceInput();
        input.setTermType(TermType.Reserved.name());
        input.setOfferingClass(OfferingClass.Convertible.name());
        input.setLeaseContractLength(LeaseContractLength.ONE_YEAR.getColumnName());
        input.setPurchaseOption(PurchaseOption.ALL_UPFRONT.getColumnName());
        ValidateCommonInputs.validate(input);
        assertEquals(OfferingClass.Convertible.name(), input.getOfferingClass());
    }


    @Test
    public void testNullTenancyAndNullPreInstalledSwValues(){
        InstanceInput input = buildInstanceInput();
        ValidateCommonInputs.validate(input);
        assertEquals(Tenancy.Shared.name(), input.getTenancy());
        assertEquals(PreInstalledSoftware.NA.name(), input.getPreInstalledSw());
    }

    @Test
    public void testNotNullTenancyAndPreInstalledSwValues(){
        InstanceInput input = buildInstanceInput();

        input.setTenancy(Tenancy.Dedicated.name());
        input.setPreInstalledSw(PreInstalledSoftware.SQLEnterprise.name());

        ValidateCommonInputs.validate(input);

        assertEquals(Tenancy.Dedicated.name(), input.getTenancy());
        assertEquals(PreInstalledSoftware.SQLEnterprise.name(), input.getPreInstalledSw());
    }


    private InstanceInput buildInstanceInput() {
        InstanceInput input = new InstanceInput();
        input.setDescription("Server1");
        input.setRegion(Region.SOUTH_AMERICA_SAO_PAULO.getColumnName());
        input.setOperatingSystem(OperatingSystem.Windows.name());
        return input;
    }
}
