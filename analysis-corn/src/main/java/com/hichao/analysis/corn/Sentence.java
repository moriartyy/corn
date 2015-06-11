package com.hichao.analysis.corn;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.hichao.analysis.corn.dic.Word;

/*
 * 
 * pos: positon 特指相对reader的偏移
 * offset：相对sentence的偏移 
 */
public class Sentence {

	public final static int BUFFER_SIZE = 1024;
	
	protected char[] charBuffer = new char[BUFFER_SIZE];
	protected byte[] charTypeBuffer = new byte[BUFFER_SIZE];
	private int limit;
	
	private int currsor;

	// Position in reader.
	private int position = 0;
	
	private final Set<Integer> lexemeHeads = new HashSet<Integer>(100);
	private final Set<Integer> lexemeTails =  new HashSet<Integer>(100);
	private final Map<Integer, LexemeSet> lexemes = new HashMap<Integer, LexemeSet>(100);
	private final LinkedList<Lexeme> outputLexemes = new LinkedList<Lexeme>();

	private byte currentCharType;

	private char currentChar;
	
	public void reset() {
		this.lexemeHeads.clear();
		this.lexemeTails.clear();
		this.lexemes.clear();
		this.outputLexemes.clear();
		this.currsor = -1;
		this.limit = 0;
		this.position = 0;
	}
	
	public void addLexeme(int start, int length, LexemeType type) {
		addLexeme(Lexeme.create(start + this.position, length, type));
	}

	public void addLexeme(Word word, int start, int length, LexemeType type) {
		addLexeme(Lexeme.create(start + this.position, length, type, word));
	}
	
	public void addLexeme(Lexeme lexeme) {
		LexemeSet set = lexemes.get(lexeme.offset());
		if (set == null) {
			set = new LexemeSet(10);
			lexemes.put(lexeme.offset(), set);
		}
		set.add(lexeme);
		this.lexemeHeads.add(lexeme.offset());
		this.lexemeTails.add(lexeme.offset() + lexeme.length() - 1);
		for (int i=lexeme.offset() - 1; i>=position; i--) {
			if (lexemeTails.contains(i)) {
				break;
			} else {
				LexemeType type = charTypeAt(i-position) == CharacterType.USELESS ? LexemeType.USELESS_CHAR : LexemeType.CHAR;
				addLexeme(Lexeme.create(i, 1, type));
			}
		}
	}

	public char[] buffer() {
		return charBuffer;
	}

	public int limit() {
		return limit;
	}

	public int currsor() {
		return currsor;
	}

	public int position() {
		return position;
	}

	public Set<Integer> lexemeHeads() {
		return lexemeHeads;
	}

	public Set<Integer> lexemeTails() {
		return lexemeTails;
	}
	
	public boolean containsLexemeStartAt(int pos) {
		return lexemeHeads.contains(pos);
	}
	
	public boolean containsLexemeEndAt(int pos) {
		return lexemeTails.contains(pos);
	}

	public Map<Integer, LexemeSet> lexemes() {
		return lexemes;
	}

	public void addLexemeHead(int pos) {
		this.lexemeHeads.add(pos);
	}
	
	public void addLexemeTail(int pos) {
		this.lexemeTails.add(pos);
	}

	public LexemeSet getLexemes(int pos) {
		return this.lexemes.get(pos);
	}

	public byte charTypeAt(int offset) {
		return charTypeBuffer[offset];
	}
	
	public char charAt(int offset) {
		return charBuffer[offset];
	}

	public void limit(int limit) {
		this.limit = limit;
	}

	public void position(int pos) {
		this.position = pos;
	}

	public boolean advanceCursor() {
		this.currsor++;
		if (currsor == limit) {
			return false;
		} else {
			currentCharType = charTypeBuffer[currsor];
			currentChar = charBuffer[currsor];
			return true;
		}
	}

	public char currentChar() {
		return currentChar;
	}
	
	public byte currentCharType() {
		return currentCharType;
	}

	public byte charTypeAtPos(int pos) {
		return charTypeAt(pos - position);
	}

	public void addOutputLexeme(Lexeme lexeme) {
		
		if (lexeme.type() == LexemeType.USELESS_CHAR)
			return;
		
		if (lexeme.text() == null) {
			lexeme.setText(new String(this.charBuffer, lexeme.offset() - position, lexeme.length()));
		}
		outputLexemes.add(lexeme);
	}

	public Queue<Lexeme> outputLexemes() {
		return outputLexemes;
	}

	public void sortOutputLexemes() {
		Collections.sort(outputLexemes);
	}
}
