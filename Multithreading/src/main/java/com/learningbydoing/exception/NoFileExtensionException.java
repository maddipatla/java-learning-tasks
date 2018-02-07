package com.learningbydoing.exception;

/**
 * @author Maddipatla Chandra Babu
 * 
 * @date 07-Feb-2018
 */
public class NoFileExtensionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1256732124932684880L;

	/**
	 * @param message
	 */
	public NoFileExtensionException(String message) {
		super(message);
	}
}
