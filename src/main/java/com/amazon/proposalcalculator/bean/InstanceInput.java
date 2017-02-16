package com.amazon.proposalcalculator.bean;

import com.amazon.proposalcalculator.utils.NumbersConverter;
import com.ebay.xcelite.annotations.Column;

public class InstanceInput {
	
	@Column(name = "Description")
	private String description;
	
	@Column(name = "Region")
	private String region;

	@Column(name = "Instances")
	private int instances;

	@Column(name = "CPU")
	private Double cpu;
	
	@Column(name = "SAPS")
	private Integer saps;
	
	@Column(name = "Use SAP Certified Instances")
	private String useSAPCertifiedInstances;

	@Column(name = "Monthly Utilization")
	private double monthlyUtilization;

	@Column(name = "Memory")
	private Double memory;

	@Column(name = "Storage(GB)")
	private Integer storage;

	@Column(name = "Volume Type")
	private String volumeType;

	@Column(name = "IOPS")
	private Integer iops;

	@Column(name = "Snapshot(GB)")
	private Integer snapshot;

	@Column(name = "Term Type")
	private String termType;
	
	@Column(name = "Lease Contract Length")
	private String leaseContractLength;
	
	@Column(name = "Purchase Option")
	private String purchaseOption;
	
	@Column(name = "Offering Class")
	private String offeringClass;

	@Column(name = "Tenancy")
	private String tenancy;

	@Column(name = "Operating System")
	private String operatingSystem;
	
	@Column(name = "Beginning")
	private String beginning;
	
	@Column(name = "End")
	private String end;	
	
	@Column(name = "Pre Installed S/W")
	private String preInstalledSw;
	
	@Column(name = "Only Current Generation Instances")
	private String onlyCurrentGenerationInstances;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getCpu() {
		return cpu;
	}

	public void setCpu(Double cpu) {
		this.cpu = cpu;
	}

	public Double getMemory() {
		return memory;
	}

	public void setMemory(Double memory) {
		this.memory = memory;
	}

	public Integer getStorage() { return storage; }

	public void setStorage(Integer storage) {
		this.storage = storage;
	}

	public String getVolumeType() {
		return volumeType;
	}

	public void setVolumeType(String volumeType) {
		this.volumeType = volumeType;
	}

	public Integer getSnapshot() { return snapshot; }

	public void setSnapshot(Integer snapshot) { this.snapshot = snapshot; }

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

	public Integer getIops() {
		return iops;
	}

	public void setIops(Integer iops) {
		this.iops = iops;
	}

	public double getMonthlyUtilization() {
		return monthlyUtilization;
	}

	public void setMonthlyUtilization(double monthlyUtilization) {
		this.monthlyUtilization = monthlyUtilization;
	}

	public String getPreInstalledSw() {
		return preInstalledSw;
	}

	public void setPreInstalledSw(String preInstalledSw) {
		this.preInstalledSw = preInstalledSw;
	}

	public String getUseSAPCertifiedInstances() {
		return useSAPCertifiedInstances;
	}

	public void setUseSAPCertifiedInstances(String useSAPCertifiedInstances) {
		this.useSAPCertifiedInstances = useSAPCertifiedInstances;
	}

	public Integer getSaps() {
		return saps;
	}

	public void setSaps(Integer saps) {
		this.saps = saps;
	}

	public String getOnlyCurrentGenerationInstances() {
		return onlyCurrentGenerationInstances;
	}

	public void setOnlyCurrentGenerationInstances(String onlyCurrentGenerationInstances) {
		this.onlyCurrentGenerationInstances = onlyCurrentGenerationInstances;
	}

}
