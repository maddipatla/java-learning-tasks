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

import com.learningbydoing.exception.NoFileExtensionException;
import com.learningbydoing.exception.NoPathExistException;
import com.learningbydoing.exception.NoWritePermissionsException;

/**
 * @author Maddipatla Chandra Babu
 * 
 * @date 07-Feb-2018
 */
public class SplitFileByNLines {
	static final Logger logger = LogManager.getLogger(SplitFileByNLines.class.getName());

	private static final String DEFAULT_FILE = "Multithreading_Task1_Books.csv";

	private Path filePath;
	private Long splitNumber;
	private Path outputFilePath;

	/**
	 * FilePath is classpath file, which is Multithreading_Task1_Books.csv.
	 * <p>
	 * SplitNumber is 1000
	 * <p>
	 * outputFilePath is user directory, under which task1 directory will be created
	 * in which all the files will be written.
	 */
	public SplitFileByNLines() {
		try {
			this.filePath = Paths.get(getClass().getClassLoader().getResource(DEFAULT_FILE).toURI());
			StringBuilder builder = new StringBuilder(System.getProperty("user.dir"));
			builder.append(File.separator);
			builder.append("task1");
			builder.append(File.separator);
			this.outputFilePath = Paths.get(builder.toString());
			if (!Files.isWritable(this.outputFilePath))
				throw new NoWritePermissionsException(
						"Don't have write permissions to the default location. You many need to use overloaded constructor to specify custom path to writes");
			Files.createDirectory(this.outputFilePath);
			this.splitNumber = 1000L;
		} catch (URISyntaxException | IOException e) {
			logger.warn("Exception in SplitFileByNLines(): {}", e.getMessage());
		}
	}

	/**
	 * @param filePath
	 * @param splitNumber
	 * @param outputFilePath
	 */
	public SplitFileByNLines(String filePath, Long splitNumber, String outputFilePath) {
		this.splitNumber = splitNumber;
		if (filePath != null && outputFilePath != null) {
			if (FilenameUtils.getExtension(filePath).isEmpty())
				throw new NoFileExtensionException("You must specify file extension");
			this.filePath = Paths.get(filePath);
			this.outputFilePath = Paths.get(outputFilePath);
			if ((!this.filePath.toFile().exists() && !this.filePath.toFile().isFile())
					|| (!this.outputFilePath.toFile().exists() && !this.outputFilePath.toFile().isDirectory()))
				throw new NoPathExistException("File path or output file directory doesn't exist");

		} else
			throw new NoPathExistException("File path or output file directory can't be null");
	}

	public void split() {
		Long startTime = System.currentTimeMillis();
		ExecutorService service = Executors.newFixedThreadPool(2);
		service.execute(new ReadThread(filePath));
		service.execute(new WriteThread(splitNumber, outputFilePath));
		service.shutdown();
		while (!service.isTerminated()) {
			logger.info("Waiting for all threads to be finished with their work and executorService is shutdown");
		}
		logger.info("Time taken is: {} milliseconds.", System.currentTimeMillis() - startTime);
	}
}
