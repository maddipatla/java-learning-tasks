package com.learningbydoing.wikicall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveAction;

import javax.net.ssl.HttpsURLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;

public class WikiCallUsingForkJoin extends RecursiveAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1717977980508872106L;

	static final Logger logger = LogManager.getLogger(WikiCallUsingForkJoin.class.getName());

	public static final Integer THRESHOLD = 1;
	List<String> strings = new ArrayList<>();
	static ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() * 20);

	public WikiCallUsingForkJoin(List<String> strings) {
		this.strings = strings;
	}

	public static void main(String[] args) {
		Long startTime = System.currentTimeMillis();
		List<String> list = new ArrayList<>();
		ExecutorService serviceToGetStrings = Executors.newFixedThreadPool(3);
		Future<List<String>> futureOne = serviceToGetStrings
				.submit(new ExtractStringsByNewLine(WikiCall.DEFAULT_FILE_TWO));
		try {
			list.addAll(futureOne.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		serviceToGetStrings.shutdown();
		while (!serviceToGetStrings.isTerminated())
			;
		pool.invoke(new WikiCallUsingForkJoin(list));
		logger.info("Time taken: {}", System.currentTimeMillis() - startTime);
	}

	@Override
	protected void compute() {
		if (strings.size() > THRESHOLD)
			ForkJoinTask.invokeAll(createSubtasks());
		run(strings.get(0));
	}

	private List<WikiCallUsingForkJoin> createSubtasks() {
		List<WikiCallUsingForkJoin> subtasks = new ArrayList<>();

		List<String> partOne = strings.subList(0, strings.size() / 2);
		List<String> partTwo = strings.subList(strings.size() / 2, strings.size());

		subtasks.add(new WikiCallUsingForkJoin(partOne));
		subtasks.add(new WikiCallUsingForkJoin(partTwo));

		return subtasks;
	}

	public void run(String wikiString) {
		try {
			String queryString = URLEncoder.encode(wikiString, "UTF-8");
			URL url = new URL(
					"https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles="
							+ queryString);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/json");
			if (connection.getResponseCode() != 200) {
				throw new RuntimeException("Failed: HTTP Error code: " + connection.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String output;
			StringBuilder builder = new StringBuilder();
			while ((output = br.readLine()) != null) {
				builder.append(output);
			}
			Any obj = JsonIterator.deserialize(builder.toString());
			Map<String, Any> map = obj.asMap().get("query").asMap().get("pages").asMap();
			List<String> list = new ArrayList<>(map.keySet());
			Map<String, Any> descriptionMap = map.get(list.get(0)).asMap();
			Any description = descriptionMap.get("extract");
			list.clear();
			if (description != null) {
				list.add(description.toString());
				String title = descriptionMap.get("title").toString().replaceAll("[^a-zA-Z0-9\\s]", "").trim();
				Files.write(Paths.get("task2/" + title), list, StandardCharsets.UTF_8, StandardOpenOption.CREATE,
						StandardOpenOption.APPEND);
			} else
				logger.warn("don't find description for the given word: {}", wikiString);
			connection.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
