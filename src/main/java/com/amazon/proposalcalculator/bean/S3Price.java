package com.amazon.proposalcalculator.bean;

import com.opencsv.bean.CsvBindByName;

public class S3Price {
	
	@CsvBindByName(column="Product Family")
	private String productFamily;
	
	//AmazonS3
	@CsvBindByName(column="serviceCode")
	private String serviceCode;
	
	//US East (N. Virginia)
	@CsvBindByName(column="Location")
	private String location;
	
	//General Purpose
	@CsvBindByName(column="Storage Class")
	private String storageClass;
	
	@CsvBindByName(column="StartingRange")
	private String startingRange;
	
	@CsvBindByName(column="EndingRange")
	private String endingRange;
	
	@CsvBindByName(column="PricePerUnit")
	private Double pricePerUnit;

	public String getProductFamily() {
		return productFamily;
	}

	public void setProductFamily(String productFamily) {
		this.productFamily = productFamily;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStorageClass() {
		return storageClass;
	}

	public void setStorageClass(String storageClass) {
		this.storageClass = storageClass;
	}

	public String getStartingRange() {
		return startingRange;
	}

	public void setStartingRange(String startingRange) {
		this.startingRange = startingRange;
	}

	public String getEndingRange() {
		return endingRange;
	}

	public void setEndingRange(String endingRange) {
		this.endingRange = endingRange;
	}

	public Double getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(Double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}
	
	
	
}
