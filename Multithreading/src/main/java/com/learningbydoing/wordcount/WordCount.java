package com.learningbydoing.wordcount;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.learningbydoing.exception.NoPathExistException;

public class WordCount {
	static final Logger logger = LogManager.getLogger(WordCount.class.getName());
	private static final String DEFAULT_PATH = System.getProperty("user.dir") + File.separator + "task2";

	private Path filesPath;
	private Path outputFilePath;

	public WordCount() {
		this.filesPath = Paths.get(DEFAULT_PATH);
		this.outputFilePath = Paths.get(DEFAULT_PATH);
	}

	public WordCount(String filesPath, String outputFilePath) {
		if (filesPath != null && outputFilePath != null) {
			this.filesPath = Paths.get(filesPath);
			this.outputFilePath = Paths.get(outputFilePath);
			if ((!this.filesPath.toFile().exists() && !this.filesPath.toFile().isDirectory())
					|| (!this.outputFilePath.toFile().exists() && !this.outputFilePath.toFile().isDirectory()))
				throw new NoPathExistException("Directory doesn't exist");

		} else
			throw new NoPathExistException("Directory can't be null");
	}

	public void processWordCount() {
		Long startTime = System.currentTimeMillis();
		ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 20);

		try (Stream<Path> paths = Files.walk(filesPath)) {
			paths.filter(Files::isRegularFile).forEach(file -> executorService.execute(new WordCountThread(file)));
		} catch (IOException e) {
			logger.warn("Exception in WordCount.processWordCount(): {}", e.getMessage());
		}

		executorService.shutdown();
		while (!executorService.isTerminated()) {
			logger.info("Waiting for all threads to be finished with their work and executorService is shutdown");
		}

		try {
			Files.write(Paths.get(outputFilePath + File.separator + "WordCount"),
					() -> WordCountThread.getWordCount().entrySet().stream()
							.<CharSequence>map(e -> e.getKey() + " = " + e.getValue()).iterator(),
					StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			logger.info("Output File stored location: {}", outputFilePath + File.separator + "WordCount");
		} catch (IOException e) {
			logger.warn("Exception in WordCount.processWordCount(): {}", e.getMessage());
		}
		logger.info("Time taken: {}", System.currentTimeMillis() - startTime);
	}
}
