package com.hichao.nlp.analysis.corn;

import org.junit.Assert;
import org.junit.Test;

import com.hichao.analysis.corn.Lexeme;
import com.hichao.analysis.corn.LexemeType;

public class TestLexeme {
	
	@Test
	public void testLexemeCompare() {
		
		Lexeme a = null;
		Lexeme b = null;
		
		a = Lexeme.create(0, 1, LexemeType.CHAR);
		b = Lexeme.create(1, 1, LexemeType.CHAR);
		Assert.assertTrue(a.compareTo(b) < 0);
		
		a = Lexeme.create(0, 2, LexemeType.CHAR);
		b = Lexeme.create(0, 1, LexemeType.CHAR);
		Assert.assertTrue(a.compareTo(b) < 0);
		
		a = Lexeme.create(0, 1, LexemeType.DIC_WORD);
		b = Lexeme.create(0, 1, LexemeType.CHAR);
		Assert.assertTrue(a.compareTo(b) > 0);
	}
}
