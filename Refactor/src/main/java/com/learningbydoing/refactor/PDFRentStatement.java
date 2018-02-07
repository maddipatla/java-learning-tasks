package com.learningbydoing.refactor;

public class PDFRentStatement extends AbstractRentStatement {

	public PDFRentStatement(Customer customer) {
		super(customer);
	}

	@Override
	public String fetchStatement() {
		super.fetchStatement();
		return new StringBuilder().append("PDF Statement: ").append(totalPrice).toString();
	}

}
