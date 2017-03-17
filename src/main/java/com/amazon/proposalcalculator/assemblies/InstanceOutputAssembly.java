package com.amazon.proposalcalculator.assemblies;

import com.amazon.proposalcalculator.bean.InstanceInput;
import com.amazon.proposalcalculator.bean.InstanceOutput;
import com.amazon.proposalcalculator.bean.Price;
import com.amazon.proposalcalculator.utils.Constants;

/**
 * Created by ravanini on 20/01/17.
 */
public class InstanceOutputAssembly {

	public static InstanceOutput from(InstanceInput input) {

		InstanceOutput output = new InstanceOutput();

		output.setDescription(input.getDescription());
		output.setInstances(input.getInstances());

		output.setRegion(input.getRegion());

		output.setCpu(input.getCpu());
		output.setMemory(input.getMemory());
		output.setOriginalMemory(input.getOriginalMemory());
		output.setStorage(input.getStorage());
		output.setVolumeType(input.getVolumeType());
		output.setIops(input.getIops());
		output.setSnapshot(input.getSnapshot());
		output.setMonthlyUtilization(input.getMonthlyUtilization());

		output.setTermType(input.getTermType());
		output.setOfferingClass(input.getOfferingClass());
		output.setLeaseContractLength(input.getLeaseContractLength());
		output.setPurchaseOption(input.getPurchaseOption());

		output.setTenancy(input.getTenancy());
		output.setOperatingSystem(input.getOperatingSystem());

		output.setBeginning(input.getBeginning());
		output.setEnd(input.getEnd());
		output.setPreInstalledSw(input.getPreInstalledSw());

		output.setUseSAPCertifiedInstances(input.getUseSAPCertifiedInstances());
		output.setSaps(input.getSaps());
		output.setOnlyCurrentGenerationInstances(input.getOnlyCurrentGenerationInstances());

		output.setEnvironment(input.getEnvironment());
		output.setSapInstanceType(input.getSapInstanceType());
		output.setCpuTolerance(input.getCpuTolerance());
		output.setMemoryTolerance(input.getMemoryTolerance() );
		output.setArchiveLogsLocalBackup(input.getArchiveLogsLocalBackup() );
		output.setS3Backup(input.getS3Backup());

		return output;
	}

}
