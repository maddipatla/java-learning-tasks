package com.learningbydoing.lru.practice;

public class LRUPractice {
	private Integer noOfPages;
	private Node rear;
	private Node front;
	private static Integer noOfHits = 0;
	private Integer listSize = 0;

	public LRUPractice(Integer noOfPages) {
		this.noOfPages = noOfPages;
	}

	public Node nodeExists(Integer data) {
		Node node = front;
		while (node != null) {
			if (node.getData() == data) {
				noOfHits++;
				return node;
			}
			node = node.getNext();
		}
		return null;
	}

	public void add(Integer data) {
		if (rear != null && listSize < noOfPages) {
			Node existingNode = nodeExists(data);
			if (existingNode == null) {
				Node node = new Node(rear, null, data);
				rear.setNext(node);
				rear = node;
			} else
				processExistingNode(existingNode);
		} else if (front == null) {
			Node node = new Node(null, null, data);
			front = node;
			rear = node;
		} else {
			Node existingNode = nodeExists(data);
			if (existingNode == null)
				processNonExistNode(data);
			else
				processExistingNode(existingNode);
		}
		listSize++;
	}

	public void processExistingNode(Node existingNode) {
		if (existingNode != null && existingNode != rear) {
			if (existingNode == front) {
				front = front.getNext();
				front.setPrev(null);
				rear.setNext(existingNode);
				existingNode.setPrev(rear);
				existingNode.setNext(null);
				rear = existingNode;
			} else {
				existingNode.getPrev().setNext(existingNode.getNext());
				existingNode.getNext().setPrev(existingNode.getPrev());
				existingNode.setPrev(rear);
				existingNode.setNext(null);
			}
		}
	}

	public void processNonExistNode(Integer data) {
		Node node = new Node(rear, null, data);
		rear.setNext(node);
		rear = node;
		front = front.getNext();
		front.setPrev(null);
	}
}
