package com.amazon.proposalcalculator.enums;

public enum Environment {

	PROD, DEV, QA, SANDBOX, TEST, NON_PROD, HA, DR_FULL, DR_OPTIMIZED, DR_INACTIVE;

	public static boolean isEquivalentToProd(String input_environment) {
		return isEquivalentToProd(Environment.valueOf(input_environment.toUpperCase()));
	}

	public static boolean isEquivalentToProd(Environment environment) {

		if (environment.equals(PROD) || environment.equals(DR_FULL) || environment.equals(DR_INACTIVE)
				|| environment.equals(DR_OPTIMIZED) || environment.equals(HA)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public static boolean isEquivalentToNonProd(Environment environment) {
		return !isEquivalentToProd(environment);
	}

	public static boolean isEquivalentToNonProd(String input_environment) {
		return !isEquivalentToProd(Environment.valueOf(input_environment.toUpperCase()));
	}

}
