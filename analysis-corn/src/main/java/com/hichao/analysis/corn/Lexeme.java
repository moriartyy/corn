package com.hichao.analysis.corn;

import com.hichao.analysis.corn.dic.Word;
import com.hichao.analysis.corn.dic.attribute.Attribute;

public class Lexeme implements Comparable<Lexeme> {

    private String text;
    private final int offset;
    private final int length;
    private LexemeType type;
    private Word word = Word.Empty;
    
    public static Lexeme create(int offset, int length, LexemeType type) {
        return new Lexeme(offset, length, type);
    }
    
    public static Lexeme create(int offset, int length, LexemeType type, Word word) {
        Lexeme lexeme = new Lexeme(offset, length, type);
        lexeme.word = word;
        lexeme.text = word.text();
        return lexeme;
    }
    
    private Lexeme(int offset, int length, LexemeType type) {
        this.offset = offset;
        this.length = length;
        this.type = type;
    }
    
    public <T extends Attribute> T getAttribute(Class<T> clazz) {
        return word.getAttribute(clazz);
    }

    public String text() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int offset() {
        return offset;
    }

    public int length() {
        return length;
    }

    public LexemeType type() {
        return type;
    }

    public String typeAsString() {
        return type.name();
    }
    
    @Override
    public String toString() {
    	return String.format("[offset=%d, length=%d, type=%s]", offset, length, typeAsString());
    }
    
    @Override
    public boolean equals(Object o) {   
        if (o == null) {
        	return false;
        }
        
        if (this == o) {   
            return true;   
        }  
        
        if (Lexeme.class != o.getClass()) {
        	return false;
        }
        
        Lexeme t = (Lexeme)o;
        
        if (this.compareTo(t) == 0) {
        	return true;
        }
        
        return false;
    }   

	@Override
	public int compareTo(Lexeme o) {
		
		int delta = this.offset - o.offset;
		if (delta != 0) {
			return delta;
		} 
		delta = o.length - length;
		if (delta != 0) {
			return delta;
		}
		return o.type.compareTo(this.type);
	}
	
	@Override
	public int hashCode() {
		int hash = this.offset;
		hash = 31 * hash + length;
		return hash;
	}
}
