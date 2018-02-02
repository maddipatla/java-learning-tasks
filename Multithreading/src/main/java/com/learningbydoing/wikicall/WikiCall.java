package com.learningbydoing.wikicall;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WikiCall {
	static final Logger logger = LogManager.getLogger(WikiCall.class.getName());

	static final String DEFAULT_FILE_ONE = "Multithreading_Task_2_fortune1000companies.txt";
	static final String DEFAULT_FILE_TWO = "Multithreading_Task2_ProgrammingLanguages.txt";
	static final String DEFAULT_FILE_THREE = "Multithreading_Task_2_java Keywords.txt";

	public static void main(String[] args) {
		Long startTime = System.currentTimeMillis();
		List<String> strings = new ArrayList<>();
		ExecutorService serviceToGetStrings = Executors.newFixedThreadPool(3);
		Future<List<String>> futureOne = serviceToGetStrings
				.submit(new ThreadToExtractStringsByNewLine(DEFAULT_FILE_TWO));
		Future<List<String>> futureTwo = serviceToGetStrings
				.submit(new ThreadToExtractStringsByDelimeter(DEFAULT_FILE_THREE, ",,,"));
		Future<List<String>> futureThree = serviceToGetStrings
				.submit(new ThreadToExtractStringsByDelimeterAndPosition(DEFAULT_FILE_ONE, "  ", 2));
		try {
			strings.addAll(futureOne.get());
			strings.addAll(futureTwo.get());
			strings.addAll(futureThree.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		serviceToGetStrings.shutdown();
		while (!serviceToGetStrings.isTerminated())
			;
		ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 20);
		for (String string : strings) {
			service.execute(new WikiCallThread(string));
		}
		service.shutdown();
		while (!service.isTerminated())
			;
		logger.info("Time taken: {}", System.currentTimeMillis() - startTime);
	}

	public List<String> extractStrings(String filePath, String delimeter) {
		List<String> strings = new ArrayList<>();
		try {
			strings.addAll(Files.lines(Paths.get(getClass().getClassLoader().getResource(filePath).toURI()))
					.map(string -> string.split(delimeter)).flatMap(x -> Arrays.stream(x))
					.collect(Collectors.toList()));
			for (String string : strings) {
				System.out.println(string);
			}
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		return strings;
	}

	public List<String> extractStrings(String filePath) {
		List<String> strings = new ArrayList<>();
		try {
			strings.addAll(Files.lines(Paths.get(getClass().getClassLoader().getResource(filePath).toURI()))
					.collect(Collectors.toList()));
			for (String string : strings) {
				System.out.println(string);
			}
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		return strings;
	}

	public List<String> extractStrings(String filePath, String delimeter, Integer positionOfString) {
		List<String> strings = new ArrayList<>();
		try {
			strings.addAll(Files.lines(Paths.get(getClass().getClassLoader().getResource(filePath).toURI()))
					.map(string -> string.split(delimeter)).map(array -> array[positionOfString - 1])
					.collect(Collectors.toList()));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		return strings;
	}
}
