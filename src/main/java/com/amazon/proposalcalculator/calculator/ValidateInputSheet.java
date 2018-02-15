package com.amazon.proposalcalculator.calculator;

import com.amazon.proposalcalculator.bean.InstanceInput;
import com.amazon.proposalcalculator.exception.PricingCalculatorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;

/**
 * Created by ravanini on 18/02/17.
 */
public class ValidateInputSheet {

    private final static Logger LOGGER = LogManager.getLogger();

    public static void validate(Collection<InstanceInput> servers) {

        LOGGER.info("Filling in default values...");

        for (InstanceInput input : servers) {

            try {
                ValidateCommonInputs.validate(input);

                if (isSAPInputSheet(input)){
                    ValidateSAPSpecificInputs.validate(input);
                }

                ValidateReservedPurchaseOptionCombination.validate(input);

            }catch (PricingCalculatorException pce){
                input.setErrorMessageInput(pce.getMessage());
            }
        }
    }

    private static boolean isSAPInputSheet(InstanceInput input){

        return ((input.getSaps() != 0)
            ||  input.getSapInstanceType() != null)
        ;
    }
}
