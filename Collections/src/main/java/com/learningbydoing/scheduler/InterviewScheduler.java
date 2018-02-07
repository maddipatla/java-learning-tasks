package com.learningbydoing.scheduler;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Maddipatla Chandra Babu
 * 
 * @date 07-Feb-2018
 */
public class InterviewScheduler {
	static final Logger logger = LogManager.getLogger(InterviewScheduler.class.getName());

	private List<Person> persons = new ArrayList<>();
	BlockingQueue<Person> queue = new PriorityBlockingQueue<>(100, new ScheduleComparator());

	public InterviewScheduler() {
		persons.addAll(Person.getScheduledPersons());
		persons.addAll(Person.getUnscheduledPersons());
		for (Person person : persons) {
			if (person.isScheduled()) {
				int number = person.getInTime().compareTo(LocalTime.parse("08:30"));
				if (number == 0 || number > 0) {
					person.setScheduled(false);
				}
			}
			queue.add(person);
		}
	}

	public void processInterview() {
		while (!queue.isEmpty()) {
			try {
				Person person = queue.take();
				if (!person.isAvailableForInterview()) {
					person.setAvailableForInterview(true);
					queue.put(person);
					continue;
				}
				Thread.sleep(5000);
				logger.info("Person interviewed: {}", person.getName());
			} catch (InterruptedException e) {
				logger.warn("Exception in InterviewScheduler.processInterview(): {}", e.getMessage());
				Thread.currentThread().interrupt();
			}
		}
	}

	public static void main(String[] args) {
		new InterviewScheduler().processInterview();
	}
}
