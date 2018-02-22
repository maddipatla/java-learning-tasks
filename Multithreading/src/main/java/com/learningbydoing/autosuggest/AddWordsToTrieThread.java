package com.learningbydoing.autosuggest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Maddipatla Chandra Babu
 * 
 * @date 22-Feb-2018
 */
public class AddWordsToTrieThread implements Runnable {
	static final Logger logger = LogManager.getLogger(AddWordsToTrieThread.class.getName());
	private Path file;

	public AddWordsToTrieThread(Path file) {
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
					String stringWithOnlyAlphabet = string.replaceAll("[^A-Za-z]", "").trim();
					if (!stringWithOnlyAlphabet.trim().isEmpty()) {
						AutoSuggest.trie.addWord(stringWithOnlyAlphabet);
					}
				}
			});
		} catch (IOException e) {
			logger.warn("Exception in AddWordsToTrieThread.run(): {}", e.getMessage());
		}
	}

}
