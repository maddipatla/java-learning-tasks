package com.learningbydoing.refactor;

public class Children extends Book {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5720809525046839792L;

	public Children(Long id, String title, Double price) {
		super(id, title, price);
	}

	@Override
	public String getTitle() {
		return new StringBuilder().append(title).append(" - Children").toString();
	}

}
