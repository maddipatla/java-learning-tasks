package com.learningbydoing.rentstatement;

import com.learningbydoing.book.Customer;

/**
 * @author Maddipatla Chandra Babu
 * 
 * @date 07-Feb-2018
 */
public class PDFRentStatement extends AbstractRentStatement {

	/**
	 * @param customer
	 */
	public PDFRentStatement(Customer customer) {
		super(customer);
	}

	@Override
	public String fetchStatement() {
		super.fetchStatement();
		return new StringBuilder().append("PDF Statement: ").append(totalPrice).toString();
	}

}
