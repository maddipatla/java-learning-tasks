package com.learningbydoing.rentstatement;

import com.learningbydoing.book.Customer;
import com.learningbydoing.exception.TypeRequiredException;
import com.learningbydoing.statement.Statement;

public class RentStatementFactory {

	private RentStatementFactory() {
		throw new IllegalStateException("Utililty class");
	}

	public static Statement getStatement(RentStatementConstants statementType, Customer customer) {
		switch (statementType) {
		case EMAIL:
			return new EmailRentStatement(customer);
		case PDF:
			return new PDFRentStatement(customer);
		default:
			break;
		}
		throw new TypeRequiredException("Rent Statement type is required");
	}
}
