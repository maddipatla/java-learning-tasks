package com.learningbydoing.rentstatement;

import com.learningbydoing.book.Customer;
import com.learningbydoing.rental.Rental;
import com.learningbydoing.statement.Statement;

import java.util.Iterator;

/**
 * @author Maddipatla Chandra Babu
 * @date 07-Feb-2018
 */
public class AbstractRentStatement implements Statement {
    protected double totalPrice;
    private Customer customer;

    /**
     * @param customer
     */
    public AbstractRentStatement(Customer customer) {
        this.customer = customer;
    }

    /**
     * To calculate Price get rentals from the Customer, iterate over rentals, get
     * book from each rental and get price from the book, sum it up all the books
     * price.
     *
     * @return Price of all the books rented for the Customer.
     */
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
