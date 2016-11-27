package com.amazon.sample.bean;

import com.ebay.xcelite.annotations.Column;

public class DefaultInput {
	
	@Column(name = "Description")
	private String description;
	
	@Column(name = "Region")
	private String region;
	
	@Column(name = "Term Type")
	private String termType;
	
	@Column(name = "Lease Contract Length")
	private String leaseContractLength;
	
	@Column(name = "Purchase Option")
	private String purchaseOption;
	
	@Column(name = "Offering Class")
	private String offeringClass;
	
	@Column(name = "Instances")
	private int instances;

	@Column(name = "CPU")
	private float cpu;

	@Column(name = "Memory")
	private float memory;

	@Column(name = "Disk")
	private int disk;
	
	@Column(name = "Usage")
	private int usage;
	
	@Column(name = "Tenancy")
	private String tenancy;
	
	@Column(name = "Operating System")
	private String operatingSystem;
	
	@Column(name = "Beginning")
	private String beginning;
	
	@Column(name = "End")
	private String end;	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getCpu() {
		return cpu;
	}

	public void setCpu(float cpu) {
		this.cpu = cpu;
	}

	public float getMemory() {
		return memory;
	}

	public void setMemory(float memory) {
		this.memory = memory;
	}

	public int getDisk() {
		return disk;
	}

	public void setDisk(int disk) {
		this.disk = disk;
	}

	public int getUsage() {
		return usage;
	}

	public void setUsage(int usage) {
		this.usage = usage;
	}

	public String getBeginning() {
		return beginning;
	}

	public void setBeginning(String beginning) {
		this.beginning = beginning;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public int getInstances() {
		return instances;
	}

	public void setInstances(int instances) {
		this.instances = instances;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getTermType() {
		return termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
	}

	public String getTenancy() {
		return tenancy;
	}

	public void setTenancy(String tenancy) {
		this.tenancy = tenancy;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	public String getLeaseContractLength() {
		return leaseContractLength;
	}

	public void setLeaseContractLength(String leaseContractLength) {
		this.leaseContractLength = leaseContractLength;
	}

	public String getPurchaseOption() {
		return purchaseOption;
	}

	public void setPurchaseOption(String purchaseOption) {
		this.purchaseOption = purchaseOption;
	}

	public String getOfferingClass() {
		return offeringClass;
	}

	public void setOfferingClass(String offeringClass) {
		this.offeringClass = offeringClass;
	}
	
}
