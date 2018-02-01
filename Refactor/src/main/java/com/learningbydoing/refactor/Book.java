package com.learningbydoing.refactor;

import java.io.Serializable;

public abstract class Book implements Serializable {

	private static final long serialVersionUID = 4643891220394093788L;

	public Book(Long id, String title, Double price) {
		this.id = id;
		this.title = title;
		this.price = price;
	}

	protected String title;

	protected Long id;

	protected Double price;

	public String getTitle() {
		return title;
	}

	public Long getId() {
		return id;
	}

	/**
	 * 
	 * @param daysRented
	 *            - Number of days to be rented
	 * @param daysToDiscount
	 *            - Number of days to be discounted. if daysRented > daysToDiscount
	 *            otherwise price calculation will get defaulted.
	 * @return
	 */
	public Double getPrice(Integer daysRented, Integer daysToDiscount) {
		if (daysRented > daysToDiscount)
			return (daysRented - daysToDiscount) * price;
		return daysRented * price;

	}
}
