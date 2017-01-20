package com.amazon.proposalcalculator.bean;

import com.ebay.xcelite.annotations.Column;
import com.ebay.xcelite.annotations.Row;

//@Row(colsOrder = {"Description", "Instance Type", "Instance vCPU", "Instance Memory", "Unit Price", "Monthly Price"})
@Row(colsOrder = {"Description", "Region", "Instances", "CPU", "Memory", "Storage(GB)", "Snapshot(GB)","Usage", "Term Type", "Lease Contract Length", "Purchase Option", 	"Offering Class", "Tenancy", "Operating System", "Beginning", "End", "Instance Type", "Instance vCPU", "Instance Memory", "Compute Unit Price", "Compute Monthly Price", "Compute Total Price"})

public class DefaultOutput extends DefaultInput {
	
	public DefaultOutput() {
		
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

	@Column(name = "Instance Type")
	private String instanceType;

	@Column(name = "Instance vCPU")
	private int instanceVCPU;

	@Column(name = "Instance Memory")
	private float instanceMemory;

	@Column(name = "Compute Unit Price")
	private float computeUnitPrice;
	
	@Column(name = "Compute Monthly Price")
	private float computeMonthlyPrice;
	
	@Column(name = "Compute Total Price")
	private float computeTotalPrice;
	
}
