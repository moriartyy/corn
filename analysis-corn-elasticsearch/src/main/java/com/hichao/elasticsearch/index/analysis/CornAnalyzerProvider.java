package com.hichao.elasticsearch.index.analysis;

import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.analysis.AbstractIndexAnalyzerProvider;
import org.elasticsearch.index.settings.IndexSettings;

import com.hichao.analysis.corn.artiber.Strategy;
import com.hichao.analysis.corn.dic.DictionaryFactoryImpl;
import com.hichao.lucene.CornAnalyzer;

public class CornAnalyzerProvider extends AbstractIndexAnalyzerProvider<CornAnalyzer> {
    private final CornAnalyzer analyzer;

    @Inject
    public CornAnalyzerProvider(Index index, @IndexSettings Settings indexSettings, Environment environment, @Assisted String name, @Assisted Settings settings) {
        super(index, indexSettings, name, settings);
        analyzer = new CornAnalyzer(DictionaryFactoryImpl.create(settings, environment), Strategy.MostWords);
    }

    @Override 
    public CornAnalyzer get() {
        return this.analyzer;
    }
}
