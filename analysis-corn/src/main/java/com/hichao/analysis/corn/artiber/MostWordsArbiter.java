package com.hichao.analysis.corn.artiber;

import com.hichao.analysis.corn.CharacterType;
import com.hichao.analysis.corn.Lexeme;
import com.hichao.analysis.corn.LexemeSet;
import com.hichao.analysis.corn.LexemeType;
import com.hichao.analysis.corn.Sentence;

public class MostWordsArbiter implements Arbiter {

    public void process(Sentence sentence) {
    	for (LexemeSet lexemeSet : sentence.lexemes().values()) {
    		for (Lexeme lexeme : lexemeSet) {
    	   		for (int i=lexeme.offset() - 1; i>=sentence.position(); i--) {
        			if (sentence.containsLexemeEndAt(i)) {
        				break;
        			} else {
        				outputCharLexeme(i, sentence);
        			}
        		}
    	   		sentence.addOutputLexeme(lexeme);
        		for (int i=lexeme.offset() + lexeme.length(); i<sentence.position() + sentence.limit(); i++) {
        			if (sentence.containsLexemeStartAt(i)) {
        				break;
        			} else {
        				outputCharLexeme(i, sentence);
        			}
        		}
			}
		}
    	sentence.sortOutputLexemes();
    }
    
    private void outputCharLexeme(int pos, Sentence sentence) {
		sentence.addLexemeHead(pos);
		sentence.addLexemeTail(pos);
		LexemeType type = sentence.charTypeAtPos(pos) == CharacterType.USELESS ? LexemeType.USELESS_CHAR : LexemeType.CHAR;
		sentence.addOutputLexeme(Lexeme.create(pos, 1, type));
    }
}
