package com.amazon.proposalcalculator.assemblies;

import com.amazon.proposalcalculator.bean.DefaultInput;
import com.amazon.proposalcalculator.bean.DefaultOutput;

/**
 * Created by ravanini on 20/01/17.
 */
public class DefaultOutputAssembly {


    public static DefaultOutput from(DefaultInput input){

    DefaultOutput output = new DefaultOutput();
        output.setDescription(input.getDescription());
        output.setInstances(input.getInstances());
        output.setRegion(input.getRegion());
        output.setCpu(input.getCpu());
        output.setMemory(input.getMemory());
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
        
        return output;
    }

}
