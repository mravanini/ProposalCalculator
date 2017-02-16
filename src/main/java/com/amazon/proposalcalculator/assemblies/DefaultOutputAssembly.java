package com.amazon.proposalcalculator.assemblies;

import com.amazon.proposalcalculator.bean.InstanceInput;
import com.amazon.proposalcalculator.bean.InstanceOutput;
import com.amazon.proposalcalculator.bean.Price;
import com.amazon.proposalcalculator.utils.Constants;

/**
 * Created by ravanini on 20/01/17.
 */
public class DefaultOutputAssembly {

	public static InstanceOutput from(InstanceInput input, Price price) {

		InstanceOutput output = new InstanceOutput();

		output.setDescription(input.getDescription());
		output.setInstances(input.getInstances());

		output.setRegion(price.getFromLocation());

		output.setCpu(input.getCpu());
		output.setMemory(input.getMemory());
		output.setStorage(input.getStorage());
		output.setVolumeType(input.getVolumeType());
		output.setIops(input.getIops());
		output.setSnapshot(input.getSnapshot());
		output.setMonthlyUtilization(input.getMonthlyUtilization());

		output.setTermType(price.getTermType());
		output.setOfferingClass(price.getOfferingClass());
		output.setLeaseContractLength(price.getLeaseContractLength());
		output.setPurchaseOption(price.getPurchaseOption());

		output.setTenancy(price.getTenancy());
		output.setOperatingSystem(price.getOperatingSystem());

		output.setBeginning(input.getBeginning());
		output.setEnd(input.getEnd());
		output.setPreInstalledSw(price.getPreInstalledSw());

		output.setUseSAPCertifiedInstances(input.getUseSAPCertifiedInstances());
		output.setSaps(input.getSaps());
		output.setOnlyCurrentGenerationInstances(input.getOnlyCurrentGenerationInstances());

		output.setInstanceType(price.getInstanceType());
		output.setInstanceMemory(price.getMemory());
		output.setInstanceSAPS(price.getSaps());
		output.setInstanceVCPU(price.getvCPU());
		output.setComputeUnitPrice(price.getInstanceHourPrice());
		output.setComputeMonthlyPrice(price.getInstanceHourPrice() * Constants.HOURS_IN_A_MONTH * input.getInstances()
				* (price.getTermType().equals("OnDemand") ? input.getMonthlyUtilization() / 100 : 1));

		return output;
	}

}
