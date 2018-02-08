package com.learningbydoing.generic.lru;

/**
 * @author Maddipatla Chandra Babu
 * 
 * @date 07-Feb-2018
 */
public class LRUCache<K, V> {
	private Integer noOfPages;
	private Node<K, V> front;
	private Node<K, V> rear;
	private Integer noOfHits = 0;
	private Integer listSize = 0;

	/**
	 * @param noOfPages
	 */
	public LRUCache(Integer noOfPages) {
		this.noOfPages = noOfPages;
	}

	/**
	 * @param key
	 * @return
	 */
	public V getObject(K key) {
		V value;
		if (front != null && listSize < noOfPages) {
			Node<K, V> existingNode = getNodeExists(key);
			if (existingNode == null) {
				Node<K, V> node = getObjectFromDB(key, front);
				value = node.getValue();
				front.setPrev(node);
				front = node;
			} else {
				processExistingNode(existingNode);
				value = existingNode.getValue();
				noOfHits++;
			}
		} else if (front == null) {
			Node<K, V> node = getObjectFromDB(key, front);
			value = node.getValue();
			front = rear = node;
		} else {
			Node<K, V> existingNode = getNodeExists(key);
			if (existingNode == null)
				value = processNonExistNode(key).getValue();
			else {
				processExistingNode(existingNode);
				value = existingNode.getValue();
				noOfHits++;
			}
		}
		listSize++;
		return value;
	}

	/**
	 * @param key
	 * @return
	 */
	private Node<K, V> getNodeExists(K key) {
		Node<K, V> node = front;
		while (node != null) {
			if (node.getKey() == key)
				return node;
			node = node.getNext();
		}
		return null;
	}

	/**
	 * @param existingNode
	 */
	private void processExistingNode(Node<K, V> existingNode) {
		if (existingNode.getKey() == rear.getKey() && existingNode.getKey() != front.getKey()) {
			rear = rear.getPrev();
			rear.setNext(null);
			existingNode.setPrev(null);
			existingNode.setNext(front);
			front.setPrev(existingNode);
			front = existingNode;
		} else if (existingNode.getKey() != front.getKey()) {
			existingNode.getPrev().setNext(existingNode.getNext());
			existingNode.getNext().setPrev(existingNode.getPrev());
			existingNode.setPrev(null);
			existingNode.setNext(front);
			front.setPrev(existingNode);
			front = existingNode;
		}
	}

	/**
	 * @param key
	 * @return
	 */
	private Node<K, V> processNonExistNode(K key) {
		Node<K, V> node = getObjectFromDB(key, front);
		front.setPrev(node);
		front = node;
		rear = rear.getPrev();
		rear.setNext(null);
		return node;
	}

	/**
	 * @param key
	 * @param front
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Node<K, V> getObjectFromDB(K key, Node<K, V> front) {
		Employee employee = new Employee((Integer) key, "Employee-" + key, Double.valueOf(((Integer) key) * 5d));
		return new Node<>(null, front, (K) employee.getId(), (V) employee);
	}
}
