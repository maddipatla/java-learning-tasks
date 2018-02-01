package com.learningbydoing.refactor;

public class NonFiction extends Book {

	private static final long serialVersionUID = 1L;

	public NonFiction(Long id, String title, Double price) {
		super(id, title, price);
	}

	public String getTitle() {
		return title + " - NonFiction";
	}

	public Long getId() {
		return id;
	}
}
