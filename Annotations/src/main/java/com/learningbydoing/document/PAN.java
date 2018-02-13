/**
 *
 */
package com.learningbydoing.document;

import com.learningbydoing.annotation.DateOfBirth;
import com.learningbydoing.annotation.Document;
import com.learningbydoing.annotation.Name;
import com.learningbydoing.annotation.NotNull;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Maddipatla Chandra Babu
 * @date 08-Feb-2018
 */
@Document(name = "PAN")
public class PAN implements Serializable, com.learningbydoing.document.Document {

	/**
	 *
	 */
	private static final long serialVersionUID = 9008790655933548253L;
	@NotNull
	@Name(type = String.class)
	private String name;
	@NotNull
	private String number;
	@NotNull
	@DateOfBirth(type = Date.class)
	private Date dateOfBirth;

	public PAN(String name, String number, Date dateOfBirth) {
		super();
		this.name = name;
		this.number = number;
		this.dateOfBirth = dateOfBirth;
	}

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

	@Override
	public String toString() {
		return "PAN [name=" + name + ", number=" + number + ", dateOfBirth=" + dateOfBirth + "]";
	}

}
