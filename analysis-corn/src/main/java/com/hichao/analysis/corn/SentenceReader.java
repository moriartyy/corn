package com.hichao.analysis.corn;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;


public class SentenceReader {
	
	private Reader input;
	private char[] buffer = new char[1024];
	private int bufferCursor = 0;
	private int bufferLimit = 0;
	private final static int Push_Back_Buffer_Size = 2;
	private char[] pushBackBuffer = new char[Push_Back_Buffer_Size];
	private int pushBackCharNumber = 0;
	
	private static final char[] Sentence_Separator = new char[]{',' , '.', '!', '?', ';', '，', '。', '！', '？', '；'};

	public SentenceReader(Reader input) {
		Arrays.sort(Sentence_Separator);
		this.input = input;
	}
	
	public void reset(Reader input) {
		this.input = input;
		this.bufferCursor = 0;
		this.bufferLimit = 0;
		this.pushBackCharNumber = 0;
	}

	public boolean read() {
		sentence.reset();
		sentence.position(bufferCursor - pushBackCharNumber);

		int index = 0;
		int c = -1;
		byte cType = 0;
		
		while ((c = readPushBackChar()) > -1) {  // Read push back chars.
			sentence.charBuffer[index] = (char) c;
			sentence.charTypeBuffer[index] = CharacterUtil.getCharType(c);
			index++;
		}
		
		while ((c = readChar()) > -1) {
			cType = CharacterUtil.getCharType(c);
			c = normalizeChar(c, cType);
			sentence.charBuffer[index] = (char)c;
			sentence.charTypeBuffer[index] = cType;
			index++;
			if (isSentenceSeparator((char)c)) {
				if (c == '.') {
					c = readChar();
					if (c == -1) {  // No more chars.
						break;
					}
					cType = CharacterUtil.getCharType(c);  
					c = normalizeChar(c, cType);
					if (cType == CharacterType.ARABIC_NUMBER) {   // Number with digit
						sentence.charBuffer[index] = (char)c;
						sentence.charTypeBuffer[index] = cType;
						index++;
					} else {   
						pushBack(c);
						break;
					}
				} else {
					break;
				}
			}
		}
		sentence.limit(index);
		return index > 0;
	}
	
	private int normalizeChar(int c, byte cType) {
		if (cType == CharacterType.ENGLISH_LETTER) {
			return Character.toLowerCase(c);
		}
		return c;
	}
	
	private void pushBack(int c) {
		pushBackBuffer[pushBackCharNumber++] = (char) c;
	}
	
	private int readPushBackChar() {
		if (pushBackCharNumber > 0) {
			return pushBackBuffer[--pushBackCharNumber];
		} else {
			return -1;
		}
	}

	private int readChar() {
		if (bufferCursor == bufferLimit) {
			bufferLimit = fillBuffer();
			bufferCursor = 0;
		}
		if (bufferLimit == -1) {
			return  -1;
		}
		return buffer[bufferCursor++];
	}
	
	private boolean isSentenceSeparator(char c) {
		int index = Arrays.binarySearch(Sentence_Separator, c);
		return index >= 0;
	}
	
	private int fillBuffer() {
		try {
			return input.read(buffer);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private Sentence sentence = new Sentence();
	
	public Sentence sentence() {
		return sentence;
	}
}
