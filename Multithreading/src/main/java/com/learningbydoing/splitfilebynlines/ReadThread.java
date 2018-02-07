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

	/**
	 * Read file line by line using Stream and put it in the queue which will be
	 * taken by the WriteThread in WriteThrad.java
	 */
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
	 * This flag is used to exit from the loop in WriteThread.java, which is keep on
	 * waiting for the items in the queue.
	 * 
	 * @param isAllLinesQueued
	 */
	public static void setAllLinesQueued(boolean isAllLinesQueued) {
		ReadThread.isAllLinesQueued = isAllLinesQueued;
	}

	/**
	 * Number of rows in input file is required to determine when to exit from the
	 * loop in WriteThread.java
	 * 
	 * @param noOfRowsInFile
	 */
	public static void setNoOfRowsInFile(Long noOfRowsInFile) {
		ReadThread.noOfRowsInFile = noOfRowsInFile;
	}

	public static Long getNoOfRowsInFile() {
		return noOfRowsInFile;
	}

}
