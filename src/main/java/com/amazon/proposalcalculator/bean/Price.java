package com.amazon.proposalcalculator.bean;

import com.amazon.proposalcalculator.utils.Constants;
import com.amazon.proposalcalculator.utils.MemoryConverter;
import com.ebay.xcelite.annotations.Column;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;

public class Price {
	
	//Compute Instance
	
	@CsvBindByName(column="Product Family")
	private String productFamily;
	
	@CsvBindByName(column="Instance Type")
	private String instanceType;
	
	@CsvBindByName(column="vCPU")
	private int vCPU;
	
	//2 GiB
	@CsvCustomBindByName(column="Memory", converter=MemoryConverter.class)
	private double memory;
	
	@CsvBindByName(column="PricePerUnit")
	private double pricePerUnit;
	
	// South America (Sao Paulo) / US East (N. Virginia)
	@CsvBindByName(column="Location")
	private String location;
	
	//Yes / No
	@CsvBindByName(column="Current Generation")
	private String currentGeneration;
	
	//Reserve OnDemand
	@CsvBindByName(column="TermType")
	private String termType;
	
	//RHEL Windows SUSE Linux
	@CsvBindByName(column="Operating System")
	private String operatingSystem;
	
	//standard convertible
	@CsvBindByName(column="OfferingClass")
	private String offeringClass;
	
	//All Upfront / Partial Upfront / No Upfront
	@CsvBindByName(column="PurchaseOption")
	private String purchaseOption;
	
	//Shared
	@CsvBindByName(column="Tenancy")
	private String tenancy;
	
	@CsvBindByName(column = "LeaseContractLength")
	private String leaseContractLength;

	@CsvBindByName(column = "Volume Type")
	private String volumeType;

	@CsvBindByName(column = "Group")
	private String group;
	
	@CsvBindByName(column = "PriceDescription")
	private String priceDescription;
	
	@CsvBindByName(column = "SKU")
	private String sku;
	
	@CsvBindByName(column = "Pre Installed S/W")
	private String preInstalledSw;
	
	@CsvBindByName(column = "License Model")
	private String licenseModel;
	
	@CsvBindByName(column = "Transfer Type")
	private String transferType;
	
	@CsvBindByName(column = "StartingRange")
	private String startingRange;
	
	@CsvBindByName(column = "EndingRange")
	private String endingRange;
	
	@CsvBindByName(column = "From Location")
	private String fromLocation;
	
	@CsvBindByName(column = "CapacityStatus")
	private String capacityStatus;
	
	public String getCapacityStatus() {
		return capacityStatus;
	}

	public void setCapacityStatus(String capacityStatus) {
		this.capacityStatus = capacityStatus;
	}

	private double upfrontFee;
	
	private double efectivePrice;
	
	private double instanceHourPrice;
	
	private int saps;

	@Override
	public String toString() {
		return "Price [productFamily=" + productFamily + ", instanceType=" + instanceType + ", vCPU=" + vCPU
				+ ", memory=" + memory + ", pricePerUnit=" + pricePerUnit + ", location=" + location
				+ ", currentGeneration=" + currentGeneration + ", termType=" + termType + ", operatingSystem="
				+ operatingSystem + ", offeringClass=" + offeringClass + ", purchaseOption=" + purchaseOption
				+ ", tenancy=" + tenancy + ", leaseContractLength=" + leaseContractLength + ", volumeType=" + volumeType
				+ ", group=" + group + ", priceDescription=" + priceDescription + ", sku=" + sku + ", preInstalledSw="
				+ preInstalledSw + ", upfrontFee=" + upfrontFee + ", efectivePrice=" + efectivePrice
				+ ", instanceHourPrice=" + instanceHourPrice + "]";
	}

	public String getTenancy() {
		return tenancy;
	}

	public void setTenancy(String tenancy) {
		this.tenancy = tenancy;
	}

	public String getLeaseContractLength() {
		return leaseContractLength;
	}

	public void setLeaseContractLength(String leaseContractLength) {
		this.leaseContractLength = leaseContractLength;
	}

	public String getProductFamily() {
		return productFamily;
	}

	public void setProductFamily(String productFamily) {
		this.productFamily = productFamily;
	}

	public String getInstanceType() {
		return instanceType;
	}

	public void setInstanceType(String instanceType) {
		this.instanceType = instanceType;
	}

	public int getvCPU() {
		return vCPU;
	}

	public void setvCPU(int vCPU) {
		this.vCPU = vCPU;
	}

	public double getMemory() {
		return (int) Math.round(memory * Constants.GIB_TO_GB);
	}

	public void setMemory(double memory) {
		this.memory = memory;
	}

	public double getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCurrentGeneration() {
		return currentGeneration;
	}

	public void setCurrentGeneration(String currentGeneration) {
		this.currentGeneration = currentGeneration;
	}

	public String getTermType() {
		return termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	public String getOfferingClass() {
		return offeringClass;
	}

	public void setOfferingClass(String offeringClass) {
		this.offeringClass = offeringClass;
	}

	public String getPurchaseOption() {
		return purchaseOption;
	}

	public void setPurchaseOption(String purchaseOption) {
		this.purchaseOption = purchaseOption;
	}

	public String getVolumeType() {
		return volumeType;
	}

	public void setVolumeType(String volumeType) {
		this.volumeType = volumeType;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public double getUpfrontFee() {
		return upfrontFee;
	}

	public void setUpfrontFee(double upfrontFee) {
		this.upfrontFee = upfrontFee;
	}

	public String getPriceDescription() {
		return priceDescription;
	}

	public void setPriceDescription(String priceDescription) {
		this.priceDescription = priceDescription;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public double getEfectivePrice() {
		return efectivePrice;
	}

	public void setEfectivePrice(double efectivePrice) {
		this.efectivePrice = efectivePrice;
	}

	public double getInstanceHourPrice() {
		return instanceHourPrice;
	}

	public void setInstanceHourPrice(double instanceHourPrice) {
		this.instanceHourPrice = instanceHourPrice;
	}

	public String getPreInstalledSw() {
		return preInstalledSw;
	}

	public void setPreInstalledSw(String preInstalledSw) {
		this.preInstalledSw = preInstalledSw;
	}

	public String getLicenseModel() {
		return licenseModel;
	}

	public void setLicenseModel(String licenseModel) {
		this.licenseModel = licenseModel;
	}

	public String getTransferType() {
		return transferType;
	}

	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}

	public String getStartingRange() {
		return startingRange;
	}
	
	public long getStartingRangeAsLong() {
		try {
			return Long.valueOf(startingRange);
		} catch (NumberFormatException e) {
			return Long.MAX_VALUE;
		}
	}

	public void setStartingRange(String startingRange) {
		this.startingRange = startingRange;
	}

	public String getEndingRange() {
		return endingRange;
	}
	
	public long getEndingRangeAsLong() {
		try {
			return Long.valueOf(endingRange);
		} catch (NumberFormatException e) {
			return Long.MAX_VALUE;
		}
	}

	public void setEndingRange(String endingRange) {
		this.endingRange = endingRange;
	}

	public String getFromLocation() {
		return fromLocation;
	}

	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}

	public int getSaps() {
		return saps;
	}

	public void setSaps(int saps) {
		this.saps = saps;
	}
}
