package com.amazon.proposalcalculator.bean;

import com.amazon.proposalcalculator.utils.MemoryConverter;
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
	private float memory;
	
	@CsvBindByName(column="PricePerUnit")
	private float pricePerUnit;
	
	// South America (Sao Paulo) / US East (N. Virginia)
	@CsvBindByName(column="Location")
	private String location;
	
	//Yes / No
	@CsvBindByName(column="Current Generation")
	private boolean currentGeneration;
	
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
	
	private float upfrontFee;
	
	private float efectivePrice;
	
	private float instanceHourPrice;

	@Override
	public String toString() {
		return "Price [productFamily=" + productFamily + ", instanceType=" + instanceType + ", vCPU=" + vCPU
				+ ", memory=" + memory + ", pricePerUnit=" + pricePerUnit + ", location=" + location
				+ ", currentGeneration=" + currentGeneration + ", termType=" + termType + ", operatingSystem="
				+ operatingSystem + ", offeringClass=" + offeringClass + ", purchaseOption=" + purchaseOption
				+ ", volumeType=" + volumeType + ", group=" + group
				+ "]";
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

	public float getMemory() {
		return memory;
	}

	public void setMemory(float memory) {
		this.memory = memory;
	}

	public float getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(float pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isCurrentGeneration() {
		return currentGeneration;
	}

	public void setCurrentGeneration(boolean currentGeneration) {
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

	public float getUpfrontFee() {
		return upfrontFee;
	}

	public void setUpfrontFee(float upfrontFee) {
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

	public float getEfectivePrice() {
		return efectivePrice;
	}

	public void setEfectivePrice(float efectivePrice) {
		this.efectivePrice = efectivePrice;
	}

	public float getInstanceHourPrice() {
		return instanceHourPrice;
	}

	public void setInstanceHourPrice(float instanceHourPrice) {
		this.instanceHourPrice = instanceHourPrice;
	}
}
