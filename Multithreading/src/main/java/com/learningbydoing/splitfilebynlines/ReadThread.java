package com.learningbydoing.splitfilebynlines;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReadThread implements Runnable {
	static final Logger logger = LogManager.getLogger(ReadThread.class.getName());

	static BlockingQueue<String> queue = new LinkedBlockingQueue<>();
	private Path filePath;
	public static boolean isAllLinesQueued = false;

	public ReadThread(Path filePath) {
		this.filePath = filePath;
	}

	@Override
	public void run() {
		try (Stream<String> stream = Files.lines(filePath)) {
			stream.forEach(s -> queue.add(s));
		} catch (IOException e) {
			e.printStackTrace();
		}
		isAllLinesQueued = true;
	}

}
