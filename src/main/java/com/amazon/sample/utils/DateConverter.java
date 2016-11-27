package com.amazon.sample.utils;

import java.util.Date;

import com.ebay.xcelite.converters.ColumnValueConverter;

public class DateConverter implements ColumnValueConverter<Date, Date> {

	@Override
	public Date serialize(Date value) {
		// TODO Auto-generated method stub
		return new Date();
	}

	@Override
	public Date deserialize(Date value) {
		// TODO Auto-generated method stub
		return new Date();
	}

}
