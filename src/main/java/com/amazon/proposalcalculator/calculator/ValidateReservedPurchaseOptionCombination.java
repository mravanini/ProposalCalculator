package com.amazon.proposalcalculator.calculator;

import com.amazon.proposalcalculator.bean.InstanceInput;
import com.amazon.proposalcalculator.enums.LeaseContractLength;
import com.amazon.proposalcalculator.enums.OfferingClass;
import com.amazon.proposalcalculator.enums.PurchaseOption;
import com.amazon.proposalcalculator.enums.TermType;
import com.amazon.proposalcalculator.exception.PricingCalculatorException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by ravanini on 25/02/17.
 */
public class ValidateReservedPurchaseOptionCombination {

    static final String INVALID_COMBINATION = "This TermType/LeaseContractLength/PurchaseOption/OfferingClass is not valid. Please, see Help tab for instructions.";

    public static void validate(InstanceInput input){



        List<PossiblePurchaseCombination> combinations = new ArrayList<>();

        combinations.add(new PossiblePurchaseCombination(TermType.OnDemand.name(), null, null, null));
        combinations.add(new PossiblePurchaseCombination(TermType.Reserved.name(), LeaseContractLength.ONE_YEAR.getColumnName(), PurchaseOption.NO_UPFRONT.getColumnName(), OfferingClass.Standard.name()));
        combinations.add(new PossiblePurchaseCombination(TermType.Reserved.name(), LeaseContractLength.ONE_YEAR.getColumnName(), PurchaseOption.PARTIAL_UPFRONT.getColumnName(), OfferingClass.Standard.name()));
        combinations.add(new PossiblePurchaseCombination(TermType.Reserved.name(), LeaseContractLength.ONE_YEAR.getColumnName(), PurchaseOption.ALL_UPFRONT.getColumnName(), OfferingClass.Standard.name()));
        combinations.add(new PossiblePurchaseCombination(TermType.Reserved.name(), LeaseContractLength.THREE_YEARS.getColumnName(), PurchaseOption.PARTIAL_UPFRONT.getColumnName(), OfferingClass.Standard.name()));
        combinations.add(new PossiblePurchaseCombination(TermType.Reserved.name(), LeaseContractLength.THREE_YEARS.getColumnName(), PurchaseOption.ALL_UPFRONT.getColumnName(), OfferingClass.Standard.name()));
        combinations.add(new PossiblePurchaseCombination(TermType.Reserved.name(), LeaseContractLength.THREE_YEARS.getColumnName(), PurchaseOption.NO_UPFRONT.getColumnName(), OfferingClass.Convertible.name()));
        combinations.add(new PossiblePurchaseCombination(TermType.Reserved.name(), LeaseContractLength.THREE_YEARS.getColumnName(), PurchaseOption.PARTIAL_UPFRONT.getColumnName(), OfferingClass.Convertible.name()));
        combinations.add(new PossiblePurchaseCombination(TermType.Reserved.name(), LeaseContractLength.THREE_YEARS.getColumnName(), PurchaseOption.ALL_UPFRONT.getColumnName(), OfferingClass.Convertible.name() ));


        List<PossiblePurchaseCombination> validCombinations = combinations.stream().filter(getValidCombination(
                new PossiblePurchaseCombination(input.getTermType(),
                                                input.getLeaseContractLength(),
                                                input.getPurchaseOption(),
                                                input.getOfferingClass()))).collect(Collectors.toList());

        if (validCombinations.isEmpty()){
            throw new PricingCalculatorException(INVALID_COMBINATION);
        }
    }

    private static Predicate<PossiblePurchaseCombination> getValidCombination(PossiblePurchaseCombination combination){
            return  p -> p.termType.equals(TermType.getTermType(combination.termType).name())
                    &&   (
                            (p.leaseContractLength == null && combination.leaseContractLength == null) ||
                            (p.leaseContractLength != null && p.leaseContractLength.equals(
                                    LeaseContractLength.getLeaseContractLength(combination.leaseContractLength).getColumnName())))
                    &&   (
                            (p.offeringClass == null && combination.offeringClass == null) ||
                            (p.offeringClass != null && p.offeringClass.equals(
                                    OfferingClass.getOfferingClass(combination.offeringClass).name())))
                    &&   (
                            (p.purchaseOption == null && combination.offeringClass == null ) ||
                            (p.purchaseOption != null && p.purchaseOption.equals(
                                    PurchaseOption.getPurchaseOption(combination.purchaseOption).getColumnName())))
                    ;
    }


    private static class PossiblePurchaseCombination {

        private String termType;

        private String leaseContractLength;

        private String purchaseOption;

        private String offeringClass;

        public PossiblePurchaseCombination(String termType, String leaseContractLength, String purchaseOption, String offeringClass) {
            this.termType = termType;
            this.leaseContractLength = leaseContractLength;
            this.purchaseOption = purchaseOption;
            this.offeringClass = offeringClass;
        }
    }
}
