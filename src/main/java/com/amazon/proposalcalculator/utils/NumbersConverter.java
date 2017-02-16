package com.amazon.proposalcalculator.utils;

import com.ebay.xcelite.converters.ColumnValueConverter;

public class NumbersConverter implements ColumnValueConverter<String, Integer> {

	@Override
	public String serialize(Integer value) {
		if (value == null) {
			return "0";
		}
		return value.toString();
	}

	@Override
	public Integer deserialize(String value) {
		if (value == null) {
			return 0;
		}
		return Integer.valueOf(value);
	}

}
