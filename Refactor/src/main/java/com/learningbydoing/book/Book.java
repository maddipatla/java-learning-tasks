package com.learningbydoing.book;

import java.io.Serializable;

/**
 * @author Maddipatla Chandra Babu
 * 
 * @date 07-Feb-2018
 */
public interface Book extends Serializable {
	Long getId();

	String getTitle();

	Double getPrice(Integer daysRented, Integer daysToDiscount);
}
