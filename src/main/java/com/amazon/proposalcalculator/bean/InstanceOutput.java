package com.amazon.proposalcalculator.bean;

import com.ebay.xcelite.annotations.Column;
import com.ebay.xcelite.annotations.Row;

@Row(colsOrder = {"Description", "Environment", "SAP Instance Type", "Region", "Instances", "SAPS", "CPU", "CPU Tolerance",
		"Memory", "Memory Tolerance", "Monthly Utilization", "Storage(GB)", "Volume Type",
		"IOPS", "Snapshot(GB)", "Archive Logs/Local Backup(GB)", "S3 Backup(GB)", "Term Type", "Lease Contract Length",
		"Purchase Option",
		"Offering Class", "Tenancy", "Operating System", "Pre Installed S/W", "Beginning", "End", "Instance Type",
		"Instance SAPS", "Instance vCPU", "Instance Memory", "Upfront Fee", "Compute Unit Price", "Compute Monthly Price",
		"Compute Total Price", "Storage Monthly Price", "Snapshot Monthly Price", "Archive Logs/Local Backup Monthly Price",
		"S3 Backup Monthly Price", "Use SAP Certified Instances", "Only Current Generation Instances",
		"Test", "Error Message"})

public class InstanceOutput extends InstanceInput {
	
	public InstanceOutput() {
		
	}

	@Column(name = "Instance Type")
	private String instanceType;

	@Column(name = "Instance vCPU")
	private int instanceVCPU;
	
	@Column(name = "Instance SAPS")
	private int instanceSAPS;

	@Column(name = "Instance Memory")
	private Double instanceMemory;
	
	@Column(name = "Upfront Fee")
	private double upfrontFee;

	@Column(name = "Compute Unit Price")
	private double computeUnitPrice;
	
	@Column(name = "Compute Monthly Price")
	private double computeMonthlyPrice;
	
	@Column(name = "Compute Total Price")
	private double computeTotalPrice;

	@Column(name = "Storage Monthly Price")
	private double storageMonthlyPrice;

	@Column(name = "Snapshot Monthly Price")
	private double snapshotMonthlyPrice;
	
	@Column(name = "Archive Logs/Local Backup Monthly Price")
	private double archiveLogsLocalBackupMonthlyPrice;
	
	@Column(name = "S3 Backup Monthly Price")
	private double s3BackupMonthlyPrice;

	@Column(name = "Test")
	private String test = "=(1+2)";

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

	public int getInstanceSAPS() {
		return instanceSAPS;
	}

	public void setInstanceSAPS(int instanceSAPS) {
		this.instanceSAPS = instanceSAPS;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
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
