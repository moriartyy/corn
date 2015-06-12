package com.hichao.analysis.corn.dic.attribute;

public class TextAttribute implements Attribute {

	private final String text;

	public TextAttribute(String text) {
		this.text = text;
	}

	public String text() {
		return text;
	}

	@Override
	public void merge(Attribute a) {
		// Nothing to merge.
	}
}
