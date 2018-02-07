package com.learningbydoing.exception;

public class DiscountDaysMoreThanRentDaysException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8944743267192934033L;

	public DiscountDaysMoreThanRentDaysException(String message) {
		super(message);
	}
}
