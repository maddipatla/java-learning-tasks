package com.learningbydoing.generics;

/**
 * @author Maddipatla Chandra Babu
 * 
 * @date 07-Feb-2018
 */
public enum Week {
	MONDAY(new Byte("1")), TUESDAY(new Byte("2")), WEDNESDAY(new Byte("3")), THURSDAY(new Byte("4")), FRIDAY(
			new Byte("5")), SATURDAY(new Byte("6")), SUNDAY(new Byte("7"));

	private Byte value;

	/**
	 * 
	 */
	private Week(Byte value) {
		this.value = value;
	}

	public Week getInstanceByValue(Byte value) {
		for (Week week : values()) {
			if (week.value == value)
				return week;
		}
		return null;
	}
}
