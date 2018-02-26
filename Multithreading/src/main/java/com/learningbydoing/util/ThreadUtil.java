/**
 * 
 */
package com.learningbydoing.util;

/**
 * @author Maddipatla Chandra Babu
 * @date 26-Feb-2018
 */
public class ThreadUtil {

	/**
	 * 
	 */
	private ThreadUtil() {
	}

	public static Integer getThreadCountToCreate(Long noOfTasksToBeProcessed) {
		Integer availableProcessors = Runtime.getRuntime().availableProcessors();
		Long wordsPerProcessor = noOfTasksToBeProcessed / availableProcessors;
		if (wordsPerProcessor > 40L)
			return 40;
		return wordsPerProcessor.intValue();
	}
}
