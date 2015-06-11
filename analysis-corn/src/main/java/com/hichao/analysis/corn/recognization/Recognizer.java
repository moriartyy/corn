package com.hichao.analysis.corn.recognization;

import com.hichao.analysis.corn.Sentence;

public interface Recognizer {
    
	void analyze(Sentence sentence);
	void endAnalyzeAndReset(Sentence sentence);
}
