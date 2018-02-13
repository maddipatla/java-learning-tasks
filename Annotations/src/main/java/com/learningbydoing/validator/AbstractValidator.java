/**
 *
 */
package com.learningbydoing.validator;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.learningbydoing.annotation.NotNull;
import com.learningbydoing.document.Aadhaar;
import com.learningbydoing.document.Document;
import com.learningbydoing.exception.AllAreNotDocumentsException;

/**
 * @author Maddipatla Chandra Babu
 * @date 08-Feb-2018
 */
public abstract class AbstractValidator implements Validator {

	protected String name;
	protected String email;
	protected Date dateOfBirth;
	protected Long mobileNumber;

	protected List<Document> documents;

	public AbstractValidator(List<Document> documents) {
		this.documents = documents;
		isAllDocumentsValid();
		isDocumentsContainAadhaar();
		GenericValidator.validateAnnotationOnField(documents, NotNull.class, null);
		GenericValidator.validateAnnotationOnMethod(documents, NotNull.class, null);
	}

	public boolean isAllDocumentsValid() {
		List<Document> filteredDocs = documents.stream().filter(
				document -> document.getClass().isAnnotationPresent(com.learningbydoing.annotation.Document.class))
				.collect(Collectors.toList());
		if (documents.size() != filteredDocs.size()) {
			throw new AllAreNotDocumentsException(
					"Seems you missed annotating a document with @Document by passing name");
		}
		return true;
	}

	@Override
	public boolean isDocumentsContainAadhaar() {
		for (Document document : documents) {
			if (document instanceof Aadhaar) {
				name = ((Aadhaar) document).getName();
				email = ((Aadhaar) document).getEmail();
				mobileNumber = ((Aadhaar) document).getMobileNumber();
				dateOfBirth = ((Aadhaar) document).getDateOfBirth();
				return true;
			}
		}
		return false;

	}

}