package com.learningbydoing.refactor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BookFactory {
	static final Logger logger = LogManager.getLogger(BookFactory.class.getName());

	private BookFactory() {
		throw new IllegalStateException("Utility class");
	}

	public static Book getBook(BookConstants bookType, Long bookId, String bookTitle, Double bookPrice) {
		switch (bookType) {
		case FICTION:
			return new Fiction(bookId, bookTitle, bookPrice);
		case NONFICTION:
			return new NonFiction(bookId, bookTitle, bookPrice);
		case CHILDREN:
			return new Children(bookId, bookTitle, bookPrice);
		default:
			break;
		}
		return null;
	}
}
