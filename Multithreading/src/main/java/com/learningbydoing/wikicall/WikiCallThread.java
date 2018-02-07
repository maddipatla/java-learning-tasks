package com.learningbydoing.wikicall;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;

public class WikiCallThread implements Runnable {

	static final Logger logger = LogManager.getLogger(WikiCallThread.class.getName());
	private String wikiString;
	private Path outputFilePath;
	private String wikiURLString;

	public WikiCallThread(String wikiString, String wikiURLString, Path outputFilePath) {
		this.wikiString = wikiString;
		this.wikiURLString = wikiURLString;
		this.outputFilePath = outputFilePath;
	}

	public void run() {
		HttpsURLConnection connection = getHttpsURLConnection();
		List<String> list = getTitleDescriptionFromJsonString(getJsonStringBySendingRequest(connection));
		writeToFile(Objects.requireNonNull(list));
		connection.disconnect();
	}

	public String getJsonStringBySendingRequest(HttpsURLConnection connection) {
		StringBuilder builder = new StringBuilder();
		try {
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/json");
			if (connection.getResponseCode() != 200) {
				throw new RuntimeException("Failed: HTTP Error code: " + connection.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String output;
			while ((output = br.readLine()) != null) {
				builder.append(output);
			}
		} catch (IOException e) {
			logger.warn("Exception in WikiCallThread.getJsonStringBySendingRequest(HttpsURLConnection connection): {}",
					e.getMessage());
		}
		return builder.toString();
	}

	public HttpsURLConnection getHttpsURLConnection() {
		HttpsURLConnection connection = null;
		try {
			String queryString = URLEncoder.encode(wikiString, "UTF-8");
			URL url = new URL(wikiURLString + queryString);
			connection = (HttpsURLConnection) url.openConnection();
		} catch (IOException e) {
			logger.warn("Exception in WikiCallThread.getHttpsURLConnection(): {}", e.getMessage());
		}
		return connection;
	}

	private void writeToFile(List<String> list) {
		if (!list.isEmpty()) {
			String title = list.remove(0);
			try {
				Files.write(Paths.get(outputFilePath + File.separator + title), list, StandardCharsets.UTF_8,
						StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			} catch (IOException e) {
				logger.warn("Exception in WikiCallThread.writeToFile(): {}", e.getMessage());
			}
		}
	}

	private List<String> getTitleDescriptionFromJsonString(String jsonString) {
		Any obj = JsonIterator.deserialize(jsonString);
		Map<String, Any> map = obj.asMap().get("query").asMap().get("pages").asMap();
		List<String> list = new ArrayList<>(map.keySet());
		Map<String, Any> descriptionMap = map.get(list.get(0)).asMap();
		Any description = descriptionMap.get("extract");
		List<String> titleDescription = null;
		if (description != null) {
			titleDescription = new ArrayList<>(2);
			titleDescription.add(descriptionMap.get("title").toString().replaceAll("[^a-zA-Z0-9\\s]", "").trim());
			titleDescription.add(description.toString());
		} else
			logger.warn("don't find description for the given word: {}", wikiString);
		return titleDescription;
	}

}
