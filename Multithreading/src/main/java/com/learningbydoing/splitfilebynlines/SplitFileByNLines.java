package com.learningbydoing.splitfilebynlines;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SplitFileByNLines {
	final static Logger logger = LogManager.getLogger(SplitFileByNLines.class.getName());

	final String DEFAULT_FILE = "Multithreading_Task1_Books.csv";

	private Path filePath;
	private Long splitNumber;
	private Path outputFilePath;

	public SplitFileByNLines() {
		try {
			this.filePath = Paths.get(getClass().getClassLoader().getResource(DEFAULT_FILE).toURI());
			StringBuilder builder = new StringBuilder(System.getProperty("user.dir"));
			builder.append(File.separator);
			builder.append("task1");
			builder.append(File.separator);
			this.outputFilePath = Paths.get(builder.toString());
			Files.createDirectory(this.outputFilePath);
			this.splitNumber = 1000L;
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
	}

	public SplitFileByNLines(String filePath, Long splitNumber, String outputFilePath) {
		this.splitNumber = splitNumber;
		if (filePath != null && outputFilePath != null) {
			if (FilenameUtils.getExtension(filePath).isEmpty())
				throw new RuntimeException("You must specify file extension");
			this.filePath = Paths.get(filePath);
			this.outputFilePath = Paths.get(outputFilePath);
			if ((!Files.exists(this.filePath) && !Files.isRegularFile(this.filePath))
					|| (!Files.exists(this.outputFilePath) && !Files.isDirectory(this.outputFilePath)))
				throw new RuntimeException("File path or output file directory doesn't exist");

		} else
			throw new RuntimeException("File path or output file directory can't be null");
		// if (Files.exists(this.filePath) && Files.exists(this.outputFilePath))
		// throw new RuntimeException("File Path doesn't exist. Please make sure file
		// path exists");
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
		new SplitFileByNLines(
				"/home/chandrab/Pramati/Workspace/Java/Learning-Java-By-Doing/Java-Learning-Tasks/Multithreading/src/main/resources/Multithreading_Task1_Books.csv",
				1000L, "/home/chandrab/tmp/task1").split();
	}
}
