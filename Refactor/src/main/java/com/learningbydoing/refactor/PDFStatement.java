package com.learningbydoing.refactor;

import java.util.Iterator;

public class PDFStatement implements Statement {

	private Customer customer;

	public PDFStatement(Customer customer) {
		this.customer = customer;
	}

	public String fetchStatement() {
		Iterator<Rental> iterator = customer.getRentals().iterator();
		double price = 0.0;
		while (iterator.hasNext()) {
			Rental rental = iterator.next();
			price += rental.getBook().getPrice(rental.getDaysRented(), rental.getDaysToDiscount());
		}

		return String.valueOf("PDF Statement: " + price);
	}

}
