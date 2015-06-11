package com.hichao.analysis.corn.dic;


public class Match {
    
    private boolean isFullMatch;
    private boolean isPrefixMatch;
    private int offset = -1;
    private DictionarySegment dicSegment;

    public boolean isPrefixMatch() {
        return isPrefixMatch;
    }
    
    public void setDicSegment(DictionarySegment dicSegment) {
        this.dicSegment = dicSegment;
    }

    public DictionarySegment dicSegment() {
        return dicSegment;
    }

    public boolean isMatch() {
        return isFullMatch || isPrefixMatch;
    }
    
    public boolean isFullMatch() {
        return isFullMatch;
    }
    
    public static Match empty() {
        return new Match();
    }

    public int start() {
        return this.offset;
    }

    public void setStart(int offset) {
        this.offset = offset;
    }

    public void setUnMatch() {
        this.isFullMatch = false;
        this.isPrefixMatch = false;
    }

    public void setFullMatch() {
        this.isFullMatch = true;
    }

    public void setPrefixMatch() {
        this.isPrefixMatch = true;
    }

}
