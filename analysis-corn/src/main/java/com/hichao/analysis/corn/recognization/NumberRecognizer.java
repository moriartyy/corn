package com.hichao.analysis.corn.recognization;

import com.hichao.analysis.corn.CharacterType;
import com.hichao.analysis.corn.LexemeType;
import com.hichao.analysis.corn.Sentence;

public class NumberRecognizer implements Recognizer {

    private int start = -1;
    private boolean marked = false;

	@Override
	public void analyze(Sentence sentence) {
        if (sentence.currentCharType() == CharacterType.ARABIC_NUMBER) {
            if (!marked) {
                this.start = sentence.currsor();
                marked = true;
            }
        } else {
            if (marked) {
            	sentence.addLexeme(start, sentence.currsor() - start, LexemeType.NUMBER);
            	marked = false;
            }
        }
	}

	@Override
	public void endAnalyzeAndReset(Sentence sentence) {
		if (marked) {
			sentence.addLexeme(start, sentence.currsor() - start, LexemeType.NUMBER);
		}
		marked = false;
	}
}
