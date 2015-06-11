package com.hichao.analysis.corn.recognization;

import java.util.LinkedHashSet;
import java.util.Set;

import com.hichao.analysis.corn.Sentence;

public class CompositeRecognizer implements Recognizer {
    
    private Set<Recognizer> recognizers = new LinkedHashSet<Recognizer>();
    
    public void addRecognizer(Recognizer recognizer) {
        this.recognizers.add(recognizer);
    }

    @Override
	public void analyze(Sentence sentence) {
        for (Recognizer recognizer : recognizers) {
            recognizer.analyze(sentence);
        }
	}

	public void endAnalyzeAndReset(Sentence sentence) {
        for (Recognizer recognizer : recognizers) {
            recognizer.endAnalyzeAndReset(sentence);
        }
	}
}
