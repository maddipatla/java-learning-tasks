package com.learningbydoing.autosuggest;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AutoSuggest {

	private static final Logger logger = LogManager.getLogger(AutoSuggest.class);

	public AutoSuggest() {
		super();
		new FilesProcessor().process();
	}

	public List<String> searchWordsWithPrefix(String prefixWord) {
		List<String> matches = Trie.getInstance().search(prefixWord);
		if (matches.isEmpty()) {
			logger.info("No matches found the given prefix!");
		} else {
			logger.info("Matched strings list: {}", matches);
		}
		return matches;
	}

	public static void addWordToTrie(String word) {
		Trie.getInstance().addWord(word);
	}

}
