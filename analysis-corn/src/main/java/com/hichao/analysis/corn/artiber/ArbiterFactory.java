package com.hichao.analysis.corn.artiber;

public class ArbiterFactory {
	
	public static final ArbiterFactory sharedInstance = new ArbiterFactory();

	public Arbiter create(Strategy strategy) {
        
		if (strategy == Strategy.MostWords) {
            return new MostWordsArbiter();
        } 
        
		if (strategy == Strategy.DicWords) {
			return new DicWordsArbiter();
		}
		
        return new SmartMostWordsArbiter();
	}
}
