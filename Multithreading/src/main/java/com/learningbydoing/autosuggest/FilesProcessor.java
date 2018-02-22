package com.learningbydoing.autosuggest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.learningbydoing.exception.NoPathExistException;

/**
 * @author Maddipatla Chandra Babu
 * 
 * @date 22-Feb-2018
 */
public class FilesProcessor {
	static final Logger logger = LogManager.getLogger(FilesProcessor.class.getName());
	private static final String DEFAULT_PATH = System.getProperty("user.dir") + File.separator + "task2";

	private Path filesPath;
	private Path outputFilePath;

	public FilesProcessor() {
		this.filesPath = Paths.get(DEFAULT_PATH);
		this.outputFilePath = Paths.get(DEFAULT_PATH);
	}

	/**
	 * @param filesPath
	 * @param outputFilePath
	 */
	public FilesProcessor(String filesPath, String outputFilePath) {
		if (filesPath != null && outputFilePath != null) {
			this.filesPath = Paths.get(filesPath);
			this.outputFilePath = Paths.get(outputFilePath);
			if ((!this.filesPath.toFile().exists() && !this.filesPath.toFile().isDirectory())
					|| (!this.outputFilePath.toFile().exists() && !this.outputFilePath.toFile().isDirectory()))
				throw new NoPathExistException("Directory doesn't exist");

		} else
			throw new NoPathExistException("Directory can't be null");
	}

	/**
	 * Thread pool created with available cores multiplied by 30 (Reason 30 is, as
	 * per my knowledge ideal thread count per core should be 30 to 40 to avoid
	 * performance hit.) number of threads.
	 * 
	 * Walking through all the files and submitting them to the ExecutorService to
	 * execute as and when Thread is available.
	 * 
	 */
	public void process() {
		Long startTime = System.currentTimeMillis();
		ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 30);

		try (Stream<Path> paths = Files.walk(filesPath)) {
			paths.filter(Files::isRegularFile).forEach(file -> executorService.execute(new AddWordsToTrieThread(file)));
		} catch (IOException e) {
			logger.warn("Exception in WordCount.processWordCount(): {}", e.getMessage());
		}

		executorService.shutdown();
		while (!executorService.isTerminated()) {
			logger.info("Waiting for all threads to be finished with their work and executorService is shutdown");
		}

		logger.info("Time taken: {}", System.currentTimeMillis() - startTime);
	}

}
