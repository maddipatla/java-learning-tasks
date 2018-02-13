package com.learningbydoing.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.learningbydoing.annotation.NotNull;
import com.learningbydoing.document.Aadhaar;
import com.learningbydoing.document.Document;
import com.learningbydoing.exception.NotMatchException;

/**
 * @author Maddipatla Chandra Babu
 * @date 13-Feb-2018
 */
public class GenericValidator {
	static final Logger logger = LogManager.getLogger(GenericValidator.class.getName());

	private GenericValidator() {
		throw new IllegalStateException("Utility class, You can't instantiate.");
	}

	public static boolean validateAnnotationOnField(List<Document> documents, Class<? extends Annotation> annotation,
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

	public static boolean validateAnnotationOnMethod(List<Document> documents, Class<? extends Annotation> annotation,
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
