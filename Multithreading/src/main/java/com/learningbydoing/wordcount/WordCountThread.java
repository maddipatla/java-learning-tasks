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

	@Override
	public void run() {
		try (Stream<String> stream = Files.lines(file)) {
			stream.forEach(line -> {
				String[] strings = line.split("\\s+");
				for (String string : strings) {
					String stringWithOnlyAlphaNumeric = string.replaceAll("[^A-Za-z0-9]", "").trim();
					if (!stringWithOnlyAlphaNumeric.trim().isEmpty()) {
						if (wordCount.containsKey(stringWithOnlyAlphaNumeric))
							wordCount.put(stringWithOnlyAlphaNumeric, wordCount.get(stringWithOnlyAlphaNumeric) + 1);
						else
							wordCount.put(stringWithOnlyAlphaNumeric, 1L);
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

	/**
	 * @param wordCount
	 */
	public static void setWordCount(ConcurrentMap<String, Long> wordCount) {
		WordCountThread.wordCount = wordCount;
	}

}
