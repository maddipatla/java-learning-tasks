package com.learningbydoing.book;

import java.io.Serializable;

/**
 * @author Maddipatla Chandra Babu
 * 
 * @date 07-Feb-2018
 */
public class Fiction extends AbstractBook implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5606140994700892330L;

	/**
	 * @param id
	 * @param title
	 * @param price
	 */
	public Fiction(Long id, String title, Double price) {
		super(id, title, price);
	}

	/**
	 * 
	 * @return In order to override the method simply appending type of book to the
	 *         book title.
	 * 
	 */
	@Override
	public String getTitle() {
		return new StringBuilder().append(title).append(" - Fiction").toString();
	}

}
