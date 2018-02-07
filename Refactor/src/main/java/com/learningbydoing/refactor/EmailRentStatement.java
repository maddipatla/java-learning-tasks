package com.learningbydoing.refactor;

public class EmailRentStatement extends AbstractRentStatement {

	public EmailRentStatement(Customer customer) {
		super(customer);
	}

	@Override
	public String fetchStatement() {
		super.fetchStatement();
		return new StringBuilder().append("Email Statement: ").append(totalPrice).toString();
	}

}
