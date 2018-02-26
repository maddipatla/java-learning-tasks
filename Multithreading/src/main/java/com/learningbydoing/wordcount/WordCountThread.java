package com.learningbydoing.wordcount;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Maddipatla Chandra Babu
 * 
 * @date 07-Feb-2018
 */
public class WordCountThread implements Runnable {
	static final Logger logger = LogManager.getLogger(WordCountThread.class.getName());
	private Path file;
	private static ConcurrentMap<String, Long> wordCount = new ConcurrentHashMap<>();

	public WordCountThread(Path file) {
		this.file = file;
	}

	/**
	 * Read lines from file using Stream and nio API.
	 * 
	 * Split each line by one or more spaces to get words.
	 * 
	 * Replace all special characters in each word with empty String("")
	 * 
	 * Loop through the String array and check String exists in wordCount map if it
	 * contains increment value otherwise put this word in the map with value as 1.
	 * 
	 */
	@Override
	public void run() {
		try (Stream<String> stream = Files.lines(file)) {
			stream.forEach(line -> {
				String[] strings = line.split("\\s+");
				for (String string : strings) {
					String stringWithOnlyAlphaNumeric = string.replaceAll("[^A-Za-z0-9]", "").trim();
					if (!stringWithOnlyAlphaNumeric.trim().isEmpty()) {
						wordCount.compute(stringWithOnlyAlphaNumeric, (key,
								value) -> (value == null) ? wordCount.put(key, 1L) : wordCount.put(key, value + 1));
					}
				}
			});
		} catch (IOException e) {
			logger.warn("Exception in WordCountThread.run(): {}", e.getMessage());
		}
	}

	public static ConcurrentMap<String, Long> getWordCount() {
		return wordCount;
	}

}
