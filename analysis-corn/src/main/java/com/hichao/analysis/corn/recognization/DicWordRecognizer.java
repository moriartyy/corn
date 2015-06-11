package com.hichao.analysis.corn.recognization;

import java.util.Iterator;
import java.util.LinkedList;

import com.hichao.analysis.corn.CharacterUtil;
import com.hichao.analysis.corn.LexemeType;
import com.hichao.analysis.corn.Sentence;
import com.hichao.analysis.corn.dic.Dictionary;
import com.hichao.analysis.corn.dic.Match;

public class DicWordRecognizer implements Recognizer {

    private Character currentChar;
    private Iterator<Match> iterator;
    private LinkedList<Match> prefixMatches = new LinkedList<Match>();
    private Match match = null;
	private Dictionary dictionary;

    public DicWordRecognizer(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

	@Override
	public void analyze(Sentence sentence) {
        currentChar = (char)CharacterUtil.normalize(sentence.currentChar());
        iterator = prefixMatches.iterator();
        while (iterator.hasNext()) {
            match = iterator.next();
            match = match.dicSegment().match(new Character((char)sentence.currentChar()), match);
            if (match.isFullMatch()) {
            	sentence.addLexeme(match.dicSegment().word(), match.start(), sentence.currsor() - match.start() + 1, LexemeType.DIC_WORD);
            }
            
            if (!match.isPrefixMatch()) { // Means full match or unmatch either we should remove it.
                iterator.remove();
            }
        }
        
        match = new Match(); // Create a new one.
        match.setStart(sentence.currsor());
        match = dictionary.mainSeg().match(currentChar, match);
        
        if (match.isFullMatch()) { // Mean single char word.
        	sentence.addLexeme(match.dicSegment().word(), match.start(), 1, LexemeType.DIC_WORD); 
        }
        
        if (match.isPrefixMatch()) {
            this.prefixMatches.add(match);
        }
	}

	@Override
	public void endAnalyzeAndReset(Sentence sentence) {
        this.prefixMatches.clear();
        this.currentChar = null;
        this.iterator = null;
        this.match = null;
	}
}
