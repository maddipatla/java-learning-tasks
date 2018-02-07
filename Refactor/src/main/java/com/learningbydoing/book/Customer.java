package com.learningbydoing.book;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.learningbydoing.rental.Rental;

/**
 * @author Maddipatla Chandra Babu
 * 
 * @date 07-Feb-2018
 */
public class Customer implements Serializable {

	private static final long serialVersionUID = 6360800785653146523L;

	private Integer id;

	private String name;

	private List<Rental> rentals = new ArrayList<>();

	/**
	 * @param id
	 * @param name
	 * @param rentals
	 */
	public Customer(Integer id, String name, List<Rental> rentals) {
		super();
		this.id = id;
		this.name = name;
		this.rentals = rentals;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	/**
	 * @param rental
	 */
	public void addRental(Rental rental) {
		this.rentals.add(rental);
	}

	public List<Rental> getRentals() {
		return rentals;
	}

}
