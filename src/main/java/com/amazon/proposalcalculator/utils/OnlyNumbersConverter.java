package com.amazon.proposalcalculator.utils;

import com.ebay.xcelite.converters.ColumnValueConverter;

public class OnlyNumbersConverter implements ColumnValueConverter<String, Double> {

	@Override
	public String serialize(Double value) {
		String result = value.toString().replaceAll("[^\\d.]", "");
		if (result.length() == 0)
			result = "0";
		return result;
	}

	@Override
	public Double deserialize(String value) {
		return null;
	}

}
