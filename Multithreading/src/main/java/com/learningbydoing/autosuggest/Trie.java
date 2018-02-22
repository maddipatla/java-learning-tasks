/**
 * 
 */
package com.learningbydoing.autosuggest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Maddipatla Chandra Babu
 * @date 22-Feb-2018
 */
public class Trie {

	private TrieNode root;

	public void addWord(String word) {
		if (root == null) {
			root = new TrieNode(' ');
		}
		TrieNode start = root;
		char[] charecters = word.toCharArray();
		for (char c : charecters) {
			if (start.getChilds().isEmpty()) {
				TrieNode newNode = new TrieNode(c);
				start.getChilds().add(newNode);
				start = newNode;
			} else {
				ListIterator<TrieNode> iterator = start.getChilds().listIterator();
				TrieNode node = null;
				while (iterator.hasNext()) {
					node = iterator.next();
					if (node.getCharacter() >= c)
						break;
				}
				if (node.getCharacter() == c) {
					start = node;
				} else {
					TrieNode newNode = new TrieNode(c);
					iterator.add(newNode);
					start = newNode;
				}
			}
		}

	}

	public List<String> search(String prefix) {
		if (prefix.isEmpty()) {
			return new ArrayList<>(0);
		}

		char[] chars = prefix.toCharArray();
		TrieNode start = root;
		boolean flag = false;
		for (char c : chars) {
			if (!start.getChilds().isEmpty()) {
				for (TrieNode node : start.getChilds()) {
					if (node.getCharacter() == c) {
						start = node;
						flag = true;
						break;
					}
				}
			} else {
				flag = false;
				break;
			}
		}
		if (flag) {
			return getAllWords(start, prefix);
		}

		return new ArrayList<>(0);
	}

	private List<String> getAllWords(TrieNode start, final String prefix) {
		if (start.getChilds().isEmpty()) {
			List<String> list = new LinkedList<>();
			list.add(prefix);
			return list;
		} else {
			List<String> list = new LinkedList<>();
			for (TrieNode n : start.getChilds()) {
				list.addAll(getAllWords(n, prefix + n.getCharacter()));
			}
			return list;
		}

	}

}