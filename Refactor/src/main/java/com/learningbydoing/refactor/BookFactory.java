package com.learningbydoing.refactor;

public class BookFactory {
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
