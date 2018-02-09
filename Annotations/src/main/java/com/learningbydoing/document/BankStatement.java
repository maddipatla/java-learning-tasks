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
@Document(name = "Bank Statement")
public class BankStatement implements Serializable, com.learningbydoing.document.Document {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2314331691893052921L;

	public BankStatement(String name, Long accountNumber, Date dateOfBirth, String email, Long phoneNumber) {
		super();
		this.name = name;
		this.accountNumber = accountNumber;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	@NotNull
	@Name
	private String name;

	@NotNull
	private Long accountNumber;

	@NotNull
	@DateOfBirth
	private Date dateOfBirth;

	@NotNull
	@Email
	private String email;

	@NotNull
	@Mobile
	private Long phoneNumber;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
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

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
