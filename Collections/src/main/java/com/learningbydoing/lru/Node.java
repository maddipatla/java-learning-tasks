package com.learningbydoing.lru;

public class Node {
	private Node next;
	private Node prev;
	private Employee employee;

	public Node(Node prev, Node next, Employee employee) {
		this.prev = prev;
		this.next = next;
		this.employee = employee;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public Node getPrev() {
		return prev;
	}

	public void setPrev(Node prev) {
		this.prev = prev;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}
