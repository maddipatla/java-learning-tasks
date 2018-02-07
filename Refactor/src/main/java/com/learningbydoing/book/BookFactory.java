package com.learningbydoing.book;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.learningbydoing.exception.TypeRequiredException;

/**
 * @author Maddipatla Chandra Babu
 * 
 * @date 07-Feb-2018
 */
public final class BookFactory {
	static final Logger logger = LogManager.getLogger(BookFactory.class.getName());

	private BookFactory() {
		throw new IllegalStateException("Factory class, can't be instantiated");
	}

	/**
	 * @param bookType
	 * @param bookId
	 * @param bookTitle
	 * @param bookPrice
	 * @return
	 */
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
		throw new TypeRequiredException("BookType is required");
	}
}
