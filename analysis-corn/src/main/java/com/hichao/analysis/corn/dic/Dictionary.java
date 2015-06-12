package com.hichao.analysis.corn.dic;

import java.util.HashMap;
import java.util.Map;

public class Dictionary {
	
	private final static Map<Object, Dictionary> instances = new HashMap<Object, Dictionary>();
	
	public synchronized static void put(Object key, Dictionary dic) {
		instances.put(key, dic);
	}
	
	public static Dictionary get(Object key) {
		return instances.get(key);
	}
	
	public static int instanceCount() {
		return instances.size();
	}

    public static Dictionary from(WordSourceFactory sourceFactory) {
    	return new Dictionary(sourceFactory);
    }

    private DictionarySegment mainDict;
	private WordSourceFactory sourceFactory;

    private Dictionary(WordSourceFactory sourceFactory) {
    	this.sourceFactory = sourceFactory;
    	this.loadWords();
    }
    
    public synchronized void reload() {
        this.loadWords();
    }

    private void loadWords() {
        Map<String, Word> words = new HashMap<String, Word>();
        Word word = null;
        for (WordSource source : sourceFactory.createSources()) {
			while ((word = source.next()) != null) {
		           Word check = words.get(word.text());
	                if (check == null) {
	                    words.put(word.text(), word);
	                } else {
	                	check.merge(word);
	                }
			}
			source.close();
		}
        
        DictionarySegment dictSegment = new DictionarySegment((char)0);
        for (Word w : words.values()) {
            dictSegment.addWord(w);
        }
        this.mainDict = dictSegment;
    }
    
    public DictionarySegment mainSeg() {
        return this.mainDict;
    }

	public static void relaod() {
		instances.forEach((k, v) -> { v.reload(); });
	}

}
