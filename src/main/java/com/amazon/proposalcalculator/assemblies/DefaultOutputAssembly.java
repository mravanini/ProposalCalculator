package com.amazon.proposalcalculator.assemblies;

import com.amazon.proposalcalculator.bean.DefaultInput;
import com.amazon.proposalcalculator.bean.DefaultOutput;

/**
 * Created by ravanini on 20/01/17.
 */
public class DefaultOutputAssembly {


    public static DefaultOutput from(DefaultInput server){

    DefaultOutput output = new DefaultOutput();
        output.setDescription(server.getDescription());
        output.setInstances(server.getInstances());
        output.setRegion(server.getRegion());
        output.setCpu(server.getCpu());
        output.setMemory(server.getMemory());
        output.setStorage(server.getStorage());
        output.setSnapshot(server.getSnapshot());
        output.setUsage(server.getUsage());
        output.setTermType(server.getTermType());
        output.setOfferingClass(server.getOfferingClass());
        output.setLeaseContractLength(server.getLeaseContractLength());
        output.setPurchaseOption(server.getPurchaseOption());

        output.setTenancy(server.getTenancy());
        output.setOperatingSystem(server.getOperatingSystem());
        output.setBeginning(server.getBeginning());
        output.setEnd(server.getEnd());
        return output;
    }

}
