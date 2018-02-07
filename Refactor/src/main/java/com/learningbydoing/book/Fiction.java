package com.learningbydoing.book;

import java.io.Serializable;

public class Fiction extends AbstractBook implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5606140994700892330L;

	public Fiction(Long id, String title, Double price) {
		super(id, title, price);
	}

	@Override
	public String getTitle() {
		return new StringBuilder().append(title).append(" - Fiction").toString();
	}

}
