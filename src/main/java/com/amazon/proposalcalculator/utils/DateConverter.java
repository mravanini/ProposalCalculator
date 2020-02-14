package com.amazon.proposalcalculator.utils;

import java.util.Date;

import com.ebay.xcelite.converters.ColumnValueConverter;

public class DateConverter implements ColumnValueConverter<Date, Date> {

	@Override
	public Date serialize(Date value) {
		return new Date();
	}

	@Override
	public Date deserialize(Date value) {
		return new Date();
	}

}
