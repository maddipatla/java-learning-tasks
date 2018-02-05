package com.learningbydoing.scheduler;

import java.util.Comparator;

public class ScheduleComparator implements Comparator<Person> {

	@Override
	public int compare(Person o1, Person o2) {
		if (o1.isScheduled() == o2.isScheduled())
			return 0;
		else if (o1.isScheduled())
			return 1;
		else if (o2.isScheduled())
			return 1;
		return -1;
	}

}
