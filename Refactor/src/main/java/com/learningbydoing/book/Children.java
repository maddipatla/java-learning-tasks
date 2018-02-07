package com.learningbydoing.book;

import java.io.Serializable;

public class Children extends AbstractBook implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1584671888930324683L;

	public Children(Long id, String title, Double price) {
		super(id, title, price);
	}

	@Override
	public String getTitle() {
		return new StringBuilder().append(title).append(" - For Children").toString();
	}

}
