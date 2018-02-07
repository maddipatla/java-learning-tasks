package com.learningbydoing.exception;

/**
 * @author Maddipatla Chandra Babu
 * 
 * @date 07-Feb-2018
 */
public class NoPathExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1256732124932684880L;

	/**
	 * @param message
	 */
	public NoPathExistException(String message) {
		super(message);
	}
}
