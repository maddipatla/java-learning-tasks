package com.learningbydoing.lru;

/**
 * @author Maddipatla Chandra Babu
 * 
 * @date 07-Feb-2018
 */
public class Node {
	private Node next;
	private Node prev;
	private Employee employee;

	/**
	 * @param prev
	 * @param next
	 * @param employee
	 */
	public Node(Node prev, Node next, Employee employee) {
		this.prev = prev;
		this.next = next;
		this.employee = employee;
	}

	public Node getNext() {
		return next;
	}

	/**
	 * @param next
	 */
	public void setNext(Node next) {
		this.next = next;
	}

	public Node getPrev() {
		return prev;
	}

	/**
	 * @param prev
	 */
	public void setPrev(Node prev) {
		this.prev = prev;
	}

	public Employee getEmployee() {
		return employee;
	}

	/**
	 * @param employee
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}
