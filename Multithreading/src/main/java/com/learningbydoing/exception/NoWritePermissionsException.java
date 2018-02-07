/**
 * 
 */
package com.learningbydoing.exception;

/**
 * @author Maddipatla Chandra Babu
 * 
 * @date 07-Feb-2018
 */
public class NoWritePermissionsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4283054496519085183L;

	/**
	 * 
	 */
	public NoWritePermissionsException(String message) {
		super(message);
	}
}
