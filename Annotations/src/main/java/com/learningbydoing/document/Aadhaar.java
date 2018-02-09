/**
 * 
 */
package com.learningbydoing.document;

import java.io.Serializable;
import java.util.Date;

import com.learningbydoing.annotation.DateOfBirth;
import com.learningbydoing.annotation.Document;
import com.learningbydoing.annotation.Email;
import com.learningbydoing.annotation.Mobile;
import com.learningbydoing.annotation.Name;
import com.learningbydoing.annotation.NotNull;

/**
 * @author Maddipatla Chandra Babu
 * 
 * @date 08-Feb-2018
 */
@Document(name = "Aadhaar")
public class Aadhaar implements Serializable, com.learningbydoing.document.Document {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5330777006452541654L;

	public Aadhaar(String name, Long aadhaarNumber, Date dateOfBirth, String email, Long mobileNumber) {
		super();
		this.name = name;
		this.aadhaarNumber = aadhaarNumber;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.mobileNumber = mobileNumber;
	}

	@NotNull
	@Name
	private String name;

	@NotNull
	private Long aadhaarNumber;

	@NotNull
	@DateOfBirth
	private Date dateOfBirth;

	@NotNull
	@Email
	private String email;

	@NotNull
	@Mobile
	private Long mobileNumber;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getAadhaarNumber() {
		return aadhaarNumber;
	}

	public void setAadhaarNumber(Long aadhaarNumber) {
		this.aadhaarNumber = aadhaarNumber;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

}
