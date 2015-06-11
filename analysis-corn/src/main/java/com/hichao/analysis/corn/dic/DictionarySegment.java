package com.hichao.analysis.corn.dic;

import java.util.Map;

import com.hichao.analysis.corn.CharacterUtil;

public class DictionarySegment extends CharNode<DictionarySegment> {

    private Word word;
    
    public DictionarySegment(Character c) {
        super(c);
    }

    public void addWord(Word word) {
       char[] charArray = word.text().toCharArray();
       int charCount = charArray.length;
       DictionarySegment current = this;
       for (int i = 0; i < charCount; i++) {
           char c = (char) CharacterUtil.normalize(charArray[i]);
           DictionarySegment child = current.getChild(c);
           if (child == null){
               child = current.addChild(new DictionarySegment(c));
           }
           current = child;
       }
       current.word = word;
    }
    
    private boolean hasNodeWord() {
        return this.word != null;
    }
    
    public Word word() {
        return this.word;
    }

    public Match match(Character c, Match match) {
        match.setUnMatch();
        DictionarySegment child = this.getChild(c);
        if (child == null) {
            return match;
        } else {
            if (child.hasNodeWord()) {
                match.setFullMatch();
            } 
            
            if (child.hasChildren()) {
                match.setPrefixMatch();
            }
            match.setDicSegment(child);
        }
        return match;
    }

    public void printWords() {
        printWords(this, "");
    }
    
    public void printWords(DictionarySegment segment, String path){
        if (segment.hasNodeWord()){
            System.out.println(path + segment.getNodeChar());
        }
        
        Map<Character, DictionarySegment> children = segment.getChildren();
        if (children != null){
            for(DictionarySegment child : children.values()){
                printWords(child, path + segment.getNodeChar());
            }
        }
    }
}


