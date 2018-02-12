package com.learningbydoing.generics;

/**
 * @author Maddipatla Chandra Babu
 * @date 07-Feb-2018
 *
 */
public enum Month implements GenericEnum {

	JANUARY("January"),
	FEBRUARY("February"),
	MARCH("March"),
	APRIL("April"),
	MAY("May"),
	JUNE("June"),
	JULY("July"),
	AUGUST("August"),
	SEPTEMBER("September"),
	OCTOBER("October"),
	NOVEMBER("November"),
	DECEMBER("December");

	private String value;

	private Month(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}