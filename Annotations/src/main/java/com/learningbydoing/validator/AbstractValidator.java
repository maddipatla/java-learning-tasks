/**
 * 
 */
package com.learningbydoing.validator;

import java.util.List;
import java.util.stream.Collectors;

import com.learningbydoing.document.Document;

/**
 * @author Maddipatla Chandra Babu
 * 
 * @date 08-Feb-2018
 */
public class AbstractValidator implements Validator {

	public boolean isAllDocumentsValid(List<Document> documents) {
		return false;
	}

	protected boolean isAllAreDocuments(List<Document> documents) {
		List<Document> filteredDocs = documents.stream().filter(
				document -> document.getClass().isAnnotationPresent(com.learningbydoing.annotation.Document.class))
				.collect(Collectors.toList());
		return documents.size() != filteredDocs.size();
	}
}
