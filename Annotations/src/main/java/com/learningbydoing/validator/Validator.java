/**
 * 
 */
package com.learningbydoing.validator;

import java.util.List;

import com.learningbydoing.document.Document;

/**
 * @author Maddipatla Chandra Babu
 * 
 * @date 08-Feb-2018
 */
public interface Validator {
	boolean isAllDocumentsValid(List<Document> documents);

}