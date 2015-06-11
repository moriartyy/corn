package com.hichao.lucene;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;

import com.hichao.analysis.corn.artiber.Strategy;
import com.hichao.analysis.corn.dic.Dictionary;

public final class CornAnalyzer extends Analyzer{
	
	private Strategy strategy;
	private Dictionary dictionary;

	public CornAnalyzer(Dictionary dictionary, Strategy strategy){
		this.strategy = strategy;
		this.dictionary = dictionary;
	}

	@Override
	protected TokenStreamComponents createComponents(String fieldName, final Reader in) {
		Tokenizer _IKTokenizer = new CornTokenizer(in , dictionary, strategy);
		return new TokenStreamComponents(_IKTokenizer);
	}

}
