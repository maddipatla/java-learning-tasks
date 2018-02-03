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

import javax.net.ssl.HttpsURLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;

public class WikiCallThread implements Runnable {

	static final Logger logger = LogManager.getLogger(WikiCallThread.class.getName());
	private String wikiString;
	private Path outputFilePath;

	public WikiCallThread(String wikiString, Path outputFilePath) {
		this.wikiString = wikiString;
		this.outputFilePath = outputFilePath;
	}

	public void run() {
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
				Files.write(Paths.get(outputFilePath + File.separator + title), list, StandardCharsets.UTF_8,
						StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			} else
				logger.warn("don't find description for the given word: {}", wikiString);
			connection.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
