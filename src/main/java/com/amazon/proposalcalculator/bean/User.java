package com.amazon.proposalcalculator.bean;

import java.util.Date;

import com.ebay.xcelite.annotations.Column;

public class User {

	@Column(name = "Firstname")
	private String firstName;

	@Column(name = "Lastname")
	private String lastName;

	@Column
	private long id;

	@Column(dataFormat = "ddd mmm dd hh:mm:ss yyy")
	private Date birthDate;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
}
