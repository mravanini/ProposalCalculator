package com.amazon.proposalcalculator.bean;

import com.amazon.proposalcalculator.enums.InstanceInputColumn;
import com.amazon.proposalcalculator.exception.PricingCalculatorException;
import com.ebay.xcelite.annotations.Column;

public class InstanceInput {

	public void setCellSAP(InstanceInputColumn column, Object value){
		switch (column){
			case DESCRIPTION:
				setDescription(String.valueOf(value));
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
				setCpu((double)value);
				break;
			case CPU_TOLERANCE:
				setCpuTolerance((double)value);
				break;
			case MEMORY:
				setMemory((double)value);
				break;
			case MEMORY_TOLERANCE:
				setMemoryTolerance((double)value);
				break;
			case MONTHLY_UTILIZATION:
				setMonthlyUtilization((double)value);
				break;
			case STORAGE:
				setStorage(((Double) value).intValue());//(int.parseInt((String)value));
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
				setCpu((double)value);
				break;
			case CPU_TOLERANCE:
				setCpuTolerance((double)value);
				break;
			case MEMORY:
				setMemory((double)value);
				break;
			case MEMORY_TOLERANCE:
				setMemoryTolerance((double)value);
				break;
			case MONTHLY_UTILIZATION:
				setMonthlyUtilization((double)value);
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

	private double cpu;

	private double originalCpu;

	private double cpuTolerance;

	private int saps;

	private int originalSaps;

	private String useSAPCertifiedInstances;

	private double monthlyUtilization;

	private double memory;

	private double originalMemory = 0d;

	private double memoryTolerance;

	private int storage;
	
	private int originalStorage;

	private String volumeType;

	private int iops;

	private int snapshot;
	
	private int originalSnapshot;

	private int archiveLogsLocalBackup;
	
	private int originalArchiveLogsLocalBackup;

	private int s3Backup;
	
	private int originalS3Backup;

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

	public double getOriginalCpu() {
		return originalCpu;
	}

	public void setOriginalCpu(double originalCpu) {
		this.originalCpu = originalCpu;
	}

	public int getOriginalSaps() {
		return originalSaps;
	}

	public void setOriginalSaps(int originalSaps) {
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

	public double getCpu() {
		return cpu;
	}

	public void setCpu(double cpu) {
		this.cpu = cpu;
	}

	public double getMemory() {
		return memory;
	}

	public void setMemory(double memory) {
		this.memory = memory;
	}

	public int getStorage() {
		return storage;
	}

	public void setStorage(int storage) {
		this.storage = storage;
	}

	public String getVolumeType() {
		return volumeType;
	}

	public void setVolumeType(String volumeType) {
		this.volumeType = volumeType;
	}

	public int getSnapshot() {
		return snapshot;
	}

	public void setSnapshot(int snapshot) {
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

	public int getIops() {
		return iops;
	}

	public void setIops(int iops) {
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

	public int getSaps() {
		return saps;
	}

	public void setSaps(int saps) {
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

	public double getCpuTolerance() {
		return cpuTolerance;
	}

	public void setCpuTolerance(double cpuTolerance) {
		this.cpuTolerance = cpuTolerance;
	}

	public double getMemoryTolerance() {
		return memoryTolerance;
	}

	public void setMemoryTolerance(double memoryTolerance) {
		this.memoryTolerance = memoryTolerance;
	}

	public int getArchiveLogsLocalBackup() {
		return archiveLogsLocalBackup;
	}

	public void setArchiveLogsLocalBackup(int archiveLogsLocalBackup) {
		this.archiveLogsLocalBackup = archiveLogsLocalBackup;
	}

	public int getS3Backup() {
		return s3Backup;
	}

	public void setS3Backup(int s3Backup) {
		this.s3Backup = s3Backup;
	}

	public double getOriginalMemory() {
		return originalMemory;
	}

	public void setOriginalMemory(double originalMemory) {
		this.originalMemory = originalMemory;
	}

	public int getOriginalStorage() {
		return originalStorage;
	}

	public void setOriginalStorage(int originalStorage) {
		this.originalStorage = originalStorage;
	}

	public int getOriginalSnapshot() {
		return originalSnapshot;
	}

	public void setOriginalSnapshot(int originalSnapshot) {
		this.originalSnapshot = originalSnapshot;
	}

	public int getOriginalArchiveLogsLocalBackup() {
		return originalArchiveLogsLocalBackup;
	}

	public void setOriginalArchiveLogsLocalBackup(int originalArchiveLogsLocalBackup) {
		this.originalArchiveLogsLocalBackup = originalArchiveLogsLocalBackup;
	}

	public int getOriginalS3Backup() {
		return originalS3Backup;
	}

	public void setOriginalS3Backup(int originalS3Backup) {
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
