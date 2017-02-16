package com.amazon.proposalcalculator.bean;

import com.ebay.xcelite.annotations.Column;

public class ConfigInput {
			
	@Column(name = "CPU Tolerance")
	private float cpuTolerance;
	
	@Column(name = "Memory Tolerance")
	private float memoryTolerance;

	@Column(name = "Match")
	private String match;

	public float getCpuTolerance() {
		return cpuTolerance;
	}

	public void setCpuTolerance(float cpuTolerance) {
		this.cpuTolerance = cpuTolerance;
	}

	public float getMemoryTolerance() {
		return memoryTolerance;
	}

	public void setMemoryTolerance(float memoryTolerance) {
		this.memoryTolerance = memoryTolerance;
	}

	public String getMatch() {
		return match;
	}

	public void setMatch(String match) {
		this.match = match;
	}

}
