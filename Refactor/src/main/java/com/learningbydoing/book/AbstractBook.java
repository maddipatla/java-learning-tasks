package com.learningbydoing.book;

import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.learningbydoing.exception.DiscountDaysMoreThanRentDaysException;

/**
 * @author Maddipatla Chandra Babu
 * 
 * @date 07-Feb-2018
 */
public abstract class AbstractBook implements Book, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8520098057851948095L;

	static final Logger logger = LogManager.getLogger(AbstractBook.class.getName());

	/**
	 * @param id
	 * @param title
	 * @param price
	 */
	public AbstractBook(Long id, String title, Double price) {
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
	 *            - Number of days rented.
	 * @param daysToDiscount
	 *            - Number of days to discount. If number of days to discount are
	 *            more than rented days then throwing an exception.
	 */
	public Double getPrice(Integer daysRented, Integer daysToDiscount) {
		if (daysRented < daysToDiscount)
			throw new DiscountDaysMoreThanRentDaysException("Discount days are more than rent days");
		logger.info("Number of days discounterd: {}", daysToDiscount);
		return (daysRented - daysToDiscount) * price;

	}
}
