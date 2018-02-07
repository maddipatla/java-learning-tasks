package com.learningbydoing.exception;

/**
 * @author Maddipatla Chandra Babu
 * 
 * @date 07-Feb-2018
 */
public class TypeRequiredException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5738550757521119739L;

	/**
	 * @param message
	 */
	public TypeRequiredException(String message) {
		super(message);
	}
}
