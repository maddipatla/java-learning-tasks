package com.learningbydoing.wikicall;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Maddipatla Chandra Babu
 * 
 * @date 07-Feb-2018
 */
public class WikiCallRecursiveAction extends RecursiveAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1717977980508872106L;

	static final Logger logger = LogManager.getLogger(WikiCallRecursiveAction.class.getName());

	public static final Integer THRESHOLD = 1;
	private List<String> strings;
	private String wikiURLString;
	private Path outputFilePath;

	/**
	 * @param strings
	 * @param wikiURLString
	 * @param outputFilePath
	 */
	public WikiCallRecursiveAction(List<String> strings, String wikiURLString, Path outputFilePath) {
		this.strings = strings;
		this.wikiURLString = wikiURLString;
		this.outputFilePath = outputFilePath;
	}

	@Override
	protected void compute() {
		if (strings.size() > THRESHOLD)
			ForkJoinTask.invokeAll(createSubtasks());
		new WikiCallThread(strings.get(0), wikiURLString, outputFilePath).run();
	}

	/**
	 * @return
	 */
	private List<WikiCallRecursiveAction> createSubtasks() {
		List<WikiCallRecursiveAction> subtasks = new ArrayList<>();

		subtasks.add(
				new WikiCallRecursiveAction(strings.subList(0, strings.size() / 2), wikiURLString, outputFilePath));
		subtasks.add(new WikiCallRecursiveAction(strings.subList(strings.size() / 2, strings.size()), wikiURLString,
				outputFilePath));

		return subtasks;
	}
}
