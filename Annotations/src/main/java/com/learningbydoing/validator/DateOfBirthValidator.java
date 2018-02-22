/**
 *
 */
package com.learningbydoing.validator;

import java.util.List;

import com.learningbydoing.annotation.DateOfBirth;
import com.learningbydoing.document.Document;

/**
 * @author Maddipatla Chandra Babu
 * @date 09-Feb-2018
 */
public class DateOfBirthValidator extends AbstractValidator {

	public DateOfBirthValidator(List<Document> documents) {
		super(documents);
	}

	@Override
	public boolean isAllDocumentsHaveConsistentDataAgainstAadhaar() {
		return validateAnnotationOnField(DateOfBirth.class, dateOfBirth)
				&& validateAnnotationOnMethod(DateOfBirth.class, dateOfBirth);
	}

}
