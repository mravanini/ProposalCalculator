package com.amazon.proposalcalculator.bean;

import com.amazon.proposalcalculator.enums.InstanceInputColumn;
import com.amazon.proposalcalculator.exception.PricingCalculatorException;
import com.ebay.xcelite.annotations.Column;

public class InstanceInput {

	public void setCellSAP(InstanceInputColumn column, Object value){
		switch (column){
			case DESCRIPTION:
				setDescription((String) value);
				break;
			case ENVIRONMENT:
				setEnvironment((String) value);
				break;
			case SAP_INSTANCE_TYPE:
				setSapInstanceType((String) value);
				break;
			case REGION:
				setRegion((String) value);
				break;
			case SAPS:
				setSaps(((Double) value).intValue());
				break;
			case CPU:
				setCpu((Double)value);
				break;
			case CPU_TOLERANCE:
				setCpuTolerance((Double)value);
				break;
			case MEMORY:
				setMemory((Double)value);
				break;
			case MEMORY_TOLERANCE:
				setMemoryTolerance((Double)value);
				break;
			case MONTHLY_UTILIZATION:
				setMonthlyUtilization((Double)value);
				break;
			case STORAGE:
				setStorage(((Double) value).intValue());//(Integer.parseInt((String)value));
				break;
			case VOLUME_TYPE:
				setVolumeType((String)value);
				break;
			case IOPS:
				setIops(((Double) value).intValue());
				break;
			case SNAPSHOT:
				setSnapshot(((Double) value).intValue());
				break;
			case ARCHIVE_LOGS_LOCAL_BACKUP:
				setArchiveLogsLocalBackup(((Double) value).intValue());
				break;
			case S3_BACKUP:
				setS3Backup(((Double) value).intValue());
				break;
			case OPERATING_SYSTEM:
				setOperatingSystem((String)value);
				break;
			case BILLING_OPTION:
				setBillingOption((String)value);
				break;
		}
	}

	public void setCellGeneric(InstanceInputColumn column, Object value){
		switch (column){
			case DESCRIPTION:
				setDescription((String) value);
				break;
			case INSTANCES:
				setInstances(((Double)value).intValue());
				break;
			case ENVIRONMENT:
				setEnvironment((String) value);
				break;
			case REGION:
				setRegion((String) value);
				break;
			case CPU:
				setCpu((Double)value);
				break;
			case CPU_TOLERANCE:
				setCpuTolerance((Double)value);
				break;
			case MEMORY:
				setMemory((Double)value);
				break;
			case MEMORY_TOLERANCE:
				setMemoryTolerance((Double)value);
				break;
			case MONTHLY_UTILIZATION:
				setMonthlyUtilization((Double)value);
				break;
			case STORAGE:
				setStorage(((Double) value).intValue());
				break;
			case VOLUME_TYPE:
				setVolumeType((String)value);
				break;
			case IOPS:
				setIops(((Double) value).intValue());
				break;
			case SNAPSHOT:
				setSnapshot(((Double) value).intValue());
				break;
			case S3_BACKUP:
				setS3Backup(((Double) value).intValue());
				break;
			case OPERATING_SYSTEM:
				setOperatingSystem((String)value);
				break;
			case ONLY_CURRENT_GENERATION_INSTANCES:
				setOnlyCurrentGenerationInstances((String)value);
				break;
			case USE_BURSTABLE_PERFORMANCE:
				setUseBurstablePerformance((String)value);
				break;
			case PRE_INSTALLED_SOFTWARE:
				setPreInstalledSw((String)value);
				break;
			case BILLING_OPTION:
				setBillingOption((String)value);
				break;
		}
	}

	private String description;

	private String region;

	private int instances;

	private String environment;

	private String sapInstanceType;

	private Double cpu;

	private Double originalCpu;

	private Double cpuTolerance;

	private Integer saps;

	private Integer originalSaps;

	private String useSAPCertifiedInstances;

	private Double monthlyUtilization;

	private Double memory;

	private Double originalMemory = 0d;

	private Double memoryTolerance;

	private Integer storage;
	
	private Integer originalStorage;

	private String volumeType;

	private Integer iops;

	private Integer snapshot;
	
	private Integer originalSnapshot;

	private Integer archiveLogsLocalBackup;
	
	private Integer originalArchiveLogsLocalBackup;

	private Integer s3Backup;
	
	private Integer originalS3Backup;

	private String termType;

	private String leaseContractLength;
	public static String leaseContractLengthColumn = "%s!S%d";

	private String purchaseOption;

	private String offeringClass;

	private String tenancy;

	private String operatingSystem;

	private String beginning;

	private String end;

	private String preInstalledSw;

	private String onlyCurrentGenerationInstances;
	
	private String useBurstablePerformance;

	private String errorMessageInput;
	
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
