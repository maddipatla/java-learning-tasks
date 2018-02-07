package com.learningbydoing.rentstatement;

import java.util.Iterator;

import com.learningbydoing.book.Customer;
import com.learningbydoing.rental.Rental;
import com.learningbydoing.statement.Statement;

/**
 * @author Maddipatla Chandra Babu
 * 
 * @date 07-Feb-2018
 */
public class AbstractRentStatement implements Statement {
	private Customer customer;
	protected double totalPrice;

	/**
	 * @param customer
	 */
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
