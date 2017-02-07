package com.amazon.proposalcalculator.bean;

import com.ebay.xcelite.annotations.Column;
import com.ebay.xcelite.annotations.Row;

@Row(colsOrder = {"Description", "Region", "Instances", "CPU", "Monthly Utilization", "Memory", "Storage(GB)", "Volume Type",
		"IOPS", "Snapshot(GB)", "Term Type", "Lease Contract Length", "Purchase Option",
		"Offering Class", "Tenancy", "Operating System", "Pre Installed S/W", "Beginning", "End", "Instance Type",
		"Instance vCPU", "Instance Memory", "Upfront Fee", "Compute Unit Price", "Compute Monthly Price",
		"Compute Total Price", "Storage Monthly Price", "Snapshot Monthly Price", "Error Message"})

public class DefaultOutput extends DefaultInput {
	
	public DefaultOutput() {
		
	}

	@Column(name = "Instance Type")
	private String instanceType;

	@Column(name = "Instance vCPU")
	private int instanceVCPU;

	@Column(name = "Instance Memory")
	private double instanceMemory;
	
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

	@Column(name = "Error Message")
	private String errorMessage;


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

	public double getInstanceMemory() {
		return instanceMemory;
	}

	public void setInstanceMemory(double instanceMemory) {
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

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {

		if (this.errorMessage != null) {
			this.errorMessage = this.errorMessage +"\n" + errorMessage;
		}else{
			this.errorMessage = errorMessage;
		}
	}

	public double getUpfrontFee() {
		return upfrontFee;
	}

	public void setUpfrontFee(double upfrontFee) {
		this.upfrontFee = upfrontFee;
	}
}
