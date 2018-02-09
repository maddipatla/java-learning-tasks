package com.learningbydoing.generics;

/**
 * @author Maddipatla Chandra Babu
 * 
 * @date 07-Feb-2018
 */
public enum Month {
	JANUARY("January"), FEBRUARY("February"), MARCH("March"), APRIL("April"), MAY("May"), JUNE("June"), JULY(
			"July"), AUGUST(
					"August"), SEPTEMBER("September"), OCTOBER("October"), NOVEMBER("November"), DECEMBER("December");
	private String value;

	private Month(String value) {
		this.value = value;
	}

	public static Month getInstanceByValue(String value) {
		for (Month month : values()) {
			if (month.value == value)
				return month;
		}
		return null;
	}

	public String getValue() {
		return value;
	}

}