/**
 *
 */
package com.learningbydoing.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.learningbydoing.annotation.NotNull;
import com.learningbydoing.document.Aadhaar;
import com.learningbydoing.document.Document;
import com.learningbydoing.exception.AllAreNotDocumentsException;
import com.learningbydoing.exception.NotMatchException;

/**
 * @author Maddipatla Chandra Babu
 * @date 08-Feb-2018
 */
public abstract class AbstractValidator implements Validator {

	private static final Logger logger = LogManager.getLogger(AbstractValidator.class);

	protected String name;
	protected String email;
	protected Date dateOfBirth;
	protected Long mobileNumber;

	protected List<Document> documents;

	public AbstractValidator(List<Document> documents) {
		this.documents = documents;
		isAllDocumentsValid();
		isDocumentsContainAadhaar();
		validateAnnotationOnField(documents, NotNull.class, null);
		validateAnnotationOnMethod(documents, NotNull.class, null);
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

	protected boolean validateAnnotationOnField(List<Document> documents, Class<? extends Annotation> annotation,
			Object baseFieldToValidate) {
		for (Document document : documents) {
			if (!(document instanceof Aadhaar)) {
				Field[] fields = document.getClass().getDeclaredFields();
				for (Field field : fields) {
					field.setAccessible(true);
					if (field.isAnnotationPresent(annotation)) {
						try {
							Object data = field.get(document);
							if (data == null) {
								throw new NullPointerException(new StringBuilder("value should not be null for field: "
										+ field.getName() + ", in document: " + document).toString());
							}

							if (annotation.equals(NotNull.class))
								continue;

							if (!(field.getType().equals(baseFieldToValidate.getClass())
									&& baseFieldToValidate.equals(data))) {
								logger.warn(
										"Not match for field: {}, In {}, Actual data from Aadhaar: {}, data from document: {}",
										field.getName(), document.getClass(), baseFieldToValidate, data);
								throw new NotMatchException(
										new StringBuilder("Data mismatch in document " + document).toString());
							}
						} catch (IllegalArgumentException | IllegalAccessException e) {

						}
					}
				}
			}
		}
		return true;

	}

	protected boolean validateAnnotationOnMethod(List<Document> documents, Class<? extends Annotation> annotation,
			Object baseFieldToValidate) {
		for (Document document : documents) {
			if (!(document instanceof Aadhaar)) {
				Method[] methods = document.getClass().getDeclaredMethods();
				for (Method method : methods) {
					method.setAccessible(true);
					if (method.isAnnotationPresent(annotation)) {
						try {
							Object data = method.invoke(document);
							if (data == null) {
								throw new NullPointerException(
										new StringBuilder("value should not be null from method: " + method.getName()
												+ ", in document: " + document).toString());
							}

							if (annotation.equals(NotNull.class))
								continue;

							if (!(method.getReturnType().equals(baseFieldToValidate.getClass())
									&& baseFieldToValidate.equals(data))) {
								logger.warn(
										"Not match for method: {}, In {}, Actual data from Aadhaar: {}, data from Aadhaar: {}",
										method.getName(), document.getClass(), baseFieldToValidate, data);
								throw new NotMatchException(
										new StringBuilder("Data mismatch in document: " + document).toString());
							}
						} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {

						}
					}
				}
			}
		}
		return true;

	}

}