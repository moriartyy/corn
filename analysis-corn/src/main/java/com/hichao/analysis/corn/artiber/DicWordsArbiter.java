package com.hichao.analysis.corn.artiber;

import com.hichao.analysis.corn.Lexeme;
import com.hichao.analysis.corn.LexemeSet;
import com.hichao.analysis.corn.LexemeType;
import com.hichao.analysis.corn.Sentence;


public class DicWordsArbiter implements Arbiter {

	@Override
	public void process(Sentence sentence) {
		for (LexemeSet lexemeSet : sentence.lexemes().values()) {
			for (Lexeme lexeme : lexemeSet) {
				if (lexeme.type() == LexemeType.DIC_WORD)
					sentence.addOutputLexeme(lexeme);
			}
		}
	}

}
