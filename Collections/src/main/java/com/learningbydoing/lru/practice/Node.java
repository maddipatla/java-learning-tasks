package com.learningbydoing.lru.practice;

public class Node {
	private Node next;
	private Node prev;
	private Integer data;

	public Node(Node prev, Node next, Integer data) {
		this.prev = prev;
		this.next = next;
		this.data = data;
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

	public Integer getData() {
		return data;
	}

	public void setData(Integer data) {
		this.data = data;
	}

}
