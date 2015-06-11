package com.hichao.elasticsearch.index;

import org.elasticsearch.index.analysis.AnalysisModule;


public class CornAnalysisBinderProcessor extends AnalysisModule.AnalysisBinderProcessor {

    @Override public void processTokenFilters(TokenFiltersBindings tokenFiltersBindings) {

    }


    @Override public void processAnalyzers(AnalyzersBindings analyzersBindings) {
        analyzersBindings.processAnalyzer("ik", CornAnalyzerProvider.class);
        super.processAnalyzers(analyzersBindings);
    }


    @Override
    public void processTokenizers(TokenizersBindings tokenizersBindings) {
      tokenizersBindings.processTokenizer("ik", CornTokenizerFactory.class);
      super.processTokenizers(tokenizersBindings);
    }
}
