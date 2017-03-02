package com.amazon.proposalcalculator.enums;

public enum S3StorageClass {
	
	General_Purpose("General Purpose");

    private String columnName;

	S3StorageClass(String columnName){
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }

}
