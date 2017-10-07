package com.amazon.proposalcalculator.bean;

import com.amazon.proposalcalculator.exception.PricingCalculatorException;
import com.ebay.xcelite.annotations.Column;

public class InstanceInput {

	public static final String SAP_INSTANCE_TYPE = "SAP Instance Type";
	public static final String SAPS = "SAPS";

	public void setCellSAP(int column, Object value){
		switch (column){
			case 0:
				setDescription((String) value);
				break;
			case 1:
				setEnvironment((String) value);
				break;
			case 2:
				setSapInstanceType((String) value);
				break;
			case 3:
				setRegion((String) value);
				break;
			case 4:
				setSaps(((Double) value).intValue());
				break;
			case 5:
				setCpu((Double)value);
				break;
			case 6:
				setCpuTolerance((Double)value);
				break;
			case 7:
				setMemory((Double)value);
				break;
			case 8:
				setMemoryTolerance((Double)value);
				break;
			case 9:
				setMonthlyUtilization((Double)value);
				break;
			case 10:
				setStorage(((Double) value).intValue());//(Integer.parseInt((String)value));
				break;
			case 11:
				setVolumeType((String)value);
				break;
			case 12:
				setIops(((Double) value).intValue());
				break;
			case 13:
				setSnapshot(((Double) value).intValue());
				break;
			case 14:
				setArchiveLogsLocalBackup(((Double) value).intValue());
				break;
			case 15:
				setS3Backup(((Double) value).intValue());
				break;
			case 16:
				setOperatingSystem((String)value);
				break;
			case 17:
				setBillingOption((String)value);
				break;
			default:
				throw new PricingCalculatorException("This column option doesn't exist in the input sheet: " +
						column);
		}
	}

	public void setCellGeneric(int column, Object value){
		switch (column){
			case 0:
				setDescription((String) value);
				break;
			case 1:
				setInstances(((Double)value).intValue());
				break;
			case 2:
				setEnvironment((String) value);
				break;
			case 3:
				setRegion((String) value);
				break;
			case 4:
				setCpu((Double)value);
				break;
			case 5:
				setCpuTolerance((Double)value);
				break;
			case 6:
				setMemory((Double)value);
				break;
			case 7:
				setMemoryTolerance((Double)value);
				break;
			case 8:
				setMonthlyUtilization((Double)value);
				break;
			case 9:
				setStorage(((Double) value).intValue());
				break;
			case 10:
				setVolumeType((String)value);
				break;
			case 11:
				setIops(((Double) value).intValue());
				break;
			case 12:
				setSnapshot(((Double) value).intValue());
				break;
			case 13:
				setS3Backup(((Double) value).intValue());
				break;
			case 14:
				setOperatingSystem((String)value);
				break;
			case 15:
				setOnlyCurrentGenerationInstances((String)value);
				break;
			case 16:
				setUseBurstablePerformance((String)value);
				break;
			case 17:
				setPreInstalledSw((String)value);
				break;
			case 18:
				setBillingOption((String)value);
				break;
			default:
				throw new PricingCalculatorException("This column option doesn't exist in the input sheet: " +
						column);
		}
	}

	@Column(name = "Description")
	private String description;

	@Column(name = "Region")
	private String region;

	@Column(name = "Instances")
	private int instances;

	@Column(name = "Environment")
	private String environment;

	@Column(name = SAP_INSTANCE_TYPE)
	private String sapInstanceType;

	@Column(name = "CPU")
	private Double cpu;

	@Column(name = "Original CPU")
	private Double originalCpu;

	@Column(name = "CPU Tolerance")
	private Double cpuTolerance;

	@Column(name = SAPS)
	private Integer saps;

	@Column(name = "Original SAPS")
	private Integer originalSaps;

	@Column(name = "Use SAP Certified Instances")
	private String useSAPCertifiedInstances;

	@Column(name = "Monthly Utilization")
	private Double monthlyUtilization;

	@Column(name = "Memory(GB)")
	private Double memory;

	@Column(name = "Original Memory")
	private Double originalMemory = 0d;

	@Column(name = "Memory Tolerance")
	private Double memoryTolerance;

	@Column(name = "Storage(GB)")
	private Integer storage;
	
	private Integer originalStorage;

	@Column(name = "Volume Type")
	private String volumeType;

	@Column(name = "IOPS")
	private Integer iops;

	@Column(name = "Snapshot(GB)")
	private Integer snapshot;
	
	private Integer originalSnapshot;

	@Column(name = "Archive Logs/Local Backup(GB)")
	private Integer archiveLogsLocalBackup;
	
	private Integer originalArchiveLogsLocalBackup;

	@Column(name = "S3 Backup(GB)")
	private Integer s3Backup;
	
	private Integer originalS3Backup;

	@Column(name = "Term Type")
	private String termType;

	@Column(name = "Lease Contract Length")
	private String leaseContractLength;
	public static String leaseContractLengthColumn = "%s!S%d";

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
	
	@Column(name = "Use Burstable Performance")
	private String useBurstablePerformance;

	private String errorMessageInput;
	
	@Column(name = "Billing Option")
	private String billingOption;

	public Double getOriginalCpu() {
		return originalCpu;
	}

	public void setOriginalCpu(Double originalCpu) {
		this.originalCpu = originalCpu;
	}

	public Integer getOriginalSaps() {
		return originalSaps;
	}

	public void setOriginalSaps(Integer originalSaps) {
		this.originalSaps = originalSaps;
	}

	public String getErrorMessageInput() {
		return this.errorMessageInput;
	}

	public void setErrorMessageInput(String errorMessageInput) {
		if (this.errorMessageInput != null) {
			this.errorMessageInput = this.errorMessageInput + "\n" + errorMessageInput;
		} else {
			this.errorMessageInput = errorMessageInput;
		}
	}

	public boolean hasErrors() {
		return this.errorMessageInput != null;
	}

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

	public Integer getStorage() {
		return storage;
	}

	public void setStorage(Integer storage) {
		this.storage = storage;
	}

	public String getVolumeType() {
		return volumeType;
	}

	public void setVolumeType(String volumeType) {
		this.volumeType = volumeType;
	}

	public Integer getSnapshot() {
		return snapshot;
	}

	public void setSnapshot(Integer snapshot) {
		this.snapshot = snapshot;
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

	public Integer getIops() {
		return iops;
	}

	public void setIops(Integer iops) {
		this.iops = iops;
	}

	public Double getMonthlyUtilization() {
		return monthlyUtilization;
	}

	public void setMonthlyUtilization(Double monthlyUtilization) {
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

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getSapInstanceType() {
		return sapInstanceType;
	}

	public void setSapInstanceType(String sapInstanceType) {
		this.sapInstanceType = sapInstanceType;
	}

	public Double getCpuTolerance() {
		return cpuTolerance;
	}

	public void setCpuTolerance(Double cpuTolerance) {
		this.cpuTolerance = cpuTolerance;
	}

	public Double getMemoryTolerance() {
		return memoryTolerance;
	}

	public void setMemoryTolerance(Double memoryTolerance) {
		this.memoryTolerance = memoryTolerance;
	}

	public Integer getArchiveLogsLocalBackup() {
		return archiveLogsLocalBackup;
	}

	public void setArchiveLogsLocalBackup(Integer archiveLogsLocalBackup) {
		this.archiveLogsLocalBackup = archiveLogsLocalBackup;
	}

	public Integer getS3Backup() {
		return s3Backup;
	}

	public void setS3Backup(Integer s3Backup) {
		this.s3Backup = s3Backup;
	}

	public Double getOriginalMemory() {
		return originalMemory;
	}

	public void setOriginalMemory(Double originalMemory) {
		this.originalMemory = originalMemory;
	}

	public Integer getOriginalStorage() {
		return originalStorage;
	}

	public void setOriginalStorage(Integer originalStorage) {
		this.originalStorage = originalStorage;
	}

	public Integer getOriginalSnapshot() {
		return originalSnapshot;
	}

	public void setOriginalSnapshot(Integer originalSnapshot) {
		this.originalSnapshot = originalSnapshot;
	}

	public Integer getOriginalArchiveLogsLocalBackup() {
		return originalArchiveLogsLocalBackup;
	}

	public void setOriginalArchiveLogsLocalBackup(Integer originalArchiveLogsLocalBackup) {
		this.originalArchiveLogsLocalBackup = originalArchiveLogsLocalBackup;
	}

	public Integer getOriginalS3Backup() {
		return originalS3Backup;
	}

	public void setOriginalS3Backup(Integer originalS3Backup) {
		this.originalS3Backup = originalS3Backup;
	}

	public String getBillingOption() {
		return billingOption;
	}

	public void setBillingOption(String billingOption) {
		this.billingOption = billingOption;
	}

	public String getUseBurstablePerformance() {
		return useBurstablePerformance;
	}

	public void setUseBurstablePerformance(String useBurstablePerformance) {
		this.useBurstablePerformance = useBurstablePerformance;
	}
	
	

}
