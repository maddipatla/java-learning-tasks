package com.learningbydoing.wordcount;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;

public class WordCountThread implements Runnable {

	private Path file;
	public static ConcurrentHashMap<String, Long> wordCount = new ConcurrentHashMap<>();

	public WordCountThread(Path file) {
		this.file = file;
	}

	@Override
	public void run() {
		try {
			Files.lines(file).forEach(line -> {
				String[] strings = line.split(" ");
				for (String string : strings) {
					if (!string.trim().isEmpty()) {
						if (wordCount.containsKey(string))
							wordCount.put(string, wordCount.get(string) + 1);
						else
							wordCount.put(string, 1L);
					}
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
