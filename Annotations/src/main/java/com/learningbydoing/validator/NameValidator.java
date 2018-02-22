/**
 *
 */
package com.learningbydoing.validator;

import java.util.List;

import com.learningbydoing.annotation.Name;
import com.learningbydoing.document.Document;

/**
 * @author Maddipatla Chandra Babu
 * @date 08-Feb-2018
 */
public class NameValidator extends AbstractValidator {

	public NameValidator(List<Document> documents) {
		super(documents);
	}

	@Override
	public boolean isAllDocumentsHaveConsistentDataAgainstAadhaar() {
		return validateAnnotationOnField(Name.class, name) && validateAnnotationOnMethod(Name.class, name);
	}

}
