package com.hichao.elasticsearch.index.analysis;

import org.elasticsearch.index.analysis.AnalysisModule;


public class CornAnalysisBinderProcessor extends AnalysisModule.AnalysisBinderProcessor {

    @Override public void processTokenFilters(TokenFiltersBindings tokenFiltersBindings) {

    }


    @Override public void processAnalyzers(AnalyzersBindings analyzersBindings) {
        analyzersBindings.processAnalyzer("corn", CornAnalyzerProvider.class);
        super.processAnalyzers(analyzersBindings);
    }


    @Override
    public void processTokenizers(TokenizersBindings tokenizersBindings) {
      tokenizersBindings.processTokenizer("corn", CornTokenizerFactory.class);
      super.processTokenizers(tokenizersBindings);
    }
}
