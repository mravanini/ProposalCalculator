package com.amazon.proposalcalculator.utils;

import java.util.Map;
import java.util.TreeMap;

public class SAPS {

	private Map<String, Integer> sapsMap = null;
	private static SAPS instance;

	private SAPS() {

	}

	public Integer getSAPS(String instanceType) {
		Object result = instance.sapsMap.get(instanceType.toLowerCase().trim());
		if (result != null)
			return (Integer) result;
		return 0;
	}

	public static SAPS getInstance() {
		if (instance == null) {
			instance = new SAPS();
			instance.sapsMap = new TreeMap<String, Integer>();

			instance.sapsMap.put(Constants.STAND_BY_INSTANCE_TYPE, 0);

			instance.sapsMap.put("m4.16xlarge", 75770);
			instance.sapsMap.put("m4.10xlarge", 47320);
			instance.sapsMap.put("m4.4xlarge", 18928);
			instance.sapsMap.put("m4.2xlarge", 9464);
			instance.sapsMap.put("m4.xlarge", 4732);
			instance.sapsMap.put("m4.large", 2366);

			instance.sapsMap.put("c4.8xlarge", 37950);
			instance.sapsMap.put("c4.4xlarge", 19030);
			instance.sapsMap.put("c4.2xlarge", 9515);
			instance.sapsMap.put("c4.xlarge", 4758);
			instance.sapsMap.put("c4.large", 2379);

			instance.sapsMap.put("m5.24xlarge", 135230);
			instance.sapsMap.put("m5.12xlarge", 67615);
			instance.sapsMap.put("m5.4xlarge", 22536);
			instance.sapsMap.put("m5.2xlarge", 11268);
			instance.sapsMap.put("m5.xlarge", 5634);
			instance.sapsMap.put("m5.large", 2817);

			instance.sapsMap.put("c5.18xlarge", 95400);
			instance.sapsMap.put("c5.9xlarge", 47700);
			instance.sapsMap.put("c5.4xlarge", 21200);
			instance.sapsMap.put("c5.2xlarge", 10600);
			instance.sapsMap.put("c5.xlarge", 5300);
			instance.sapsMap.put("c5.large", 2650);

			instance.sapsMap.put("c3.8xlarge", 31830);
			instance.sapsMap.put("c3.4xlarge", 15915);
			instance.sapsMap.put("c3.2xlarge", 7957);
			instance.sapsMap.put("c3.xlarge", 3978);
			instance.sapsMap.put("c3.large", 1989);

			instance.sapsMap.put("r4.16xlarge", 76400);
			instance.sapsMap.put("r4.8xlarge", 38200);
			instance.sapsMap.put("r4.4xlarge", 19100);
			instance.sapsMap.put("r4.2xlarge", 9550);
			instance.sapsMap.put("r4.xlarge", 4775);
			instance.sapsMap.put("r4.large", 2387);

			instance.sapsMap.put("r5.24xlarge", 135230);
			instance.sapsMap.put("r5.12xlarge", 67615);
			instance.sapsMap.put("r5.4xlarge", 22538);
			instance.sapsMap.put("r5.2xlarge", 11269);
			instance.sapsMap.put("r5.xlarge", 5535);
			instance.sapsMap.put("r5.large", 2817);

			instance.sapsMap.put("r3.8xlarge", 31920);
			instance.sapsMap.put("r3.4xlarge", 15960);
			instance.sapsMap.put("r3.2xlarge", 7980);
			instance.sapsMap.put("r3.xlarge", 3990);
			instance.sapsMap.put("r3.large", 1995);

			instance.sapsMap.put("x1.32xlarge", 131500);
			instance.sapsMap.put("x1.16xlarge", 65750);

			instance.sapsMap.put("x1e.32xlarge", 131500);
			instance.sapsMap.put("x1e.16xlarge", 65750);
			instance.sapsMap.put("x1e.8xlarge", 32875);
			instance.sapsMap.put("x1e.4xlarge", 16437);
			instance.sapsMap.put("x1e.2xlarge", 8219);
			instance.sapsMap.put("x1e.xlarge", 4109);

			instance.sapsMap.put("u-9tb1", 480600);
			instance.sapsMap.put("u-6tb1", 480600);
			instance.sapsMap.put("u-12tb1", 480600);

			// Previous generation instances
			instance.sapsMap.put("cc2.8xlarge", 30430); // 90330 as a database server for 3-tier config
			instance.sapsMap.put("cr1.8xlarge", 30430);
			instance.sapsMap.put("m2.4xlarge", 7400);
			instance.sapsMap.put("m2.2xlarge", 3700);
		}
		return instance;
	}

}
