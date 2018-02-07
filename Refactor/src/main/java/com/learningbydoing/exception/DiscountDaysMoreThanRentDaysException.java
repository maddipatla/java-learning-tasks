package com.learningbydoing.exception;

/**
 * @author Maddipatla Chandra Babu
 * 
 * @date 07-Feb-2018
 */
public class DiscountDaysMoreThanRentDaysException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8944743267192934033L;

	/**
	 * @param message
	 */
	public DiscountDaysMoreThanRentDaysException(String message) {
		super(message);
	}
}
