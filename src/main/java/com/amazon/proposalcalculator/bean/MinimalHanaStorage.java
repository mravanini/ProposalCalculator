package com.amazon.proposalcalculator.bean;

public class MinimalHanaStorage {
	
	public MinimalHanaStorage(String instanceSize, Integer dataAndLogsVolume, 
			Integer rootVolume, Integer sapBinariesVolume, Integer sharedVolume, 
			Integer backupVolume) {
		this.instanceSize = instanceSize;
		this.dataAndLogsVolume = dataAndLogsVolume;
		this.rootVolume = rootVolume;
		this.sapBinariesVolume = sapBinariesVolume;
		this.sharedVolume = sharedVolume;
		this.backupVolume = backupVolume;
	}
	
	private String  instanceSize;
	private Integer dataAndLogsVolume;
	private Integer rootVolume;
	private Integer sapBinariesVolume;
	private Integer sharedVolume;
	private Integer backupVolume;
	
	public String getInstanceSize() {
		return instanceSize;
	}
	public void setInstanceSize(String instanceSize) {
		this.instanceSize = instanceSize;
	}
	public Integer getDataAndLogsVolume() {
		return dataAndLogsVolume;
	}
	public void setDataAndLogsVolume(Integer dataAndLogsVolume) {
		this.dataAndLogsVolume = dataAndLogsVolume;
	}
	public Integer getRootVolume() {
		return rootVolume;
	}
	public void setRootVolume(Integer rootVolume) {
		this.rootVolume = rootVolume;
	}
	public Integer getSapBinariesVolume() {
		return sapBinariesVolume;
	}
	public void setSapBinariesVolume(Integer sapBinariesVolume) {
		this.sapBinariesVolume = sapBinariesVolume;
	}
	public Integer getSharedVolume() {
		return sharedVolume;
	}
	public void setSharedVolume(Integer sharedVolume) {
		this.sharedVolume = sharedVolume;
	}
	public Integer getBackupVolume() {
		return backupVolume;
	}
	public void setBackupVolume(Integer backupVolume) {
		this.backupVolume = backupVolume;
	}
	
}
