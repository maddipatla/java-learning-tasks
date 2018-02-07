package com.learningbydoing.splitfilebynlines;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Maddipatla Chandra Babu
 * 
 * @date 07-Feb-2018
 */
public class ReadThread implements Runnable {
	static final Logger logger = LogManager.getLogger(ReadThread.class.getName());

	static BlockingQueue<String> queue = new LinkedBlockingQueue<>();
	private Path filePath;
	private static boolean isAllLinesQueued = false;
	private static Long noOfRowsInFile = 0L;

	/**
	 * @param filePath
	 */
	public ReadThread(Path filePath) {
		this.filePath = filePath;
	}

	@Override
	public void run() {
		try (Stream<String> stream = Files.lines(filePath)) {
			stream.forEach(s -> {
				queue.add(s);
				setNoOfRowsInFile(getNoOfRowsInFile() + 1);
			});

		} catch (IOException e) {
			logger.warn("Exception in ReadThread.run(): {}", e.getMessage());
		}
		setAllLinesQueued(true);
	}

	public static boolean isAllLinesQueued() {
		return isAllLinesQueued;
	}

	/**
	 * @param isAllLinesQueued
	 */
	public static void setAllLinesQueued(boolean isAllLinesQueued) {
		ReadThread.isAllLinesQueued = isAllLinesQueued;
	}

	/**
	 * @param noOfRowsInFile
	 */
	public static void setNoOfRowsInFile(Long noOfRowsInFile) {
		ReadThread.noOfRowsInFile = noOfRowsInFile;
	}

	public static Long getNoOfRowsInFile() {
		return noOfRowsInFile;
	}

}
