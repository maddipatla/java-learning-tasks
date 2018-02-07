package com.learningbydoing.book;

import java.io.Serializable;

public class NonFiction extends AbstractBook implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 621436336497884562L;

	public NonFiction(Long id, String title, Double price) {
		super(id, title, price);
	}

	@Override
	public String getTitle() {
		return new StringBuilder().append(title).append(" - NonFiction").toString();
	}
}
