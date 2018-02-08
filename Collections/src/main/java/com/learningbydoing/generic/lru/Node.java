package com.learningbydoing.generic.lru;

/**
 * @author Maddipatla Chandra Babu
 * 
 * @date 07-Feb-2018
 */
public class Node<K, V> {
	private Node<K, V> next;
	private Node<K, V> prev;
	K key;
	V value;

	/**
	 * @param prev
	 * @param next
	 * @param key
	 * @param value
	 */
	public Node(Node<K, V> prev, Node<K, V> next, K key, V value) {
		this.prev = prev;
		this.next = next;
		this.key = key;
		this.value = value;
	}

	public Node<K, V> getNext() {
		return next;
	}

	public void setNext(Node<K, V> next) {
		this.next = next;
	}

	public Node<K, V> getPrev() {
		return prev;
	}

	public void setPrev(Node<K, V> prev) {
		this.prev = prev;
	}

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

}
