package com.amazon.proposalcalculator.calculator;

import com.amazon.proposalcalculator.bean.InstanceInput;
import com.amazon.proposalcalculator.enums.BillingOption;
import com.amazon.proposalcalculator.enums.LeaseContractLength;
import com.amazon.proposalcalculator.enums.OfferingClass;
import com.amazon.proposalcalculator.enums.OperatingSystem;
import com.amazon.proposalcalculator.enums.PreInstalledSoftware;
import com.amazon.proposalcalculator.enums.PurchaseOption;
import com.amazon.proposalcalculator.enums.Region;
import com.amazon.proposalcalculator.enums.TermType;
import com.amazon.proposalcalculator.enums.VolumeType;
import com.amazon.proposalcalculator.exception.PricingCalculatorException;

/**
 * Created by ravanini on 18/02/17.
 */
public class ValidateCommonInputs {

	static final String DESCRIPTION_IS_A_MANDATORY_FIELD = "Description is a mandatory field.";
	static final String REGION_IS_A_MANDATORY_FIELD = "Region is a mandatory field.";
	static final String WHEN_RESERVED_PURCHASE_OPTION_IS_MANDATORY = "When TermType = Reserved, Purchase Option is a mandatory field.";
	static final String OPERATING_SYSTEM_IS_MANDATORY = "Operating System is a mandatory field.";
	static final String WHEN_RESERVED_LEASE_CONTRACT_LENGTH_IS_MANDATORY = "When TermType = Reserved, Lease Contract Length is a mandatory field.";

	static void validate(InstanceInput input) {

		validateDescription(input.getDescription());

		input.setInstances(fillQuantityOfInstances(input.getInstances()));

		fillBillingOption(input);

		input.setRegion(fillRegion(input.getRegion()));

		input.setOperatingSystem(fillOperatingSystem(input.getOperatingSystem()));

		input.setCpuTolerance(fillCpuTolerance(input.getCpuTolerance()));

		input.setMemoryTolerance(fillMemoryTolerance(input.getMemoryTolerance()));

		input.setMonthlyUtilization(fillMonthlyUtilization(input.getMonthlyUtilization()));

		input.setVolumeType(fillVolumeType(input.getVolumeType()));

		input.setIops(fillIops(input.getIops()));

		input.setTermType(fillTermType(input.getTermType()));

		input.setLeaseContractLength(fillLeaseContractLength(input.getLeaseContractLength(), input.getTermType()));

		input.setPurchaseOption(fillPurchaseOption(input.getPurchaseOption(), input.getTermType()));

		input.setOfferingClass(fillOfferingClass(input.getOfferingClass(), input.getTermType()));

		input.setOnlyCurrentGenerationInstances(
				fillOnlyCurrentGenerationInstances(input.getOnlyCurrentGenerationInstances()));

		input.setPreInstalledSw(fillPreInstalledSoftware(input.getPreInstalledSw()));

		input.setUseBurstablePerformance(fillUseBurstablePerformance(input.getUseBurstablePerformance()));
	}

	private static String fillOnlyCurrentGenerationInstances(String onlyCurrentGenerationInstances) {
		return onlyCurrentGenerationInstances == null ? "No" : onlyCurrentGenerationInstances;
	}

	private static String fillUseBurstablePerformance(String useBurstablePerformance) {
		return useBurstablePerformance == null ? "Yes" : useBurstablePerformance;
	}

	private static int fillQuantityOfInstances(int instances) {
		return instances == 0 ? 1 : instances;
	}

	private static String fillPreInstalledSoftware(String preInstalledSw) {
		return (preInstalledSw == null) ? PreInstalledSoftware.NA.getColumnName()
				: PreInstalledSoftware.getPreInstalledSoftware(preInstalledSw).getColumnName();
	}

	private static String fillOperatingSystem(String operatingSystem) {

		if (operatingSystem == null) {
			throw new PricingCalculatorException(OPERATING_SYSTEM_IS_MANDATORY);
		}
		return OperatingSystem.getOperatingSystem(operatingSystem).name();
	}

	private static String fillOfferingClass(String offeringClass, String termType) {
		if (TermType.valueOf(termType).equals(TermType.Reserved) && offeringClass == null) {
			return OfferingClass.Standard.name();
		}
		return offeringClass;
	}

	private static String fillLeaseContractLength(String leaseContractLength, String termType) {
		if (TermType.valueOf(termType).equals(TermType.Reserved)) {
			if (leaseContractLength == null) {
				throw new PricingCalculatorException(WHEN_RESERVED_LEASE_CONTRACT_LENGTH_IS_MANDATORY);
			}
			return LeaseContractLength.getLeaseContractLength(leaseContractLength).getColumnName();
		}
		return null;
	}

	private static String fillPurchaseOption(String purchaseOption, String termType) {
		if (TermType.valueOf(termType).equals(TermType.Reserved)) {
			if (purchaseOption == null) {
				throw new PricingCalculatorException(WHEN_RESERVED_PURCHASE_OPTION_IS_MANDATORY);
			}
			return PurchaseOption.getPurchaseOption(purchaseOption).getColumnName();
		}
		return null;
	}

	private static String fillTermType(String termType) {
		return (termType == null) ? TermType.OnDemand.name() : TermType.getTermType(termType).name();
	}

	private static void fillBillingOption(InstanceInput input) {
		if (BillingOption.ON_DEMAND.getName().equals(input.getBillingOption())) {
			input.setTermType(TermType.OnDemand.name());
		} else if (BillingOption.ONE_YRNU.getName().equals(input.getBillingOption())) {
			input.setTermType(TermType.Reserved.name());
			input.setLeaseContractLength(LeaseContractLength.ONE_YEAR.getColumnName());
			input.setPurchaseOption(PurchaseOption.NO_UPFRONT.getColumnName());
			input.setOfferingClass(OfferingClass.Standard.name());
		} else if (BillingOption.ONE_YRPU.getName().equals(input.getBillingOption())) {
			input.setTermType(TermType.Reserved.name());
			input.setLeaseContractLength(LeaseContractLength.ONE_YEAR.getColumnName());
			input.setPurchaseOption(PurchaseOption.PARTIAL_UPFRONT.getColumnName());
			input.setOfferingClass(OfferingClass.Standard.name());
		} else if (BillingOption.ONE_YRAU.getName().equals(input.getBillingOption())) {
			input.setTermType(TermType.Reserved.name());
			input.setLeaseContractLength(LeaseContractLength.ONE_YEAR.getColumnName());
			input.setPurchaseOption(PurchaseOption.ALL_UPFRONT.getColumnName());
			input.setOfferingClass(OfferingClass.Standard.name());
		} else if (BillingOption.THREE_YRNU.getName().equals(input.getBillingOption())) {
			input.setTermType(TermType.Reserved.name());
			input.setLeaseContractLength(LeaseContractLength.THREE_YEARS.getColumnName());
			input.setPurchaseOption(PurchaseOption.NO_UPFRONT.getColumnName());
			input.setOfferingClass(OfferingClass.Standard.name());
		} else if (BillingOption.THREE_YRPU.getName().equals(input.getBillingOption())) {
			input.setTermType(TermType.Reserved.name());
			input.setLeaseContractLength(LeaseContractLength.THREE_YEARS.getColumnName());
			input.setPurchaseOption(PurchaseOption.PARTIAL_UPFRONT.getColumnName());
			input.setOfferingClass(OfferingClass.Standard.name());
		} else if (BillingOption.THREE_YRAU.getName().equals(input.getBillingOption())) {
			input.setTermType(TermType.Reserved.name());
			input.setLeaseContractLength(LeaseContractLength.THREE_YEARS.getColumnName());
			input.setPurchaseOption(PurchaseOption.ALL_UPFRONT.getColumnName());
			input.setOfferingClass(OfferingClass.Standard.name());
		} else if (BillingOption.THREE_YRNUC.getName().equals(input.getBillingOption())) {
			input.setTermType(TermType.Reserved.name());
			input.setLeaseContractLength(LeaseContractLength.THREE_YEARS.getColumnName());
			input.setPurchaseOption(PurchaseOption.NO_UPFRONT.getColumnName());
			input.setOfferingClass(OfferingClass.Convertible.name());
		} else if (BillingOption.THREE_YRPUC.getName().equals(input.getBillingOption())) {
			input.setTermType(TermType.Reserved.name());
			input.setLeaseContractLength(LeaseContractLength.THREE_YEARS.getColumnName());
			input.setPurchaseOption(PurchaseOption.PARTIAL_UPFRONT.getColumnName());
			input.setOfferingClass(OfferingClass.Convertible.name());
		} else if (BillingOption.THREE_YRAUC.getName().equals(input.getBillingOption())) {
			input.setTermType(TermType.Reserved.name());
			input.setLeaseContractLength(LeaseContractLength.THREE_YEARS.getColumnName());
			input.setPurchaseOption(PurchaseOption.ALL_UPFRONT.getColumnName());
			input.setOfferingClass(OfferingClass.Convertible.name());
		}
	}

	private static Integer fillIops(Integer iops) {
		return (iops == null) ? Integer.valueOf("0") : iops;
	}

	private static Double fillMemoryTolerance(Double memoryTolerance) {
		return (memoryTolerance == null) ? Double.valueOf("0") : memoryTolerance;
	}

	private static Double fillCpuTolerance(Double cpuTolerance) {
		return (cpuTolerance == null) ? Double.valueOf("0") : cpuTolerance;
	}

	private static String fillVolumeType(String volumeType) {
		return volumeType != null ? VolumeType.getVolumeType(volumeType).getColumnName()
				: VolumeType.General_Purpose.getColumnName();
	}

	private static Double fillMonthlyUtilization(Double monthlyUtilization) {
		return (monthlyUtilization == null) ? Double.valueOf("1") : monthlyUtilization;
	}

	private static void validateDescription(String description) {
		if (description == null || description.trim().isEmpty()) {
			throw new PricingCalculatorException(DESCRIPTION_IS_A_MANDATORY_FIELD);
		}
	}

	private static String fillRegion(String region) {
		// TODO VALIDATE REGIONS reading FILE FROM S3 - GET IDEA FROM Singh, Harpreet
		// <batrahs@amazon.com>

		if (region == null)
			throw new PricingCalculatorException(REGION_IS_A_MANDATORY_FIELD);
		return Region.getRegion(region).getColumnName();
	}
}
