package com.learningbydoing.rental;

import java.io.Serializable;

import com.learningbydoing.book.Book;

/**
 * @author Maddipatla Chandra Babu
 * 
 * @date 07-Feb-2018
 */
public class Rental implements Serializable {

	private static final long serialVersionUID = -2191332894231658614L;

	private Integer daysRented;

	private Integer daysToDiscount;

	private Book book;

	/**
	 * @param daysRented
	 * @param daysToDiscount
	 *            - Which is at rental level(per book), because each book has
	 *            different priority for the renter to give discount.
	 * @param book
	 */
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
