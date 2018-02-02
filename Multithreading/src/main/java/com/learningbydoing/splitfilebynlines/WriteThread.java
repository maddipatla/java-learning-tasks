package com.learningbydoing.splitfilebynlines;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class WriteThread implements Runnable {
	private Long numberOfLines;
	private Long counter = 1L;
	private Long fileCounter = 1L;
	private String outputFilePath;

	public WriteThread(Long numberOfLines, String outputFilePath) {
		this.numberOfLines = numberOfLines;
		this.outputFilePath = outputFilePath;
	}

	@Override
	public void run() {
		try {
			do {
				List<String> strings = new ArrayList<>();
				while (numberOfLines >= counter) {
					if (ReadThread.queue.isEmpty() && ReadThread.isAllLinesQueued)
						break;
					strings.add(ReadThread.queue.take());
					counter++;
				}
				counter = 1L;
				Files.write(Paths.get(outputFilePath + "/File-" + fileCounter++), strings, StandardCharsets.UTF_8,
						StandardOpenOption.CREATE, StandardOpenOption.APPEND);
				strings.clear();
			} while (!ReadThread.queue.isEmpty());
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}

	}

}
