package com.learningbydoing.refactor;

import java.io.Serializable;

public class Rental implements Serializable {

	private static final long serialVersionUID = -2191332894231658614L;

	private Integer daysRented;

	private Integer daysToDiscount;

	private Book book;

	public Rental(Integer daysRented, Integer daysToDiscount, Book book) {
		super();
		this.daysRented = daysRented;
		this.daysToDiscount = daysToDiscount;
		this.book = book;
	}

	public Integer getDaysRented() {
		return daysRented;
	}

	public Integer getDaysToDiscount() {
		return daysToDiscount;
	}

	public Book getBook() {
		return book;
	}

}
