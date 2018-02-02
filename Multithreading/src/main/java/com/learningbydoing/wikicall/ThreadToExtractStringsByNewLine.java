package com.learningbydoing.wikicall;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class ThreadToExtractStringsByNewLine implements Callable<List<String>> {

	private String filePath;

	public ThreadToExtractStringsByNewLine(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public List<String> call() throws Exception {
		List<String> strings = new ArrayList<>();
		try {
			strings.addAll(Files.lines(Paths.get(getClass().getClassLoader().getResource(filePath).toURI()))
					.filter(string -> !string.isEmpty() && !string.equalsIgnoreCase("")).collect(Collectors.toList()));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		return strings;
	}

	public static void main(String[] args) {
		List<String> strings = new ArrayList<>();
		strings.add("");
		System.out.println(strings.contains(""));
	}
}
