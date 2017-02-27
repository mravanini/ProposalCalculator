package com.amazon.proposalcalculator.calculator;

import com.amazon.proposalcalculator.bean.InstanceInput;
import com.amazon.proposalcalculator.enums.LeaseContractLength;
import com.amazon.proposalcalculator.enums.OfferingClass;
import com.amazon.proposalcalculator.enums.PurchaseOption;
import com.amazon.proposalcalculator.enums.TermType;
import com.amazon.proposalcalculator.exception.PricingCalculatorException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by ravanini on 25/02/17.
 */
public class ValidateReservedPurchaseOptionCombinationTest {


    @Test
    public void testOnDemandValid(){
        InstanceInput input = new InstanceInput();

        input.setTermType(TermType.OnDemand.name());
        input.setOfferingClass(null);
        input.setPurchaseOption(null);
        input.setLeaseContractLength(null);

        ValidateReservedPurchaseOptionCombination.validate(input);
    }

    @Test(expected = PricingCalculatorException.class)
    public void testOnDemandException(){
        InstanceInput input = new InstanceInput();

        input.setTermType(TermType.OnDemand.name());
        input.setOfferingClass(OfferingClass.Standard.name());
//        input.setPurchaseOption("");
//        input.setLeaseContractLength("");

        try {
            ValidateReservedPurchaseOptionCombination.validate(input);
        }catch (PricingCalculatorException pce){
            assertEquals(ValidateReservedPurchaseOptionCombination.INVALID_COMBINATION, pce.getMessage());
            throw pce;
        }
    }

    @Test
    public void testReservedValid1(){
        InstanceInput input = new InstanceInput();

        input.setTermType(TermType.Reserved.name());
        input.setOfferingClass(OfferingClass.Standard.name());
        input.setPurchaseOption(PurchaseOption.NO_UPFRONT.getColumnName());
        input.setLeaseContractLength(LeaseContractLength.ONE_YEAR.getColumnName());

        ValidateReservedPurchaseOptionCombination.validate(input);
    }

    @Test
    public void testReservedValid2(){
        InstanceInput input = new InstanceInput();

        input.setTermType(TermType.Reserved.name());
        input.setOfferingClass(OfferingClass.Standard.name());
        input.setPurchaseOption(PurchaseOption.PARTIAL_UPFRONT.getColumnName());
        input.setLeaseContractLength(LeaseContractLength.ONE_YEAR.getColumnName());

        ValidateReservedPurchaseOptionCombination.validate(input);
    }

    @Test
    public void testReservedValid3(){
        InstanceInput input = new InstanceInput();

        input.setTermType(TermType.Reserved.name());
        input.setOfferingClass(OfferingClass.Standard.name());
        input.setPurchaseOption(PurchaseOption.ALL_UPFRONT.getColumnName());
        input.setLeaseContractLength(LeaseContractLength.ONE_YEAR.getColumnName());

        ValidateReservedPurchaseOptionCombination.validate(input);
    }

    @Test
    public void testReservedValid4(){
        InstanceInput input = new InstanceInput();

        input.setTermType(TermType.Reserved.name());
        input.setOfferingClass(OfferingClass.Standard.name());
        input.setPurchaseOption(PurchaseOption.PARTIAL_UPFRONT.getColumnName());
        input.setLeaseContractLength(LeaseContractLength.THREE_YEARS.getColumnName());

        ValidateReservedPurchaseOptionCombination.validate(input);
    }

    @Test
    public void testReservedValid5(){
        InstanceInput input = new InstanceInput();

        input.setTermType(TermType.Reserved.name());
        input.setOfferingClass(OfferingClass.Standard.name());
        input.setPurchaseOption(PurchaseOption.ALL_UPFRONT.getColumnName());
        input.setLeaseContractLength(LeaseContractLength.THREE_YEARS.getColumnName());

        ValidateReservedPurchaseOptionCombination.validate(input);
    }


    @Test
    public void testReservedValid6(){
        InstanceInput input = new InstanceInput();

        input.setTermType(TermType.Reserved.name());
        input.setOfferingClass(OfferingClass.Convertible.name());
        input.setPurchaseOption(PurchaseOption.NO_UPFRONT.getColumnName());
        input.setLeaseContractLength(LeaseContractLength.THREE_YEARS.getColumnName());

        ValidateReservedPurchaseOptionCombination.validate(input);
    }

    @Test
    public void testReservedValid7(){
        InstanceInput input = new InstanceInput();

        input.setTermType(TermType.Reserved.name());
        input.setOfferingClass(OfferingClass.Convertible.name());
        input.setPurchaseOption(PurchaseOption.PARTIAL_UPFRONT.getColumnName());
        input.setLeaseContractLength(LeaseContractLength.THREE_YEARS.getColumnName());

        ValidateReservedPurchaseOptionCombination.validate(input);
    }

    @Test
    public void testReservedValid8(){
        InstanceInput input = new InstanceInput();

        input.setTermType(TermType.Reserved.name());
        input.setOfferingClass(OfferingClass.Convertible.name());
        input.setPurchaseOption(PurchaseOption.ALL_UPFRONT.getColumnName());
        input.setLeaseContractLength(LeaseContractLength.THREE_YEARS.getColumnName());

        ValidateReservedPurchaseOptionCombination.validate(input);
    }



    @Test(expected = PricingCalculatorException.class)
    public void testReservedException1(){
        InstanceInput input = new InstanceInput();

        input.setTermType(TermType.Reserved.name());
        input.setOfferingClass(OfferingClass.Standard.name());
        input.setPurchaseOption(PurchaseOption.NO_UPFRONT.getColumnName());
        input.setLeaseContractLength(LeaseContractLength.THREE_YEARS.getColumnName());

        try {
            ValidateReservedPurchaseOptionCombination.validate(input);
        }catch (PricingCalculatorException pce){
            assertEquals(ValidateReservedPurchaseOptionCombination.INVALID_COMBINATION, pce.getMessage());
            throw pce;
        }
    }

    @Test(expected = PricingCalculatorException.class)
    public void testReservedException2(){
        InstanceInput input = new InstanceInput();

        input.setTermType(TermType.Reserved.name());
        input.setOfferingClass(OfferingClass.Convertible.name());
        input.setPurchaseOption(PurchaseOption.NO_UPFRONT.getColumnName());
        input.setLeaseContractLength(LeaseContractLength.ONE_YEAR.getColumnName());

        try {
            ValidateReservedPurchaseOptionCombination.validate(input);
        }catch (PricingCalculatorException pce){
            assertEquals(ValidateReservedPurchaseOptionCombination.INVALID_COMBINATION, pce.getMessage());
            throw pce;
        }
    }

    @Test(expected = PricingCalculatorException.class)
    public void testReservedException3(){
        InstanceInput input = new InstanceInput();

        input.setTermType(TermType.Reserved.name());
        input.setOfferingClass(OfferingClass.Convertible.name());
        input.setPurchaseOption(PurchaseOption.PARTIAL_UPFRONT.getColumnName());
        input.setLeaseContractLength(LeaseContractLength.ONE_YEAR.getColumnName());

        try {
            ValidateReservedPurchaseOptionCombination.validate(input);
        }catch (PricingCalculatorException pce){
            assertEquals(ValidateReservedPurchaseOptionCombination.INVALID_COMBINATION, pce.getMessage());
            throw pce;
        }
    }

    @Test(expected = PricingCalculatorException.class)
    public void testReservedException4(){
        InstanceInput input = new InstanceInput();

        input.setTermType(TermType.Reserved.name());
        input.setOfferingClass(OfferingClass.Convertible.name());
        input.setPurchaseOption(PurchaseOption.ALL_UPFRONT.getColumnName());
        input.setLeaseContractLength(LeaseContractLength.ONE_YEAR.getColumnName());

        try {
            ValidateReservedPurchaseOptionCombination.validate(input);
        }catch (PricingCalculatorException pce){
            assertEquals(ValidateReservedPurchaseOptionCombination.INVALID_COMBINATION, pce.getMessage());
            throw pce;
        }
    }
}
