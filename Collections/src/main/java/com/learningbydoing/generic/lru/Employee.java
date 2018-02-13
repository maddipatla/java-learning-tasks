package com.learningbydoing.generic.lru;

/**
 * @author Maddipatla Chandra Babu
 * @date 07-Feb-2018
 */
public class Employee {
	private Integer id;
	private String name;
	private double salary;

	/**
	 * @param id
	 * @param name
	 * @param salary
	 */
	public Employee(Integer id, String name, Double salary) {
		this.id = id;
		this.name = name;
		this.salary = salary;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	public double getSalary() {
		return salary;
	}

	/**
	 * @param salary
	 */
	public void setSalary(double salary) {
		this.salary = salary;
	}
}
