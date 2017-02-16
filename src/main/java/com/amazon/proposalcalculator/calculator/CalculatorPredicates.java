package com.amazon.proposalcalculator.calculator;

import com.amazon.proposalcalculator.bean.DataTransferInput;
import com.amazon.proposalcalculator.bean.InstanceInput;
import com.amazon.proposalcalculator.bean.Price;
import com.amazon.proposalcalculator.enums.VolumeType;
import com.amazon.proposalcalculator.utils.Constants;

import java.util.function.Predicate;

/**
 * Created by ravanini on 20/01/17.
 */
public class CalculatorPredicates {
	
	public static Predicate<Price> sapCertifiedInstances(InstanceInput server) {
		return  p -> (server.getUseSAPCertifiedInstances() == null || "No".equals(server.getUseSAPCertifiedInstances()) ||
				(("Yes".equals(server.getUseSAPCertifiedInstances()) &&
				(p.getInstanceType().toLowerCase().startsWith("m4")
				|| p.getInstanceType().toLowerCase().startsWith("c4")
				|| p.getInstanceType().toLowerCase().startsWith("c3")
				|| p.getInstanceType().toLowerCase().startsWith("r4")
				|| p.getInstanceType().toLowerCase().startsWith("r3")
				|| p.getInstanceType().toLowerCase().startsWith("x1")))));
	}
	
    public static Predicate<Price> newGeneration(InstanceInput server){
        return p -> (server.getOnlyCurrentGenerationInstances() == null || "No".equals(server.getOnlyCurrentGenerationInstances()) || server.getOnlyCurrentGenerationInstances() == null) ||
        		("Yes".equals(server.getOnlyCurrentGenerationInstances()) && (p.getCurrentGeneration() == null || "Yes".equals(p.getCurrentGeneration())));
        
     }
	
    public static Predicate<Price> ec2(InstanceInput server){
       return p -> !p.getPriceDescription().contains("Dedicated Host") && p.getProductFamily().equals("Compute Instance");
    }

    public static Predicate<Price> region(InstanceInput server){
        return p -> p.getLocation() != null && p.getLocation().toLowerCase().startsWith(server.getRegion().toLowerCase());
    }
    
    public static Predicate<Price> region(DataTransferInput dataTransfer){
        return p -> p.getFromLocation() != null && p.getFromLocation().toLowerCase().startsWith(dataTransfer.getRegion().toLowerCase());
    }

    public static Predicate<Price> cpu(InstanceInput server){
        return p -> server.getCpu() == null || p.getvCPU() >= server.getCpu() * ((100 - Constants.config.getCpuTolerance()) / 100);
    }
    
    public static Predicate<Price> saps(InstanceInput server){
        return p -> server.getSaps() == null || p.getSaps() >= server.getSaps() * ((100 - Constants.config.getCpuTolerance()) / 100);
    }

    public static Predicate<Price> memory(InstanceInput server){
        return p -> server.getMemory() == null || p.getMemory() >= server.getMemory() * ((100 - Constants.config.getMemoryTolerance()) / 100);
    }

    public static Predicate<Price> preInstalledSw(InstanceInput server){
        return p -> (((p.getPreInstalledSw().equals("NA") || p.getPreInstalledSw() == null) && server.getPreInstalledSw() == null)) ||  (p.getPreInstalledSw() != null && p.getPreInstalledSw().equalsIgnoreCase(server.getPreInstalledSw()));
    }

    /*public static Predicate<Price> termType(InstanceInput server){
        return p -> server.getTermType() == null || (p.getTermType() != null && p.getTermType().equals(server.getTermType()));
    }
    
    public static Predicate<Price> leaseContractLength(InstanceInput server){
        return p -> (p.getLeaseContractLength() == null && server.getLeaseContractLength() == null) ||  (p.getLeaseContractLength() != null && p.getLeaseContractLength().equalsIgnoreCase(server.getLeaseContractLength()));
    }

    public static Predicate<Price> purchaseOption(InstanceInput server){
        return p -> (p.getPurchaseOption() == null && server.getPurchaseOption() == null) ||  (p.getPurchaseOption() != null && p.getPurchaseOption().equalsIgnoreCase(server.getPurchaseOption()));
    }

    public static Predicate<Price> offeringClass(InstanceInput server) {
        return p -> (p.getOfferingClass() == null && server.getOfferingClass() == null) ||  (p.getOfferingClass() != null && p.getOfferingClass().equalsIgnoreCase(server.getOfferingClass()));
    }*/
    
    public static Predicate<Price> termType(InstanceInput server){
        return p -> (p.getTermType() != null && p.getTermType().equals(server.getTermType()));
    }
    
    public static Predicate<Price> leaseContractLength(InstanceInput server){
        return p -> (server.getTermType().equals("Reserved") && server.getLeaseContractLength() == null && p.getLeaseContractLength() != null) || (p.getLeaseContractLength() == null && server.getLeaseContractLength() == null) ||  (p.getLeaseContractLength() != null && p.getLeaseContractLength().equalsIgnoreCase(server.getLeaseContractLength()));
    }

    public static Predicate<Price> purchaseOption(InstanceInput server){
        return p -> (server.getTermType().equals("Reserved") && server.getPurchaseOption() == null && p.getPurchaseOption() != null) || (p.getPurchaseOption() == null && server.getPurchaseOption() == null) ||  (p.getPurchaseOption() != null && p.getPurchaseOption().equalsIgnoreCase(server.getPurchaseOption()));
    }

    public static Predicate<Price> offeringClass(InstanceInput server) {
        return p -> (server.getTermType().equals("Reserved") && server.getOfferingClass() == null && p.getOfferingClass() != null) || (p.getOfferingClass() == null && server.getOfferingClass() == null) ||  (p.getOfferingClass() != null && p.getOfferingClass().equalsIgnoreCase(server.getOfferingClass()));
    }
    
    public static Predicate<Price> operatingSystem(InstanceInput server){
        return p -> server.getOperatingSystem() == null || (p.getOperatingSystem() != null && p.getOperatingSystem().equalsIgnoreCase(server.getOperatingSystem()));
    }
    
    public static Predicate<Price> licenceModel(InstanceInput server){
        return p -> p.getLicenseModel() != null && p.getLicenseModel().equalsIgnoreCase("Windows".equals(server.getOperatingSystem()) ? "License Included" : "No License required");
    }

    public static Predicate<Price> tenancy(InstanceInput server){
        return p -> server.getTenancy() == null || (p.getTenancy() != null && p.getTenancy().equalsIgnoreCase(server.getTenancy()));
    }

    public static Predicate<Price> volumeType(InstanceInput input){
        return p -> p.getVolumeType() != null && p.getVolumeType().equalsIgnoreCase(input.getVolumeType());
    }

    public static Predicate<Price> group(String group){
        return p -> p.getGroup() != null && p.getGroup().equalsIgnoreCase(group);
    }

    public static Predicate<Price> snapshot() {
        return p -> p.getProductFamily() != null && p.getProductFamily().equalsIgnoreCase("Storage Snapshot");
    }
    
    public static Predicate<Price> dataTransferOut(DataTransferInput dataTransfer){
    	return p -> p.getProductFamily() != null && p.getProductFamily().equalsIgnoreCase("Data Transfer") &&
	    p.getTransferType()  != null && p.getTransferType().equalsIgnoreCase("AWS Outbound") &&
	    p.getStartingRangeAsLong() < dataTransfer.getDataTransferOut();
    }

}
