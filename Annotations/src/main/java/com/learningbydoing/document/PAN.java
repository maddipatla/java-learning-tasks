/**
 * 
 */
package com.learningbydoing.document;

import java.io.Serializable;
import java.util.Date;

import com.learningbydoing.annotation.DateOfBirth;
import com.learningbydoing.annotation.Document;
import com.learningbydoing.annotation.Name;
import com.learningbydoing.annotation.NotNull;

/**
 * @author Maddipatla Chandra Babu
 * 
 * @date 08-Feb-2018
 */
@Document(name = "PAN")
public class PAN implements Serializable, com.learningbydoing.document.Document {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9008790655933548253L;

	public PAN(String name, String number, Date dateOfBirth) {
		super();
		this.name = name;
		this.number = number;
		this.dateOfBirth = dateOfBirth;
	}

	@NotNull
	@Name
	private String name;

	@NotNull
	private String number;

	@NotNull
	@DateOfBirth
	private Date dateOfBirth;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

}
