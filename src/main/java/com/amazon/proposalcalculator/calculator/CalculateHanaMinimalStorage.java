package com.amazon.proposalcalculator.calculator;

import java.util.ArrayList;
import java.util.List;

import com.amazon.proposalcalculator.bean.MinimalHanaStorage;
import com.amazon.proposalcalculator.utils.Constants;

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
		
		storageList.add(new MinimalHanaStorage(Constants.STAND_BY_INSTANCE_TYPE, 3072, 50, 50, 1024, 4096));
		
		storageList.add(new MinimalHanaStorage("x1.32xlarge", 3072, 50, 50, 1024, 4096));
		storageList.add(new MinimalHanaStorage("x1.16xlarge", 2100, 50, 50, 1024, 2048));
		
		storageList.add(new MinimalHanaStorage("x1e.32xlarge", 6144, 50, 50, 1024, 8192));
		storageList.add(new MinimalHanaStorage("x1e.16xlarge", 3072, 50, 50, 1024, 4096));
		storageList.add(new MinimalHanaStorage("x1e.8xlarge", 2100, 50, 50, 1024, 2048));
		storageList.add(new MinimalHanaStorage("x1e.4xlarge", 1200, 50, 50, 512, 1024));
		storageList.add(new MinimalHanaStorage("x1e.2xlarge", 900, 50, 50, 300, 1024));
		
		storageList.add(new MinimalHanaStorage("r4.16xlarge", 1200, 50, 50, 512, 1024));
		storageList.add(new MinimalHanaStorage("r4.8xlarge", 900, 50, 50, 300, 1024));
		storageList.add(new MinimalHanaStorage("r4.4xlarge", 900, 50, 50, 300, 512));
		storageList.add(new MinimalHanaStorage("r4.2xlarge", 900, 50, 50, 300, 512));
		
		storageList.add(new MinimalHanaStorage("r3.8xlarge", 900, 50, 50, 300, 1024));
		storageList.add(new MinimalHanaStorage("r3.4xlarge", 900, 50, 50, 300, 512));
		storageList.add(new MinimalHanaStorage("r3.2xlarge", 900, 50, 50, 300, 512));
		
		storageList.add(new MinimalHanaStorage("m4.16xlarge", 900, 50, 50, 300, 1024));
		storageList.add(new MinimalHanaStorage("m4.10xlarge", 900, 50, 50, 300, 512));
		storageList.add(new MinimalHanaStorage("m4.4xlarge", 900, 50, 50, 300, 512));
		
		storageList.add(new MinimalHanaStorage("c4.8xlarge", 900, 50, 50, 300, 512));
		
		//C4
		//M4
	}
	
	public MinimalHanaStorage getMinimalHanaStorage(String instanceSize) {
		for (MinimalHanaStorage minimalHanaStorage : storageList) {
			if (minimalHanaStorage.getInstanceSize().equals(instanceSize)) {
				return minimalHanaStorage;
			}
		}
		return new MinimalHanaStorage(instanceSize, 900, 50, 50, 300, 512);
	}

}
