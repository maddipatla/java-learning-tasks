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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WordCount {
	static final Logger logger = LogManager.getLogger(WordCount.class.getName());
	private static final String DEFAULT_PATH = "/home/chandrab/Pramati/Workspace/Java/Learning-Java-By-Doing/Java-Learning-Tasks/Multithreading/task2";

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
			if ((!Files.exists(this.filesPath) && !Files.isDirectory(this.filesPath))
					|| (!Files.exists(this.outputFilePath) && !Files.isDirectory(this.outputFilePath)))
				throw new RuntimeException("Directory doesn't exist");

		} else
			throw new RuntimeException("Directory can't be null");
	}

	public void processWordCount() {
		Long startTime = System.currentTimeMillis();
		ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 20);
		try {
			Files.walk(filesPath).filter(Files::isRegularFile).forEach(file -> {
				executorService.execute(new WordCountThread(file));
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		executorService.shutdown();
		while (!executorService.isTerminated())
			;
		try {
			Files.write(Paths.get(outputFilePath + File.separator + "WordCount"),
					() -> WordCountThread.wordCount.entrySet().stream()
							.<CharSequence>map(e -> e.getKey() + " = " + e.getValue()).iterator(),
					StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			logger.info("Output File stored location: {}", outputFilePath + File.separator + "WordCount");
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("Time taken: {}", System.currentTimeMillis() - startTime);
	}

}
