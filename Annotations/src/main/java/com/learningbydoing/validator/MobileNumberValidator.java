/**
 *
 */
package com.learningbydoing.validator;

import java.util.List;

import com.learningbydoing.annotation.Mobile;
import com.learningbydoing.document.Document;

/**
 * @author Maddipatla Chandra Babu
 * @date 09-Feb-2018
 */
public class MobileNumberValidator extends AbstractValidator {

	public MobileNumberValidator(List<Document> documents) {
		super(documents);
	}

	@Override
	public boolean isAllDocumentsHaveConsistentDataAgainstAadhaar() {
		return GenericValidator.validateAnnotationOnField(documents, Mobile.class, mobileNumber)
				&& GenericValidator.validateAnnotationOnMethod(documents, Mobile.class, mobileNumber);
	}

}
