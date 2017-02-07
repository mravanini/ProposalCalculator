package com.amazon.proposalcalculator.calculator;

import com.amazon.proposalcalculator.bean.DefaultInput;
import com.amazon.proposalcalculator.bean.Price;
import com.amazon.proposalcalculator.enums.VolumeType;
import com.amazon.proposalcalculator.utils.Constants;

import java.util.function.Predicate;

/**
 * Created by ravanini on 20/01/17.
 */
public class CalculatorPredicates {

    public static Predicate<Price> region(DefaultInput server){
        return p -> p.getLocation() != null && p.getLocation().toLowerCase().startsWith(server.getRegion().toLowerCase());
    }

    public static Predicate<Price> cpuTolerance(DefaultInput server){
        return p -> p.getvCPU() >= server.getCpu() * ((100 - Constants.config.getCpuTolerance()) / 100);
    }

    public static Predicate<Price> memory(DefaultInput server){
        return p -> p.getMemory() >= server.getMemory() * ((100 - Constants.config.getMemoryTolerance()) / 100);
    }

    public static Predicate<Price> termType(DefaultInput server){
        return p -> p.getTermType() != null && p.getTermType().equals(server.getTermType());
    }
    
    public static Predicate<Price> preInstalledSw(DefaultInput server){
        return p -> (((p.getPreInstalledSw().equals("NA") || p.getPreInstalledSw() == null) && server.getPreInstalledSw() == null)) ||  (p.getPreInstalledSw() != null && p.getPreInstalledSw().equalsIgnoreCase(server.getPreInstalledSw()));
    }

    public static Predicate<Price> offeringClass(DefaultInput server) {
        return p -> (p.getOfferingClass() == null && server.getOfferingClass() == null) ||  (p.getOfferingClass() != null && p.getOfferingClass().equalsIgnoreCase(server.getOfferingClass()));
    }

    public static Predicate<Price> leaseContractLength(DefaultInput server){
        return p -> (p.getLeaseContractLength() == null && server.getLeaseContractLength() == null) ||  (p.getLeaseContractLength() != null && p.getLeaseContractLength().equalsIgnoreCase(server.getLeaseContractLength()));
    }

    public static Predicate<Price> purchaseOption(DefaultInput server){
        return p -> (p.getPurchaseOption() == null && server.getPurchaseOption() == null) ||  (p.getPurchaseOption() != null && p.getPurchaseOption().equalsIgnoreCase(server.getPurchaseOption()));
    }

    public static Predicate<Price> operatingSystem(DefaultInput server){
        return p -> p.getOperatingSystem() != null && p.getOperatingSystem().equalsIgnoreCase(server.getOperatingSystem());
    }
    
    public static Predicate<Price> licenceModel(DefaultInput server){
        return p -> p.getLicenseModel() != null && p.getLicenseModel().equalsIgnoreCase("Windows".equals(server.getOperatingSystem()) ? "License Included" : "No License required");
    }

    public static Predicate<Price> tenancy(DefaultInput server){
        return p -> p.getTenancy() != null && p.getTenancy().equalsIgnoreCase(server.getTenancy());
    }

    public static Predicate<Price> volumeType(DefaultInput input){
        return p -> p.getVolumeType() != null && p.getVolumeType().equalsIgnoreCase(input.getVolumeType());
    }

    public static Predicate<Price> group(String group){
        return p -> p.getGroup() != null && p.getGroup().equalsIgnoreCase(group);
    }

    public static Predicate<Price> snapshot(){
        return p -> p.getProductFamily() != null && p.getProductFamily().equalsIgnoreCase("Storage Snapshot");
    }


}
