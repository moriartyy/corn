package com.hichao.lucene;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;

import com.hichao.analysis.corn.Lexeme;
import com.hichao.analysis.corn.Segmenter;
import com.hichao.analysis.corn.artiber.Strategy;
import com.hichao.analysis.corn.dic.Dictionary;

public final class CornTokenizer extends Tokenizer {
	
	private Segmenter segmenter;
	
	private final CharTermAttribute termAtt;
	private final OffsetAttribute offsetAtt;
	private final TypeAttribute typeAtt;
	private int endPosition;
   	private PositionIncrementAttribute posIncrAtt;

	public CornTokenizer(Reader in , Dictionary dictionary, Strategy strategy) {
	    super(in);
	    offsetAtt = addAttribute(OffsetAttribute.class);
	    termAtt = addAttribute(CharTermAttribute.class);
	    typeAtt = addAttribute(TypeAttribute.class);
        posIncrAtt = addAttribute(PositionIncrementAttribute.class);

        segmenter = new Segmenter(dictionary, strategy);
        segmenter.setTarget(in);
	}

	@Override
	public boolean incrementToken() throws IOException {
		clearAttributes();
        Lexeme nextLexeme = segmenter.next();
		if(nextLexeme != null){
            posIncrAtt.setPositionIncrement(1);
			termAtt.append(nextLexeme.text());
			termAtt.setLength(nextLexeme.length());
			int endpos = correctOffset(nextLexeme.offset() + nextLexeme.length());
            offsetAtt.setOffset(correctOffset(nextLexeme.offset()), endpos);
			endPosition = endpos;
			typeAtt.setType(nextLexeme.typeAsString());			
			return true;
		}
		return false;
	}
	
	@Override
	public void reset() throws IOException {
		super.reset();
		segmenter.setTarget(input);
	}	
	
	@Override
	public final void end() throws IOException {
        super.end();
		int finalOffset = correctOffset(this.endPosition);
		offsetAtt.setOffset(finalOffset, finalOffset);
        posIncrAtt.setPositionIncrement(posIncrAtt.getPositionIncrement());
	}
}
