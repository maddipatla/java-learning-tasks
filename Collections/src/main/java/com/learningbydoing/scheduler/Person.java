package com.learningbydoing.scheduler;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Person {

	public Person(String name, String emailId, Long phoneNo, LocalTime inTime, boolean availableForInterview,
			boolean isScheduled) {
		super();
		this.name = name;
		this.emailId = emailId;
		this.phoneNo = phoneNo;
		this.inTime = inTime;
		this.availableForInterview = availableForInterview;
		this.isScheduled = isScheduled;
	}

	private String name;

	private String emailId;

	private Long phoneNo;

	private LocalTime inTime;

	private boolean availableForInterview;

	private boolean isScheduled;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Long getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(Long phoneNo) {
		this.phoneNo = phoneNo;
	}

	public LocalTime getInTime() {
		return inTime;
	}

	public void setInTime(LocalTime inTime) {
		this.inTime = inTime;
	}

	public boolean isAvailableForInterview() {
		return availableForInterview;
	}

	public void setAvailableForInterview(boolean availableForInterview) {
		this.availableForInterview = availableForInterview;
	}

	public boolean isScheduled() {
		return isScheduled;
	}

	public void setScheduled(boolean isScheduled) {
		this.isScheduled = isScheduled;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", emailId=" + emailId + ", phoneNo=" + phoneNo + ", inTime=" + inTime
				+ ", availableForInterview=" + availableForInterview + ", isScheduled=" + isScheduled + "]";
	}

	public static List<Person> getScheduledPersons() {
		List<Person> persons = new ArrayList<>();
		LocalTime time = LocalTime.parse("08:00");
		for (int i = 1; i <= 20; i++) {
			persons.add(
					new Person("Person-" + i, "person-" + i + "@mail.com", 9191918231L + i, time, i % 2 == 0, true));
			time = time.plus(2, ChronoUnit.MINUTES);
		}
		return persons;
	}

	public static List<Person> getUnscheduledPersons() {
		List<Person> persons = new ArrayList<>();
		LocalTime time = LocalTime.parse("10:00");
		for (int i = 21; i <= 100; i++) {
			persons.add(
					new Person("Person-" + i, "person-" + i + "@mail.com", 9191918231L + i, time, i % 2 == 1, false));
			time = time.plus(2, ChronoUnit.MINUTES);
		}
		return persons;
	}
}
