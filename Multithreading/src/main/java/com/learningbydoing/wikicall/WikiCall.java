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
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.learningbydoing.exception.NoFileExtensionException;
import com.learningbydoing.exception.NoPathExistException;

public class WikiCall {
	static final Logger logger = LogManager.getLogger(WikiCall.class.getName());

	public static final String DEFAULT_FILE_ONE = "Multithreading_Task_2_fortune1000companies.txt";
	public static final String DEFAULT_FILE_TWO = "Multithreading_Task2_ProgrammingLanguages.txt";
	public static final String DEFAULT_FILE_THREE = "Multithreading_Task_2_java Keywords.txt";

	public static final String WIKI_DEFAULT_URL_STRING = "https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles=";
	private static final String DEFAULT_DELIMETER = "\\s{2}";

	static ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() * 20);

	private Path filePath;
	private String delimeter = null;
	private Integer position = -1;
	private Path outputFilePath;
	private String wikiURLString;
	private boolean useForkJoin;

	public WikiCall(boolean useForkJoin) {
		try {
			this.useForkJoin = useForkJoin;
			this.filePath = Paths.get(getClass().getClassLoader().getResource(DEFAULT_FILE_TWO).toURI());
			this.wikiURLString = WIKI_DEFAULT_URL_STRING;
			StringBuilder builder = new StringBuilder(System.getProperty("user.dir"));
			builder.append(File.separator);
			builder.append("task2");
			builder.append(File.separator);
			this.outputFilePath = Paths.get(builder.toString());
			if (!Paths.get(outputFilePath.toUri()).toFile().exists())
				Files.createDirectory(this.outputFilePath);
		} catch (URISyntaxException | IOException e) {
			logger.warn("Exception in WikiCall(): {}", e.getMessage());
		}
	}

	public WikiCall(String filePath, String outputFilePath, boolean useForkJoin, String wikiURLString) {
		this.useForkJoin = useForkJoin;
		this.wikiURLString = wikiURLString;
		if (filePath != null && outputFilePath != null) {
			if (FilenameUtils.getExtension(filePath).isEmpty())
				throw new NoFileExtensionException("You must specify file extension");
			this.filePath = Paths.get(filePath);
			this.outputFilePath = Paths.get(outputFilePath);
			if ((!this.filePath.toFile().exists() && !this.filePath.toFile().isFile())
					|| (!this.outputFilePath.toFile().exists() && !this.outputFilePath.toFile().isDirectory()))
				throw new NoPathExistException("File path or output file directory doesn't exist");

		} else
			throw new NoPathExistException("File path or output file directory can't be null");
	}

	public WikiCall(String filePath, String delimeter, String outputFilePath, boolean useForkJoin,
			String wikiURLString) {
		this(filePath, outputFilePath, useForkJoin, wikiURLString);
		if (delimeter.trim().isEmpty())
			this.delimeter = DEFAULT_DELIMETER;
		else
			this.delimeter = delimeter;
	}

	public WikiCall(String filePath, String delimeter, Integer position, String outputFilePath, boolean useForkJoin,
			String wikiURLString) {
		this(filePath, delimeter, outputFilePath, useForkJoin, wikiURLString);
		this.position = position;
	}

	public void fetchStringsMakeWikiCallAndWrite() {
		Long startTime = System.currentTimeMillis();
		List<String> strings = getExtractedStrings();
		if (useForkJoin)
			pool.invoke(new WikiCallRecursiveAction(strings, wikiURLString, outputFilePath));
		else
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
			logger.warn("Exception in WikiCall.getExtractedStrings: {}", e.getMessage());
		}
		serviceToGetStrings.shutdown();
		while (!serviceToGetStrings.isTerminated()) {
			logger.info("Waiting for all threads to be finished with their work and executorService is shutdown");
		}
		return strings;
	}

	public void makeWikiCallAndWriteToFiles(List<String> strings) {
		ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 20);
		for (String string : strings) {
			service.execute(new WikiCallThread(string, wikiURLString, outputFilePath));
		}
		service.shutdown();
		while (!service.isTerminated()) {
			logger.info("Waiting for all threads to be finished with their work and executorService is shutdown");
		}
	}
}
