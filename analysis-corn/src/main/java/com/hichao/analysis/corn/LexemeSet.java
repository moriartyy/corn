package com.hichao.analysis.corn;

import java.util.HashMap;
import java.util.Iterator;

public class LexemeSet implements Iterable<Lexeme> {
	
	private HashMap<Integer, Lexeme> map;

	public LexemeSet(int initialCapacity) {
		this.map = new HashMap<Integer, Lexeme>(initialCapacity);
	}
	
	public void add(Lexeme lexeme) {
		int key = lexeme.hashCode();
		Lexeme old = this.map.get(key);
		if (old == null) {
			this.map.put(key, lexeme);
		} else {
			if (lexeme.compareTo(old) > 0) {
				map.put(key, lexeme);
			}
		}
	}

	@Override
	public Iterator<Lexeme> iterator() {
		return map.values().iterator();
	}

	public void clear() {
		map.clear();
	}
}
