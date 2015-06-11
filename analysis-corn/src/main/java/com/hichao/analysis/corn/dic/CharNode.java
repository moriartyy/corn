package com.hichao.analysis.corn.dic;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CharNode<T extends CharNode<?>> {
    
    protected final Character nodeChar;
    private Map<Character, T> children;
    

    public CharNode(Character c){
        this.nodeChar = c;
    }
    
    public T addChild(T newChild){
        if (this.children == null){
            synchronized (this) {
                if (this.children == null){
                    this.children = new ConcurrentHashMap<Character, T>(3,0.8f); 
//                    this.children = new HashMap<Character, T>();  
                }
            }
        }
        this.children.put(newChild.nodeChar, newChild);
        return newChild;
    }
    
    public boolean hasChildren(){
        return this.children != null;
    }
    
    public T getChild(Character c){
        return this.children == null ? null : this.children.get(c);
    }
    
    Map<Character, T> getChildren(){
        return this.children;
    }


    public char getNodeChar() {
        return this.nodeChar;
    }
    
    public int compareTo(T o) {
        if (o == null)
            return 1;
        return this.nodeChar.compareTo(o.nodeChar);
    }
}
