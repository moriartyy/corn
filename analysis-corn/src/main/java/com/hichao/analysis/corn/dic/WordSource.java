package com.hichao.analysis.corn.dic;

public interface WordSource {
    void close();
    Word next();
}
