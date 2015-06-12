package com.hichao.nlp.analysis.corn;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hichao.analysis.corn.dic.Word;
import com.hichao.analysis.corn.dic.WordSource;

public class MockWordSource implements WordSource {

	private Iterator<Word> wordIterator;
	
	public MockWordSource() {
		this.wordIterator = prepareWords().iterator();
	}
	
	private List<Word> prepareWords() {
		List<Word> words = new ArrayList<Word>();
		words.add(Word.create("hello"));
		return words;
	}

	@Override
	public void close() {
		
	}

	@Override
	public Word next() {
		return wordIterator.next();
	}
}
