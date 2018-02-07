package com.learningbydoing.rentstatement;

import java.util.Iterator;

import com.learningbydoing.book.Customer;
import com.learningbydoing.rental.Rental;
import com.learningbydoing.statement.Statement;

public class AbstractRentStatement implements Statement {
	private Customer customer;
	protected double totalPrice;

	public AbstractRentStatement(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String fetchStatement() {
		Iterator<Rental> iterator = customer.getRentals().iterator();
		double price = 0.0;
		while (iterator.hasNext()) {
			Rental rental = iterator.next();
			price += rental.getBook().getPrice(rental.getDaysRented(), rental.getDaysToDiscount());
		}
		totalPrice = price;
		return "";
	}

}
