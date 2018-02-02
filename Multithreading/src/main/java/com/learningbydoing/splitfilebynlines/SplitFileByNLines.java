package com.learningbydoing.splitfilebynlines;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SplitFileByNLines {
	final static Logger logger = LogManager.getLogger(SplitFileByNLines.class.getName());

	private String filePath;
	private Long splitNumber;
	private String outputFilePath;

	public SplitFileByNLines(String filePath, Long splitNumber, String outputFilePath) {
		this.filePath = filePath;
		this.splitNumber = splitNumber;
		this.outputFilePath = outputFilePath;
	}

	public void split() {
		Long startTime = System.currentTimeMillis();
		ExecutorService service = Executors.newFixedThreadPool(2);
		service.execute(new ReadThread(filePath));
		service.execute(new WriteThread(splitNumber, outputFilePath));
		service.shutdown();
		while (!service.isTerminated())
			;
		logger.info("Time taken is: {} milliseconds.", System.currentTimeMillis() - startTime);
	}

	public static void main(String[] args) {
		new SplitFileByNLines(null, 1000L, "/home/chandrab/tmp/task1").split();
	}
}
