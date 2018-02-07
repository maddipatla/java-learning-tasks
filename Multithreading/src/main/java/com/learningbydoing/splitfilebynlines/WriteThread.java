package com.learningbydoing.splitfilebynlines;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Maddipatla Chandra Babu
 * 
 * @date 07-Feb-2018
 */
public class WriteThread implements Runnable {
	static final Logger logger = LogManager.getLogger(WriteThread.class.getName());

	private Long numberOfLines;
	private Long counter = 1L;
	private Long fileCounter = 1L;
	private Path outputFilePath;

	/**
	 * @param numberOfLines
	 * @param outputFilePath
	 */
	public WriteThread(Long numberOfLines, Path outputFilePath) {
		this.numberOfLines = numberOfLines;
		this.outputFilePath = outputFilePath;
	}

	/**
	 * Create a list of strings from the queue, each string represents one line in
	 * input file. Once list reaches the file split number it create a file with the
	 * name as File-<file number in the increment order, starts from 1> and list
	 * will be written to the file. loop repeats until exit condition met.
	 */
	@Override
	public void run() {
		try {
			do {
				List<String> strings = new ArrayList<>();
				while (numberOfLines >= counter) {
					if (ReadThread.isAllLinesQueued() && (ReadThread.getNoOfRowsInFile() / numberOfLines) <= fileCounter
							&& ReadThread.queue.isEmpty())
						break;
					strings.add(ReadThread.queue.take());
					counter++;
				}
				counter = 1L;
				Files.write(Paths.get(outputFilePath + File.separator + "File-" + fileCounter++), strings,
						StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
				strings.clear();
			} while (!ReadThread.queue.isEmpty());
		} catch (InterruptedException | IOException e) {
			logger.warn("Exception in WriteThread.run(): {}", e.getMessage());
		}

	}

}
