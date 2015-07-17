package com.hichao.analysis.corn;

import java.io.IOException;
import java.io.StringReader;

import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.junit.Test;

import com.hichao.analysis.corn.artiber.Strategy;
import com.hichao.analysis.corn.dic.Dictionary;
import com.hichao.analysis.corn.dic.DictionaryFactoryImpl;

public class SegmenterTest {

	@Test
	public void testSegmenter() throws IOException {
		Settings settings = ImmutableSettings.EMPTY;
		Environment environment = new Environment(settings);
		Dictionary dictionary = DictionaryFactoryImpl.create(settings, environment);
		Segmenter segmenter = new Segmenter(dictionary, Strategy.MostWords);
		segmenter.setTarget(new StringReader("温碧泉"));
		Lexeme lexeme = null;
		while ((lexeme = segmenter.next()) != null) {
			System.out.print(lexeme.text());
			System.out.print("|");
		}
		System.out.println();
	}
}
