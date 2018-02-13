package com.learningbydoing.lru.practice;

/**
 * @author Maddipatla Chandra Babu
 * @date 07-Feb-2018
 */
public class LRUPractice {
	private Integer noOfPages;
	private Node rear;
	private Node front;
	private Integer noOfHits = 0;
	private Integer listSize = 0;

	/**
	 * @param noOfPages
	 */
	public LRUPractice(Integer noOfPages) {
		this.noOfPages = noOfPages;
	}

	/**
	 * @param data
	 * @return Node
	 */
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

	/**
	 * @param data
	 */
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

	/**
	 * @param existingNode
	 */
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

	/**
	 * @param data
	 */
	public void processNonExistNode(Integer data) {
		Node node = new Node(rear, null, data);
		rear.setNext(node);
		rear = node;
		front = front.getNext();
		front.setPrev(null);
	}
}
