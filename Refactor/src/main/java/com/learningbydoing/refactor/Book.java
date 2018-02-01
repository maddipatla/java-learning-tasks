package com.learningbydoing.refactor;

import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Book implements Serializable {

	static final Logger logger = LogManager.getLogger(Book.class.getName());

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
		if (daysRented > daysToDiscount) {
			logger.info("Number of days discounterd: {}", daysToDiscount);
			return (daysRented - daysToDiscount) * price;
		}
		logger.warn("Price calculation defaulted, Because daysRented({}) < daysToDiscount({})", daysRented,
				daysToDiscount);
		return daysRented * price;

	}
}
