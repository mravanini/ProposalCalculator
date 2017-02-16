package com.amazon.proposalcalculator.bean;

public class DataTransferInput {
	
	private String region;
	private long dataTransferOut;
	private long InterRegionDataTransfer;
	
	private long s3StandardSize;
	private long s3InfrequentAccessSize;
	private long s3Puts;
	private long s3Gets;
	
	private long elbAmmount;
	private long elbProcessedData;
	
	public long getDataTransferOut() {
		return dataTransferOut;
	}
	public void setDataTransferOut(long dataTransferOut) {
		this.dataTransferOut = dataTransferOut;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	@Override
	public String toString() {
		return "DataTransferInput [region=" + region + ", dataTransferOut=" + dataTransferOut + "]";
	}
	
	

}
