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
import com.learningbydoing.util.ThreadUtil;

public class WikiCall {
	static final Logger logger = LogManager.getLogger(WikiCall.class.getName());

	public static final String DEFAULT_FILE_ONE = "Multithreading_Task_2_fortune1000companies.txt";
	public static final String DEFAULT_FILE_TWO = "Multithreading_Task2_ProgrammingLanguages.txt";
	public static final String DEFAULT_FILE_THREE = "Multithreading_Task_2_java Keywords.txt";

	public static final String WIKI_DEFAULT_URL_STRING = "https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles=";
	private static final String DEFAULT_DELIMETER = "\\s{2}";

	private Path filePath;
	private String delimiter = null;
	private Integer position = -1;
	private Path outputFilePath;
	private String wikiURLString;
	private boolean useForkJoin;

	/**
	 * @param useForkJoin
	 */
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

	/**
	 * @param filePath
	 * @param outputFilePath
	 * @param useForkJoin
	 * @param wikiURLString
	 */
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

	/**
	 * @param filePath
	 * @param delimiter
	 * @param outputFilePath
	 * @param useForkJoin
	 * @param wikiURLString
	 */
	public WikiCall(String filePath, String delimiter, String outputFilePath, boolean useForkJoin,
			String wikiURLString) {
		this(filePath, outputFilePath, useForkJoin, wikiURLString);
		if (delimiter.trim().isEmpty())
			this.delimiter = DEFAULT_DELIMETER;
		else
			this.delimiter = delimiter;
	}

	/**
	 * @param filePath
	 * @param delimiter
	 * @param position
	 * @param outputFilePath
	 * @param useForkJoin
	 * @param wikiURLString
	 */
	public WikiCall(String filePath, String delimiter, Integer position, String outputFilePath, boolean useForkJoin,
			String wikiURLString) {
		this(filePath, delimiter, outputFilePath, useForkJoin, wikiURLString);
		this.position = position;
	}

	/**
	 * Extract strings from a given file. If useForkJoin is true, use fork join
	 * framework recurisveAction to make a call to Wiki and extract the description
	 * from the json response and write it into a file.
	 * 
	 */
	public void fetchStringsMakeWikiCallAndWrite() {
		Long startTime = System.currentTimeMillis();
		List<String> strings = getExtractedStrings();
		if (useForkJoin) {
			Integer size = strings.size();
			ForkJoinPool pool = new ForkJoinPool(ThreadUtil.getThreadCountToCreate(size.longValue()));
			pool.invoke(new WikiCallRecursiveAction(strings, wikiURLString, outputFilePath));
		} else
			makeWikiCallAndWriteToFiles(strings);
		logger.info("Time taken: {}", System.currentTimeMillis() - startTime);
	}

	/**
	 * 
	 * @return list of strings from a given file as per the delimiter, position of
	 *         the string and other parameters specified.
	 */
	public List<String> getExtractedStrings() {
		List<String> strings = new ArrayList<>();
		ExecutorService serviceToGetStrings = Executors.newSingleThreadExecutor();
		Future<List<String>> future;
		if (position != -1)
			future = serviceToGetStrings.submit(new ExtractStringsByDelimeterAndPosition(DEFAULT_FILE_ONE, "  ", 2));
		else if (delimiter != null)
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

	/**
	 * This implementation uses executorService framework.
	 * 
	 * Make a call to Wiki for each and every string available in list and extract
	 * the description from the json response and write it into a file.
	 * 
	 * @param strings
	 */
	public void makeWikiCallAndWriteToFiles(List<String> strings) {
		Integer size = strings.size();
		ExecutorService service = Executors.newFixedThreadPool(ThreadUtil.getThreadCountToCreate(size.longValue()));
		for (String string : strings) {
			service.execute(new WikiCallThread(string, wikiURLString, outputFilePath));
		}
		service.shutdown();
		while (!service.isTerminated()) {
			logger.info("Waiting for all threads to be finished with their work and executorService is shutdown");
		}
	}

	public static void main(String[] args) {
		new WikiCall(true).fetchStringsMakeWikiCallAndWrite();
	}
}
