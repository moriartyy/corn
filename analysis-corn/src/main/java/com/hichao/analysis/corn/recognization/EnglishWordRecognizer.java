package com.hichao.analysis.corn.recognization;

import com.hichao.analysis.corn.CharacterType;
import com.hichao.analysis.corn.LexemeType;
import com.hichao.analysis.corn.Sentence;

public class EnglishWordRecognizer implements Recognizer {

    private int start = -1;
    private boolean marked = false;
    
	@Override
	public void analyze(Sentence sentence) {
        if (sentence.currentCharType() == CharacterType.ENGLISH_LETTER) {
            if (!marked) {
                this.start = sentence.currsor();
                marked = true;
            }
        } else {
            if (marked) {
            	sentence.addLexeme(start, sentence.currsor() - start, LexemeType.ENGLISH_WORD);
            	marked = false;
            }
        }
	}

	@Override
	public void endAnalyzeAndReset(Sentence sentence) {
		if (marked) {
			sentence.addLexeme(start, sentence.currsor() - start, LexemeType.ENGLISH_WORD);
		}
		marked = false;
	}
}
