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

public class ExtractStringsByDelimeter implements Callable<List<String>> {

	private Path filePath;
	private String delimeter;

	public ExtractStringsByDelimeter(String filePath, String delimeter) {
		this.filePath = Paths.get(filePath);
		this.delimeter = delimeter;
	}

	@Override
	public List<String> call() throws Exception {
		List<String> strings = new ArrayList<>();
		try {
			strings.addAll(Files.lines(Paths.get(getClass().getClassLoader().getResource(filePath.toString()).toURI()))
					.map(string -> string.split(delimeter)).flatMap(x -> Arrays.stream(x))
					.filter(string -> !string.isEmpty() && !string.equalsIgnoreCase("")).collect(Collectors.toList()));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		return strings;
	}
}
