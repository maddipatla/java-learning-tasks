package com.learningbydoing.wikicall;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WikiCall {
	static final Logger logger = LogManager.getLogger(WikiCall.class.getName());

	public static final String DEFAULT_FILE_ONE = "Multithreading_Task_2_fortune1000companies.txt";
	public static final String DEFAULT_FILE_TWO = "Multithreading_Task2_ProgrammingLanguages.txt";
	public static final String DEFAULT_FILE_THREE = "Multithreading_Task_2_java Keywords.txt";

	private static final String WIKI_DEFAULT_URL_STRING = "https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles=";
	private static final String DEFAULT_DELIMETER = "\\s{2}";
	private Path filePath;
	private String delimeter = null;
	private Integer position = -1;
	private Path outputFilePath;
	private String wikiURLString;

	public WikiCall() {
		try {
			this.filePath = Paths.get(getClass().getClassLoader().getResource(DEFAULT_FILE_TWO).toURI());
			this.wikiURLString = WIKI_DEFAULT_URL_STRING;
			StringBuilder builder = new StringBuilder(System.getProperty("user.dir"));
			builder.append(File.separator);
			builder.append("task2");
			builder.append(File.separator);
			this.outputFilePath = Paths.get(builder.toString());
			Files.createDirectory(this.outputFilePath);
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
	}

	public WikiCall(String filePath, String outputFilePath) {
		if (filePath != null && outputFilePath != null) {
			if (FilenameUtils.getExtension(filePath).isEmpty())
				throw new RuntimeException("You must specify file extension");
			this.filePath = Paths.get(filePath);
			this.outputFilePath = Paths.get(outputFilePath);
			if ((!Files.exists(this.filePath) && !Files.isRegularFile(this.filePath))
					|| (!Files.exists(this.outputFilePath) && !Files.isDirectory(this.outputFilePath)))
				throw new RuntimeException("File path or output file directory doesn't exist");

		} else
			throw new RuntimeException("File path or output file directory can't be null");
		// if (Files.exists(this.filePath) && Files.exists(this.outputFilePath))
		// throw new RuntimeException("File Path doesn't exist. Please make sure file
		// path exists");
	}

	public WikiCall(String filePath, String delimeter, String outputFilePath) {
		this(filePath, outputFilePath);
		if (delimeter.trim().isEmpty())
			this.delimeter = DEFAULT_DELIMETER;
		else
			this.delimeter = delimeter;
	}

	public WikiCall(String filePath, String delimeter, Integer position, String outputFilePath) {
		this(filePath, delimeter, outputFilePath);
		this.position = position;
	}

	public void fetchStringsMakeWikiCallAndWrite() {
		Long startTime = System.currentTimeMillis();
		List<String> strings = getExtractedStrings();
		makeWikiCallAndWriteToFiles(strings);
		logger.info("Time taken: {}", System.currentTimeMillis() - startTime);
	}

	public List<String> getExtractedStrings() {
		List<String> strings = new ArrayList<>();
		ExecutorService serviceToGetStrings = Executors.newSingleThreadExecutor();
		Future<List<String>> future;
		if (position != -1)
			future = serviceToGetStrings.submit(new ExtractStringsByDelimeterAndPosition(DEFAULT_FILE_ONE, "  ", 2));
		else if (delimeter != null)
			future = serviceToGetStrings.submit(new ExtractStringsByDelimeter(DEFAULT_FILE_THREE, ",,,"));
		else
			future = serviceToGetStrings.submit(new ExtractStringsByNewLine(DEFAULT_FILE_TWO));
		try {
			strings.addAll(future.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		serviceToGetStrings.shutdown();
		while (!serviceToGetStrings.isTerminated())
			;
		return strings;
	}

	public void makeWikiCallAndWriteToFiles(List<String> strings) {
		ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 20);
		for (String string : strings) {
			service.execute(new WikiCallThread(string, wikiURLString, outputFilePath));
		}
		service.shutdown();
		while (!service.isTerminated())
			;
	}
}
