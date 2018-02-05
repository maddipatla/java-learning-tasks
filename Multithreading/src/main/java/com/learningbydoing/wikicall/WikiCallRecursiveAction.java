package com.learningbydoing.wikicall;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

	private List<WikiCallRecursiveAction> createSubtasks() {
		List<WikiCallRecursiveAction> subtasks = new ArrayList<>();

		List<String> partOne = strings.subList(0, strings.size() / 2);
		List<String> partTwo = strings.subList(strings.size() / 2, strings.size());

		subtasks.add(new WikiCallRecursiveAction(partOne, wikiURLString, outputFilePath));
		subtasks.add(new WikiCallRecursiveAction(partTwo, wikiURLString, outputFilePath));

		return subtasks;
	}
}
