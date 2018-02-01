package com.learningbydoing.refactor;

public class Fiction extends Book {

	private static final long serialVersionUID = 4970924989123255579L;

	public Fiction(Long id, String title, Double price) {
		super(id, title, price);
	}

	public String getTitle() {
		return title + " - Fiction";
	}

}
