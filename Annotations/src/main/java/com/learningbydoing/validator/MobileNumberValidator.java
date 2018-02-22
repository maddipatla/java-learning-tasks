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
		return validateAnnotationOnField(Mobile.class, mobileNumber)
				&& validateAnnotationOnMethod(Mobile.class, mobileNumber);
	}

}
