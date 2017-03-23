package com.amazon.proposalcalculator.bean;

import com.amazon.proposalcalculator.exception.PricingCalculatorException;
import com.ebay.xcelite.annotations.Column;
import com.ebay.xcelite.annotations.Row;

@Row(colsOrder = {"Description", "Environment", "SAP Instance Type", "Region", "Instances", "SAPS", "CPU", "CPU Tolerance",
		"Memory", "Memory Tolerance", "Monthly Utilization", "Storage(GB)", "Volume Type",
		"IOPS", "Snapshot(GB)", "Archive Logs/Local Backup(GB)", "S3 Backup(GB)", "Term Type", "Lease Contract Length",
		"Purchase Option",
		"Offering Class", "Tenancy", "Operating System", "Pre Installed S/W", "Beginning", "End", "Instance Type",
		"Instance SAPS", "Instance vCPU", "Instance Memory", "Upfront Fee", "Compute Unit Price", "Compute Monthly Price",
		"Compute Total Price", "Storage Monthly Price", "Snapshot Monthly Price", "Archive Logs/Local Backup Monthly Price",
		"S3 Backup Monthly Price", "Use SAP Certified Instances", "Only Current Generation Instances", "Original Memory", "Original CPU",
		"Original SAPS", "Error Message"})

public class InstanceOutput extends InstanceInput {

	public static final int CPU_TOLERANCE = 7;
	public static final int MEMORY_TOLERANCE = 9;
	public static final int UPFRONT_FEE = 27;
	public static final int COMPUTE_UNIT_PRICE = 28;
	public static final int COMPUTE_MONTHLY_PRICE = 29;
	public static final int STORAGE_MONTHLY_PRICE = 30;
	public static final int SNAPSHOT_MONTHLY_PRICE = 31;
	public static final int ARCHIVE_LOGS_MONTHLY_PRICE = 32;
	public static final int S3_BACKUP_MONTHLY_PRICE = 33;

	public static String[] titles = {"Description", "Environment", "SAP Instance Type", "Region", "Instances", "SAPS",
			"CPU", "CPU Tolerance","Memory", "Memory Tolerance", "Monthly Utilization", "Storage(GB)", "Volume Type",
			"IOPS", "Snapshot(GB)", "Archive Logs/Local Backup(GB)", "S3 Backup(GB)", "Term Type", "Lease Contract Length",
			"Purchase Option", "Offering Class", "Tenancy", "Operating System",
			"Instance Type", "Instance SAPS", "Instance vCPU", "Instance Memory", "Upfront Fee", "Compute Unit Price",
			"Compute Monthly Price", "Storage Monthly Price", "Snapshot Monthly Price",
			"Archive Logs/Local Backup Monthly Price", "S3 Backup Monthly Price", "Error Message"};


	public Object getCell(int column){
		switch (column){
			case 0:
				return getDescription();
			case 1:
				return getEnvironment();
			case 2:
				return getSapInstanceType();
			case 3:
				return getRegion();
			case 4:
				return new Integer(getInstances());
			case 5:
				return getSaps();
			case 6:
				return getCpu();
			case CPU_TOLERANCE:
				return getCpuTolerance();
			case 8:
				return getMemory();
			case MEMORY_TOLERANCE:
				return getMemoryTolerance();
			case 10:
				return getMonthlyUtilization();
			case 11:
				return getStorage();
			case 12:
				return getVolumeType();
			case 13:
				return getIops();
			case 14:
				return getSnapshot();
			case 15:
				return getArchiveLogsLocalBackup();
			case 16:
				return getS3Backup();
			case 17:
				return getTermType();
			case 18:
				return getLeaseContractLength();
			case 19:
				return getPurchaseOption();
			case 20:
				return getOfferingClass();
			case 21:
				return getTenancy();
			case 22:
				return getOperatingSystem();
			case 23:
				return getInstanceType();
			case 24:
				return getInstanceSAPS();
			case 25:
				return new Integer(getInstanceVCPU());
			case 26:
				return getInstanceMemory();
			case UPFRONT_FEE:
				return new Double(getUpfrontFee());
			case COMPUTE_UNIT_PRICE:
				return new Double(getComputeUnitPrice());
			case COMPUTE_MONTHLY_PRICE:
				return new Double(getComputeMonthlyPrice());
			case STORAGE_MONTHLY_PRICE:
				return new Double(getStorageMonthlyPrice());
			case SNAPSHOT_MONTHLY_PRICE:
				return new Double(getSnapshotMonthlyPrice());
			case ARCHIVE_LOGS_MONTHLY_PRICE:
				return new Double(getArchiveLogsLocalBackupMonthlyPrice());
			case S3_BACKUP_MONTHLY_PRICE:
				return new Double(getS3BackupMonthlyPrice());
			case 34:
				return getErrorMessage();
			default:
				throw new PricingCalculatorException("This collumn option doesn't exist: " + column);
		}
	}

	public static int getCollumnCount(){
		return titles.length;
	}

	
	public InstanceOutput() {
		
	}

	@Column(name = "Instance Type")
	private String instanceType;

	@Column(name = "Instance vCPU")
	private int instanceVCPU;
	
	@Column(name = "Instance SAPS")
	private Integer instanceSAPS;

	@Column(name = "Instance Memory")
	private Double instanceMemory;
	
	@Column(name = "Upfront Fee")
	private double upfrontFee;
	public static String upfrontFeeColumn = "%s!AE%d";

	@Column(name = "Compute Unit Price")
	private double computeUnitPrice;
	
	@Column(name = "Compute Monthly Price")
	private double computeMonthlyPrice;
	public static String computeMonthlyPriceColumn = "%s!AG%d";

	@Column(name = "Compute Total Price")
	private double computeTotalPrice;

	@Column(name = "Storage Monthly Price")
	private double storageMonthlyPrice;
	public static String storageMonthlyPriceColumn = "%s!AI%d";

	@Column(name = "Snapshot Monthly Price")
	private double snapshotMonthlyPrice;
	public static String snapshotMonthlyPriceColumn = "%s!AJ%d";

	@Column(name = "Archive Logs/Local Backup Monthly Price")
	private double archiveLogsLocalBackupMonthlyPrice;
	public static String archiveLogsLocalBackupMonthlyPriceColumn = "%s!AK%d";

	@Column(name = "S3 Backup Monthly Price")
	private double s3BackupMonthlyPrice;
	public static String s3BackupMonthlyPriceColumn = "%s!AL%d";

	@Column(name = "Error Message")
	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		if(this.errorMessage == null) {
			this.errorMessage = errorMessage;
		}else{
			this.errorMessage = this.errorMessage + " \n " + errorMessage;
		}
	}

	public String getInstanceType() {
		return instanceType;
	}

	public void setInstanceType(String instanceType) {
		this.instanceType = instanceType;
	}

	public int getInstanceVCPU() {
		return instanceVCPU;
	}

	public void setInstanceVCPU(int instanceVCPU) {
		this.instanceVCPU = instanceVCPU;
	}

	public Double getInstanceMemory() {
		return instanceMemory;
	}

	public void setInstanceMemory(Double instanceMemory) {
		this.instanceMemory = instanceMemory;
	}

	public double getComputeUnitPrice() {
		return computeUnitPrice;
	}

	public void setComputeUnitPrice(double computeUnitPrice) {
		this.computeUnitPrice = computeUnitPrice;
	}

	public double getComputeMonthlyPrice() {
		return computeMonthlyPrice;
	}

	public void setComputeMonthlyPrice(double computeMonthlyPrice) {
		this.computeMonthlyPrice = computeMonthlyPrice;
	}

	public double getComputeTotalPrice() {
		return computeTotalPrice;
	}

	public void setComputeTotalPrice(double computeTotalPrice) {
		this.computeTotalPrice = computeTotalPrice;
	}

	public double getStorageMonthlyPrice() {
		return storageMonthlyPrice;
	}

	public void setStorageMonthlyPrice(double storageMonthlyPrice) {
		this.storageMonthlyPrice = storageMonthlyPrice;
	}

	public double getSnapshotMonthlyPrice() {
		return snapshotMonthlyPrice;
	}

	public void setSnapshotMonthlyPrice(double snapshotMonthlyPrice) {
		this.snapshotMonthlyPrice = snapshotMonthlyPrice;
	}

	public double getUpfrontFee() {
		return upfrontFee;
	}

	public void setUpfrontFee(double upfrontFee) {
		this.upfrontFee = upfrontFee;
	}

	public Integer getInstanceSAPS() {
		return instanceSAPS;
	}

	public void setInstanceSAPS(Integer instanceSAPS) {
		this.instanceSAPS = instanceSAPS;
	}

	public double getArchiveLogsLocalBackupMonthlyPrice() {
		return archiveLogsLocalBackupMonthlyPrice;
	}

	public void setArchiveLogsLocalBackupMonthlyPrice(double archiveLogsLocalBackupMonthlyPrice) {
		this.archiveLogsLocalBackupMonthlyPrice = archiveLogsLocalBackupMonthlyPrice;
	}

	public double getS3BackupMonthlyPrice() {
		return s3BackupMonthlyPrice;
	}

	public void setS3BackupMonthlyPrice(double s3BackupMonthlyPrice) {
		this.s3BackupMonthlyPrice = s3BackupMonthlyPrice;
	}
}
