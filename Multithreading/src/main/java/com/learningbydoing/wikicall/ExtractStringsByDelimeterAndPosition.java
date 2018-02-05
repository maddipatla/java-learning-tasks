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

public class ExtractStringsByDelimeterAndPosition implements Callable<List<String>> {
	static final Logger logger = LogManager.getLogger(ExtractStringsByDelimeterAndPosition.class.getName());
	private String filePath;
	private String delimeter;
	private Integer positionOfString;

	public ExtractStringsByDelimeterAndPosition(String filePath, String delimeter, Integer positionOfString) {
		this.filePath = filePath;
		this.delimeter = delimeter;
		this.positionOfString = positionOfString;
	}

	@Override
	public List<String> call() throws Exception {
		List<String> strings = new ArrayList<>();
		try (Stream<String> stream = Files
				.lines(Paths.get(getClass().getClassLoader().getResource(filePath).toURI()))) {
			strings.addAll(stream.map(string -> string.split(delimeter)).map(array -> array[positionOfString - 1])
					.filter(string -> !string.isEmpty() && !string.equalsIgnoreCase("")).collect(Collectors.toList()));
		} catch (IOException | URISyntaxException e) {
			logger.warn("Exception in ExtractStringsByDelimeterAndPosition.call(): {}", e.getMessage());
		}
		return strings;
	}
}
