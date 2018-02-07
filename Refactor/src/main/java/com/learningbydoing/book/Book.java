package com.learningbydoing.book;

import java.io.Serializable;

public interface Book extends Serializable {
	Long getId();

	String getTitle();

	Double getPrice(Integer daysRented, Integer daysToDiscount);
}
