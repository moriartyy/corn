package com.hichao.nlp.analysis.corn;


import org.junit.Assert;
import org.junit.Test;

import com.hichao.analysis.corn.Lexeme;
import com.hichao.analysis.corn.LexemeSet;
import com.hichao.analysis.corn.LexemeType;

public class LexemeSetTests {

	@Test
	public void testLexemeSet() {
		Lexeme a = Lexeme.create(0, 0, LexemeType.CHAR);
		Lexeme b = Lexeme.create(0, 0, LexemeType.DIC_WORD);
		
		LexemeSet set = new LexemeSet(10);
		set.add(a);
		set.add(b);
		Assert.assertEquals(set.iterator().next().type(), LexemeType.DIC_WORD);
		
		set.clear();
		set.add(b);
		set.add(a);
		Assert.assertEquals(set.iterator().next().type(), LexemeType.DIC_WORD);
	}
}
