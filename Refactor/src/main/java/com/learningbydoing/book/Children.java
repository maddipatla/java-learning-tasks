package com.learningbydoing.book;

import java.io.Serializable;

/**
 * @author Maddipatla Chandra Babu
 * 
 * @date 07-Feb-2018
 */
public class Children extends AbstractBook implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1584671888930324683L;

	/**
	 * @param id
	 * @param title
	 * @param price
	 */
	public Children(Long id, String title, Double price) {
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
		return new StringBuilder().append(title).append(" - For Children").toString();
	}

}
