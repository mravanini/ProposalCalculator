package com.amazon.proposalcalculator.bean;

import com.ebay.xcelite.annotations.Column;
import com.ebay.xcelite.annotations.Row;

@Row(colsOrder = {"Description", "Region", "Instances", "CPU", "CPU Usage", "Memory", "Storage(GB)", "Volume Type",
		"IOPS", "Snapshot(GB)", "Term Type", "Lease Contract Length", "Purchase Option",
		"Offering Class", "Tenancy", "Operating System", "Beginning", "End", "Instance Type",
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
	private float instanceMemory;
	
	@Column(name = "Upfront Fee")
	private float upfrontFee;

	@Column(name = "Compute Unit Price")
	private float computeUnitPrice;
	
	@Column(name = "Compute Monthly Price")
	private float computeMonthlyPrice;
	
	@Column(name = "Compute Total Price")
	private float computeTotalPrice;

	@Column(name = "Storage Monthly Price")
	private float storageMonthlyPrice;

	@Column(name = "Snapshot Monthly Price")
	private float snapshotMonthlyPrice;

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

	public float getInstanceMemory() {
		return instanceMemory;
	}

	public void setInstanceMemory(float instanceMemory) {
		this.instanceMemory = instanceMemory;
	}

	public float getComputeUnitPrice() {
		return computeUnitPrice;
	}

	public void setComputeUnitPrice(float computeUnitPrice) {
		this.computeUnitPrice = computeUnitPrice;
	}

	public float getComputeMonthlyPrice() {
		return computeMonthlyPrice;
	}

	public void setComputeMonthlyPrice(float computeMonthlyPrice) {
		this.computeMonthlyPrice = computeMonthlyPrice;
	}

	public float getComputeTotalPrice() {
		return computeTotalPrice;
	}

	public void setComputeTotalPrice(float computeTotalPrice) {
		this.computeTotalPrice = computeTotalPrice;
	}

	public float getStorageMonthlyPrice() {
		return storageMonthlyPrice;
	}

	public void setStorageMonthlyPrice(float storageMonthlyPrice) {
		this.storageMonthlyPrice = storageMonthlyPrice;
	}

	public float getSnapshotMonthlyPrice() {
		return snapshotMonthlyPrice;
	}

	public void setSnapshotMonthlyPrice(float snapshotMonthlyPrice) {
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

	public float getUpfrontFee() {
		return upfrontFee;
	}

	public void setUpfrontFee(float upfrontFee) {
		this.upfrontFee = upfrontFee;
	}
}
