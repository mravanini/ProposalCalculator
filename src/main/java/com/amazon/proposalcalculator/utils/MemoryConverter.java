package com.amazon.proposalcalculator.utils;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class MemoryConverter extends AbstractBeanField<Float> {

	@Override
	protected Object convert(String value)
			throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, CsvConstraintViolationException {
		value = value.replaceAll("[^\\d.]", "");
		if (value.length() == 0)
			value = "0";
		return Float.valueOf(value);
	}

}
