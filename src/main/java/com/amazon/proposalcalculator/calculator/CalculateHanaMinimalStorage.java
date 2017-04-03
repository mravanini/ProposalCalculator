package com.amazon.proposalcalculator.calculator;

import java.util.ArrayList;
import java.util.List;

import com.amazon.proposalcalculator.bean.MinimalHanaStorage;

public class CalculateHanaMinimalStorage {
	
	private static CalculateHanaMinimalStorage instance;
	
	private CalculateHanaMinimalStorage() {
		
	}
	
	public static CalculateHanaMinimalStorage getInstance() {
		if (instance == null)
			instance = new CalculateHanaMinimalStorage();
		return instance;
	}
	
	private static List<MinimalHanaStorage> storageList = new ArrayList<MinimalHanaStorage>();
	
	static {
		storageList.add(new MinimalHanaStorage("x1.32xlarge", 3072, 50, 50, 1024, 4096));
		storageList.add(new MinimalHanaStorage("x1.16xlarge", 2100, 50, 50, 1024, 2048));
		
		storageList.add(new MinimalHanaStorage("r4.16xlarge", 1200, 50, 50, 512, 1024));
		storageList.add(new MinimalHanaStorage("r4.8xlarge", 900, 50, 50, 300, 1024));
		storageList.add(new MinimalHanaStorage("r4.4xlarge", 900, 50, 50, 300, 512));
		storageList.add(new MinimalHanaStorage("r4.2xlarge", 900, 50, 50, 300, 512));
		
		storageList.add(new MinimalHanaStorage("r3.8xlarge", 900, 50, 50, 300, 1024));
		storageList.add(new MinimalHanaStorage("r3.4xlarge", 900, 50, 50, 300, 512));
		storageList.add(new MinimalHanaStorage("r3.2xlarge", 900, 50, 50, 300, 512));
		
		//C4
		//M4
	}
	
	public MinimalHanaStorage getMinimalHanaStorage(String instanceSize) {
		for (MinimalHanaStorage minimalHanaStorage : storageList) {
			if (minimalHanaStorage.getInstanceSize().equals(instanceSize)) {
				return minimalHanaStorage;
			}
		}
		return null;
	}

}
