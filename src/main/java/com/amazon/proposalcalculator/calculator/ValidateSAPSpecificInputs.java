package com.amazon.proposalcalculator.calculator;

import com.amazon.proposalcalculator.bean.InstanceInput;
import com.amazon.proposalcalculator.enums.OperatingSystem;
import com.amazon.proposalcalculator.exception.PricingCalculatorException;

/**
 * Created by ravanini on 18/02/17.
 */
public class ValidateSAPSpecificInputs {

	static final String SAP_INSTANCE_TYPE_IS_A_MANDATORY_FIELD = "SAP Instance Type is a mandatory field for SAP calculations.";
	static final String ENVIRONMENT_IS_A_MANDATORY_FIELD = "Environment is a mandatory field";

	static void validate(InstanceInput input) {

		validateSAPInstanceType(input.getSapInstanceType());

		input.setSaps(fillSaps(input.getSaps()));

		validateMandatoryFields(input);

		input.setOperatingSystem(fillOperatingSystem(input.getOperatingSystem()));

		fillSAPsCPURamOnDREnvironment(input);

	}

	private static void fillSAPsCPURamOnDREnvironment(InstanceInput input) {

	}

	private static String fillOperatingSystem(String operatingSystem) {
		if (OperatingSystem.BYOL.name().equalsIgnoreCase(operatingSystem)) {
			return OperatingSystem.Linux.name();
		}
		return operatingSystem;
	}

	private static void validateSAPInstanceType(String sapInstanceType) {

		if (sapInstanceType == null) {
			throw new PricingCalculatorException(SAP_INSTANCE_TYPE_IS_A_MANDATORY_FIELD);
		}
	}

	private static Integer fillSaps(Integer saps) {
		return saps == null ? Integer.valueOf("0") : saps;
	}

	private static void validateMandatoryFields(InstanceInput input) {
		if (input.getEnvironment() == null) {
			throw new PricingCalculatorException(ENVIRONMENT_IS_A_MANDATORY_FIELD);
		}
	}

	private static boolean validInteger(Integer value) {
		return value != null && value.intValue() != 0;
	}

	private static boolean validDouble(Double value) {
		return value != null && value.doubleValue() != 0;
	}

}
