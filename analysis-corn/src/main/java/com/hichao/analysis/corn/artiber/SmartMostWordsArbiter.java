package com.hichao.analysis.corn.artiber;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hichao.analysis.corn.CharacterType;
import com.hichao.analysis.corn.Lexeme;
import com.hichao.analysis.corn.LexemePath;
import com.hichao.analysis.corn.LexemeSet;
import com.hichao.analysis.corn.LexemeType;
import com.hichao.analysis.corn.Sentence;

public class SmartMostWordsArbiter implements Arbiter {
	
	private List<LexemePath> paths = new ArrayList<LexemePath>(100);
	private List<LexemePath> generatedPaths = new ArrayList<LexemePath>(100);

	public void process(Sentence sentence) {

		paths.clear();
		paths.add(new LexemePath(sentence.position()));
		int stopPos = sentence.position() + 1;
		int stopPosLimit = sentence.position() + sentence.limit();
		boolean allPathEndAtSamePos = false;
		while (stopPos <= stopPosLimit) {
			generatedPaths.clear();
			for (LexemePath path : paths) {
				generatePaths(path, stopPos, sentence, generatedPaths);
			}
			int tempEndPos = 0;
			int maxEndPos = 0;
			paths.clear();
			allPathEndAtSamePos = true;
			for (LexemePath generateedPath : generatedPaths) {
				if (tempEndPos > 0) {
					allPathEndAtSamePos &= (tempEndPos == generateedPath.end());
				} else {
					tempEndPos = generateedPath.end();
				}
				paths.add(generateedPath);
				if (generateedPath.end() > maxEndPos) {
					maxEndPos = generateedPath.end();
				}
			}
			if (allPathEndAtSamePos) {
				outputTokens(paths, sentence);
				paths.clear();
				paths.add(new LexemePath(maxEndPos));
				stopPos = maxEndPos + 1;
			} else {
				stopPos = maxEndPos;
			}
		}
	}
	
    private void outputTokens(List<LexemePath> paths, Sentence sentence) {
        LexemePath path = selectPath(paths);
        Iterator<Lexeme> iterator = path.iterator();
        while (iterator.hasNext()) {
            Lexeme lexeme = iterator.next();
            if (lexeme.type() != LexemeType.USELESS_CHAR) {
            	sentence.addOutputLexeme(lexeme);
            }
        }
    }
    
    private LexemePath selectPath(List<LexemePath> pathes) {
        LexemePath selected = null;
        for (LexemePath tokenPath : pathes) {
            if (selected == null) {
                selected = tokenPath;
            } else {
                int comp = tokenPath.compareTo(selected);
                if (comp == 1) {
                    selected = tokenPath;
                } else if (comp == 0) {
                }
            }
        }
        return selected;
    }
	
	private void generatePaths(LexemePath path, int stopPos, Sentence sentence, List<LexemePath> paths) {
		
		if (path.end() >= stopPos) {
			paths.add(path);
			return;
		}
		
		LexemeSet lexemes = sentence.getLexemes(path.end());
		if (lexemes == null) {
			LexemeType type = sentence.charTypeAtPos(path.end()) == CharacterType.USELESS ? LexemeType.USELESS_CHAR : LexemeType.CHAR;
			path.appendLexeme(Lexeme.create(path.end(), 1, type));
			generatePaths(path, stopPos, sentence, paths);
		} else {
			for (Lexeme lexeme : lexemes) {
				generatePaths(new LexemePath(path, lexeme), stopPos, sentence, paths);
			}
		}
	}

	

}
