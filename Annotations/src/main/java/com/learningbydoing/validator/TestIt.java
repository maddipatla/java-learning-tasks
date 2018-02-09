/**
 * 
 */
package com.learningbydoing.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.learningbydoing.annotation.NotNull;
import com.learningbydoing.document.Aadhaar;
import com.learningbydoing.document.BankStatement;
import com.learningbydoing.document.Document;
import com.learningbydoing.document.PAN;
import com.learningbydoing.exception.AllAreNotDocumentsException;

/**
 * @author Maddipatla Chandra Babu
 * 
 * @date 08-Feb-2018
 */
public class TestIt {
	
	
	public static void main(String[] args) {
		Date date = null;
		try {
			date = new SimpleDateFormat("dd-MMM-yyyy").parse("15-APR-1991");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		PAN pan = new PAN("Chandu", "my pan nUmber", date);
		Aadhaar aadhaar = new Aadhaar("Chandu", 610916239383L, date, "chandu@gmail.com", 9160605056L);
		BankStatement statement = new BankStatement("Chandu", 30504010L, date, "chandra@email.com", 91606050L);
		List<Document> documents = new ArrayList<>();
		documents.add(pan);
		documents.add(aadhaar);
		documents.add(statement);
		System.out.println(validateNotNullAnnotaions(documents));

	}

	private static boolean isAllAreDocuments(List<Document> documents) {
		List<Document> filteredDocs = documents.stream().filter(
				document -> document.getClass().isAnnotationPresent(com.learningbydoing.annotation.Document.class))
				.collect(Collectors.toList());
		if (documents.size() != filteredDocs.size())
			throw new AllAreNotDocumentsException(
					"Seems you missed annotating a document with @Document by passing name");
		return true;
	}

	private static boolean validateNotNullAnnotaions(List<Document> documents) {
		documents.stream().forEach(document -> {
			Field[] fields = document.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				Annotation[] annotations = field.getAnnotationsByType(NotNull.class);
				for (Annotation annotation : annotations) {
					try {
						if (field.get(document) == null)
							throw new NullPointerException("Field value should not be null");
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		});
		return true;
	}
}
