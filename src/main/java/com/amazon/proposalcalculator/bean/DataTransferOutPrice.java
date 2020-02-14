package com.amazon.proposalcalculator.bean;

import com.opencsv.bean.CsvBindByName;

public class DataTransferOutPrice {

	@CsvBindByName(column = "Product Family")
	private String productFamily;
	// Data Transfer

	@CsvBindByName(column = "From Location")
	private String fromLocation;
	// South America (Sao Paulo)

	@CsvBindByName(column = "To Location")
	private String toLocation;
	// External

	@CsvBindByName(column = "Transfer Type")
	private String transferType;
	// AWS Outbound

	@CsvBindByName(column = "StartingRange")
	private String startingRange;

	@CsvBindByName(column = "EndingRange")
	private String endingRange;

	@CsvBindByName(column = "PricePerUnit")
	private Double pricePerUnit;

	public String getFromLocation() {
		return fromLocation;
	}

	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}

	public String getToLocation() {
		return toLocation;
	}

	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}

	public String getTransferType() {
		return transferType;
	}

	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}

	public String getProductFamily() {
		return productFamily;
	}

	public void setProductFamily(String productFamily) {
		this.productFamily = productFamily;
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
