package com.learningbydoing.rentstatement;

import com.learningbydoing.book.Customer;

/**
 * @author Maddipatla Chandra Babu
 * @date 07-Feb-2018
 */
public class EmailRentStatement extends AbstractRentStatement {

    /**
     * @param customer
     */
    public EmailRentStatement(Customer customer) {
        super(customer);
    }

    /**
     * As I am not really implementing email service to send email, simply returning
     * String by appending text as Email Statement.
     */
    @Override
    public String fetchStatement() {
        super.fetchStatement();
        return new StringBuilder().append("Email Statement: ").append(totalPrice).toString();
    }

}
