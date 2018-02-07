package com.learningbydoing.wikicall;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Maddipatla Chandra Babu
 * 
 * @date 07-Feb-2018
 */
public class ExtractStringsByDelimeter implements Callable<List<String>> {
	static final Logger logger = LogManager.getLogger(ExtractStringsByDelimeter.class.getName());
	private Path filePath;
	private String delimeter;

	/**
	 * @param filePath
	 * @param delimeter
	 */
	public ExtractStringsByDelimeter(String filePath, String delimeter) {
		this.filePath = Paths.get(filePath);
		this.delimeter = delimeter;
	}

	@Override
	public List<String> call() throws Exception {
		List<String> strings = new ArrayList<>();
		try (Stream<String> stream = Files
				.lines(Paths.get(getClass().getClassLoader().getResource(filePath.toString()).toURI()))) {
			strings.addAll(stream.map(string -> string.split(delimeter)).flatMap(Arrays::stream)
					.filter(string -> !string.isEmpty() && !string.equalsIgnoreCase("")).collect(Collectors.toList()));
		} catch (IOException | URISyntaxException e) {
			logger.error("Exception in ExtractStringsByDelimeter.call(): {}", e.getMessage());
		}
		return strings;
	}
}
