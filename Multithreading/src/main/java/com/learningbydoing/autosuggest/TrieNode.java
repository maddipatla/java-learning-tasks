package com.learningbydoing.autosuggest;

import java.util.LinkedList;
import java.util.List;

public class TrieNode {

	public TrieNode(char charecter) {
		this.character = charecter;
		this.childs = new LinkedList<>();
	}

	private char character;
	private List<TrieNode> childs;

	public char getCharacter() {
		return character;
	}

	public void setCharacter(char character) {
		this.character = character;
	}

	public List<TrieNode> getChilds() {
		return childs;
	}

	public void setChilds(List<TrieNode> childs) {
		this.childs = childs;
	}

}