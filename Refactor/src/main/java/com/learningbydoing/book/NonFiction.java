package com.learningbydoing.book;

import java.io.Serializable;

/**
 * @author Maddipatla Chandra Babu
 * 
 * @date 07-Feb-2018
 */
public class NonFiction extends AbstractBook implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 621436336497884562L;

	/**
	 * @param id
	 * @param title
	 * @param price
	 */
	public NonFiction(Long id, String title, Double price) {
		super(id, title, price);
	}

	@Override
	public String getTitle() {
		return new StringBuilder().append(title).append(" - NonFiction").toString();
	}
}
