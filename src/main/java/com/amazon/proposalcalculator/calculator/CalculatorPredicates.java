package com.amazon.proposalcalculator.calculator;

import com.amazon.proposalcalculator.bean.DataTransferInput;
import com.amazon.proposalcalculator.bean.InstanceInput;
import com.amazon.proposalcalculator.bean.Price;
import com.amazon.proposalcalculator.bean.S3Price;
import com.amazon.proposalcalculator.enums.ProductFamily;
import com.amazon.proposalcalculator.enums.ProductName;
import com.amazon.proposalcalculator.enums.S3StorageClass;
import com.amazon.proposalcalculator.enums.SAPInstanceType;
import com.amazon.proposalcalculator.enums.VolumeType;
import com.amazon.proposalcalculator.utils.Constants;

import java.util.function.Predicate;

/**
 * Created by ravanini on 20/01/17.
 */
public class CalculatorPredicates {
	
	public static Predicate<Price> sapProductionCertifiedInstances(InstanceInput server) {
		return  p -> (p.getInstanceType().toLowerCase().startsWith("m4")
				|| p.getInstanceType().toLowerCase().startsWith("c4")
				|| p.getInstanceType().toLowerCase().startsWith("r4")
				|| p.getInstanceType().toLowerCase().startsWith("x1"));
	}
	
	public static Predicate<Price> hanaProductionCertifiedInstances(InstanceInput server) {
		boolean isCluster = server.getInstances() > 1;
		if (!isCluster && SAPInstanceType.HANA_OLTP.equals(SAPInstanceType.getSAPInstanceType(server.getSapInstanceType()))) {
			return p -> (p.getInstanceType().toLowerCase().startsWith("r3.8xlarge")
					|| p.getInstanceType().toLowerCase().startsWith("r4.8xlarge")
					|| p.getInstanceType().toLowerCase().startsWith("r4.16xlarge")
					|| p.getInstanceType().toLowerCase().startsWith("x1.16xlarge")
					|| p.getInstanceType().toLowerCase().startsWith("x1.32xlarge")
					|| p.getInstanceType().toLowerCase().startsWith("x1e.32xlarge"));
		} else if (isCluster && SAPInstanceType.HANA_OLAP.equals(SAPInstanceType.getSAPInstanceType(server.getSapInstanceType()))) {
			return p -> ((p.getInstanceType().toLowerCase().startsWith("r3.8xlarge")
					|| p.getInstanceType().toLowerCase().startsWith("x1.16xlarge")
					|| p.getInstanceType().toLowerCase().startsWith("x1.32xlarge")));
		} else if (!isCluster && SAPInstanceType.HANA_OLAP.equals(SAPInstanceType.getSAPInstanceType(server.getSapInstanceType()))) {
			return p -> ((p.getInstanceType().toLowerCase().startsWith("r3.8xlarge")
					|| p.getInstanceType().toLowerCase().startsWith("r4.8xlarge")
					|| p.getInstanceType().toLowerCase().startsWith("r4.16xlarge")
					|| p.getInstanceType().toLowerCase().startsWith("x1.16xlarge")
					|| p.getInstanceType().toLowerCase().startsWith("x1.32xlarge")
					|| p.getInstanceType().toLowerCase().startsWith("x1e.32xlarge")));
		} else if (!isCluster && SAPInstanceType.HANA_B1.equals(SAPInstanceType.getSAPInstanceType(server.getSapInstanceType()))) {
			return p -> ((p.getInstanceType().toLowerCase().startsWith("c3.8xlarge")
					|| p.getInstanceType().toLowerCase().startsWith("m4.10xlarge")
					|| p.getInstanceType().toLowerCase().startsWith("m4.16xlarge")
					|| p.getInstanceType().toLowerCase().startsWith("r3.8xlarge")
					|| p.getInstanceType().toLowerCase().startsWith("x1.16xlarge")));
		} else {
			return p -> (p.getInstanceType().toLowerCase().startsWith("r3.8xlarge")
					|| p.getInstanceType().toLowerCase().startsWith("r4.8xlarge")
					|| p.getInstanceType().toLowerCase().startsWith("r4.16xlarge")
					|| p.getInstanceType().toLowerCase().startsWith("x1.16xlarge")
					|| p.getInstanceType().toLowerCase().startsWith("x1.32xlarge")
					|| p.getInstanceType().toLowerCase().startsWith("x1e.32xlarge"));
		}
	}
	
	public static Predicate<Price> hanaDevQaInstances(InstanceInput server) {
		return  p -> (p.getMemory() >= 61 && (p.getInstanceType().toLowerCase().startsWith("m4")
				|| p.getInstanceType().toLowerCase().startsWith("c4")
				|| p.getInstanceType().toLowerCase().startsWith("r4")
				|| p.getInstanceType().toLowerCase().startsWith("x1")));
	}

	/*public static Predicate<Price> newGeneration(InstanceInput server) {
		return p -> ("No".equals(server.getOnlyCurrentGenerationInstances())
				|| ("Yes".equals(server.getOnlyCurrentGenerationInstances())
						&& (p.getCurrentGeneration() == null || "Yes".equals(p.getCurrentGeneration()))));
	}*/
	
	public static Predicate<Price> newGeneration(InstanceInput server) {
				return p -> ("No".equals(server.getOnlyCurrentGenerationInstances()))
						|| ("Yes".equals(server.getOnlyCurrentGenerationInstances())
								&& (p.getCurrentGeneration() == null || "Yes".equals(p.getCurrentGeneration())));
	}
	
    public static Predicate<Price> ec2(InstanceInput server){
       return p -> !p.getPriceDescription().contains("Dedicated Host") && p.getProductFamily().equals("Compute Instance");
    }

    public static Predicate<Price> region(InstanceInput server){
        return p -> p.getLocation() != null && p.getLocation().equals(server.getRegion());
    }
    
	public static Predicate<S3Price> s3(InstanceInput input) {
		return p -> p.getLocation() != null
				&& p.getLocation().toLowerCase().startsWith(input.getRegion().toLowerCase())
				&& p.getProductFamily().equals(ProductFamily.Storage.toString())
				&& p.getServiceCode().equals(ProductName.AmazonS3.toString())
				&& p.getStorageClass().equals(S3StorageClass.General_Purpose.getColumnName())
				&& p.getEndingRange().equals(Constants.S3EndRange);
	}
    
    public static Predicate<Price> region(DataTransferInput dataTransfer){
        return p -> p.getFromLocation() != null && p.getFromLocation().toLowerCase().startsWith(dataTransfer.getRegion().toLowerCase());
    }

    public static Predicate<Price> cpu(InstanceInput server){
        return p -> server.getCpu() == null || p.getvCPU() >= server.getCpu() * ((1 - server.getCpuTolerance()));
    }
    
    public static Predicate<Price> saps(InstanceInput server){
        return p -> server.getSaps() == null || p.getSaps() >= server.getSaps() * ((1 - server.getCpuTolerance()));
    }

    public static Predicate<Price> memory(InstanceInput server){
        return p -> server.getMemory() == null || p.getMemory() >= server.getMemory() * ((1 - server.getMemoryTolerance()));
    }

    public static Predicate<Price> preInstalledSw(InstanceInput server){
        return p -> p.getPreInstalledSw() != null && p.getPreInstalledSw().equals(server.getPreInstalledSw());
    }

    public static Predicate<Price> termType(InstanceInput server){
        return p -> (p.getTermType() != null && p.getTermType().equals(server.getTermType()));
    }
    
	public static Predicate<Price> leaseContractLength(InstanceInput server) {
		return p -> server.getTermType().equals("OnDemand")
				|| (server.getTermType().equals("Reserved") && p.getLeaseContractLength() != null
						&& p.getLeaseContractLength().equalsIgnoreCase(server.getLeaseContractLength()));
	}

    public static Predicate<Price> purchaseOption(InstanceInput server){
		return p -> server.getTermType().equals("OnDemand")
				|| (server.getTermType().equals("Reserved") && p.getPurchaseOption() != null
						&& p.getPurchaseOption().equalsIgnoreCase(server.getPurchaseOption()));
    }

    public static Predicate<Price> offeringClass(InstanceInput server) {
		return p -> server.getTermType().equals("OnDemand")
				|| (server.getTermType().equals("Reserved") && p.getOfferingClass() != null
						&& p.getOfferingClass().equalsIgnoreCase(server.getOfferingClass()));
    }
    
    public static Predicate<Price> operatingSystem(InstanceInput server){
        return p -> p.getOperatingSystem() != null && p.getOperatingSystem().equals(server.getOperatingSystem());
    }
    
    public static Predicate<Price> licenceModel(InstanceInput server){
        return p -> p.getLicenseModel() != null && p.getLicenseModel().equals("Windows".equals(server.getOperatingSystem()) ? "License Included" : "No License required");
    }

    public static Predicate<Price> tenancy(InstanceInput server) {
        return p -> p.getTenancy() != null && p.getTenancy().equals(server.getTenancy());
    }

    public static Predicate<Price> volumeType(InstanceInput input){
        return p -> p.getVolumeType() != null && p.getVolumeType().equalsIgnoreCase(input.getVolumeType());
    }
    
    public static Predicate<Price> st1(){
        return p -> p.getVolumeType() != null && p.getVolumeType().equalsIgnoreCase(VolumeType.Throughput_Optimized_HDD.getColumnName());
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
