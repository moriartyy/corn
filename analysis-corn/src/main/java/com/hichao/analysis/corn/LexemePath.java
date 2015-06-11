package com.hichao.analysis.corn;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.hichao.analysis.corn.Lexeme;
import com.hichao.analysis.corn.LexemeType;
import com.hichao.analysis.corn.dic.attribute.EntityAttribute;

public class LexemePath implements Comparable<LexemePath> {

    private int start;
    private int length;
    private List<Lexeme> lexemes = new LinkedList<Lexeme>();
    private int lexemeNumber;
    private int singleCharWordNumber;
    private int multiCharDicWordNumber;
    private int sumOfPos;
	private int entityNumber;
	
	public LexemePath(LexemePath path, Lexeme lexeme) {
        this.lexemes.clear();
        this.lexemes.addAll(path.lexemes);
        this.start = path.start;
        this.length = path.length;
        this.lexemeNumber = path.lexemeNumber;
        this.singleCharWordNumber = path.singleCharWordNumber;
        this.multiCharDicWordNumber = path.multiCharDicWordNumber;
        this.sumOfPos = path.sumOfPos;
        this.entityNumber = path.entityNumber;
        appendLexeme(lexeme);
	}

	public LexemePath(int pos) {
        this.start = pos;
        this.length = 0;
	}
	
	public int start() {
		return start;
	}
	
	public int length() {
		return length;
	}
	
	public int end() {
		return start + length;
	}

	@Override
	public int compareTo(LexemePath o) {
        if (this == o)
            return 0;
        
        if (o == null)
            return 1;
        
        if (this.entityNumber > o.entityNumber) {
        	if (this.singleCharWordNumber <= o.singleCharWordNumber) {
        		return 1;
        	} else {
        		return -1;
        	}
        } else if (this.entityNumber < o.entityNumber) {
        	if (this.singleCharWordNumber >= o.singleCharWordNumber) {
        		return  -1;
        	} else {
        		return 1;
        	}
        } else if (this.entityNumber > 0 && this.entityNumber == o.entityNumber) {
        	if (this.singleCharWordNumber < o.singleCharWordNumber) {
        		return 1;
        	} else if (this.singleCharWordNumber > o.singleCharWordNumber) {
        		return -1;
        	}
        }
        
        if (this.multiCharDicWordNumber > o.multiCharDicWordNumber) {
            return 1;
        } else if (this.multiCharDicWordNumber == o.multiCharDicWordNumber) {
            if (this.singleCharWordNumber < o.singleCharWordNumber) {
                return 1;
            } else if (this.singleCharWordNumber == o.singleCharWordNumber) {
                return Integer.compare(o.sumOfPos, this.sumOfPos); //后向匹配
            } else {
                return -1;
            }
        } else {
            return -1;
        }
	}

	public void appendLexeme(Lexeme lexeme) {
        this.lexemes.add(lexeme);
        EntityAttribute entityAttribute = lexeme.getAttribute(EntityAttribute.class);
        if (entityAttribute != null) {
        	this.entityNumber += entityAttribute.count();
        }
        if (lexeme.length() == 1) {
            this.singleCharWordNumber++;
        } else if (lexeme.type() == LexemeType.DIC_WORD) {
            this.multiCharDicWordNumber++;
        }
        this.lexemeNumber++;
        this.sumOfPos += lexeme.offset();
        this.length += lexeme.length();
	}

	public Iterator<Lexeme> iterator() {
		return this.lexemes.iterator();
	}
	
    @Override
    public String toString() {
        return String.format("[%d, %d]", this.start, this.start + this.length);
    }
}
