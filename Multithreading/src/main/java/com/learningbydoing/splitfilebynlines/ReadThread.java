package com.learningbydoing.splitfilebynlines;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReadThread implements Runnable {
	static final Logger logger = LogManager.getLogger(ReadThread.class.getName());

	final static String DEFAULT_FILE = "Multithreading_Task1_Books.csv";

	static BlockingQueue<String> queue = new LinkedBlockingQueue<>();
	private Path filePath;
	public static boolean isAllLinesQueued = false;

	public ReadThread(Path filePath) {
		this.filePath = filePath;
	}

	@Override
	public void run() {
		try {
			Stream<String> stream;
			if (filePath != null && Files.exists(filePath)) {
				stream = Files.lines(filePath);
			} else {
				stream = Files.lines(Paths.get(getClass().getClassLoader().getResource(DEFAULT_FILE).toURI()));
				logger.info(
						"filePath may null: {} OR doesn't exist. Please make sure you specify file name with extension. Taking default file: {}",
						filePath, DEFAULT_FILE);
			}
			stream.forEach(s -> queue.add(s));
			isAllLinesQueued = true;
		} catch (NoSuchFileException | NullPointerException npe) {
			npe.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

}
