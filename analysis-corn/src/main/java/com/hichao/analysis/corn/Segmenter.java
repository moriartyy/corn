package com.hichao.analysis.corn;

import java.io.IOException;
import java.io.Reader;

import com.hichao.analysis.corn.artiber.Arbiter;
import com.hichao.analysis.corn.artiber.ArbiterFactory;
import com.hichao.analysis.corn.artiber.Strategy;
import com.hichao.analysis.corn.dic.Dictionary;
import com.hichao.analysis.corn.recognization.CompositeRecognizer;
import com.hichao.analysis.corn.recognization.DicWordRecognizer;
import com.hichao.analysis.corn.recognization.EnglishWordRecognizer;
import com.hichao.analysis.corn.recognization.NumberRecognizer;
import com.hichao.analysis.corn.recognization.Recognizer;

public class Segmenter {
    
    private Dictionary dic;
    private CompositeRecognizer recognizer = new CompositeRecognizer();
    private Arbiter arbiter;
    private SentenceReader reader = new SentenceReader(null);

    public Segmenter(Dictionary dic, Strategy strategy) {
        this.dic = dic;
        this.arbiter = ArbiterFactory.sharedInstance.create(strategy);
        this.initRecognizers();
    }
    
    public void addRecognizer(Recognizer recognizer) {
    	this.recognizer.addRecognizer(recognizer);
    }
    
    private void initRecognizers() {
    	recognizer.addRecognizer(new EnglishWordRecognizer());
    	recognizer.addRecognizer(new NumberRecognizer());
    	recognizer.addRecognizer(new DicWordRecognizer(dic));
    }
    
    Dictionary dictionary() {
        return dic;
    }

    public void setTarget(Reader reader) {
        this.reader.reset(reader);;
    }
    
    public Lexeme next() throws IOException {
    	Sentence sentence = reader.sentence();
        Lexeme lexeme;
        while ((lexeme = sentence.outputLexemes().poll()) == null) {
            
        	if (!this.reader.read()) {
        		break;
        	}
        	
            while (sentence.advanceCursor()) {
            	this.recognizer.analyze(sentence);
            }
            
            this.recognizer.endAnalyzeAndReset(sentence);
            
            this.arbiter.process(sentence);
        }
        return lexeme;
    }

}
