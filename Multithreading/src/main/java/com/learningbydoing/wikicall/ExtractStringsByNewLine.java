package com.learningbydoing.wikicall;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExtractStringsByNewLine implements Callable<List<String>> {
	static final Logger logger = LogManager.getLogger(ExtractStringsByNewLine.class.getName());

	private String filePath;

	public ExtractStringsByNewLine(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public List<String> call() throws Exception {
		List<String> strings = new ArrayList<>();
		try (Stream<String> stream = Files
				.lines(Paths.get(getClass().getClassLoader().getResource(filePath).toURI()))) {
			strings.addAll(stream.filter(string -> !string.isEmpty() && !string.equalsIgnoreCase(""))
					.collect(Collectors.toList()));
		} catch (IOException | URISyntaxException e) {
			logger.warn("Exception in ExtractStringsByNewLine.call(): {}", e.getMessage());
		}
		return strings;
	}
}
