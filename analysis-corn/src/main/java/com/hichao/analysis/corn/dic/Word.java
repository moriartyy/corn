package com.hichao.analysis.corn.dic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.hichao.analysis.corn.dic.attribute.Attribute;
import com.hichao.analysis.corn.dic.attribute.TextAttribute;


public class Word implements AttributeSource<Word> {
	
	private Map<Class<? extends Attribute>, Attribute> attrs = new HashMap<Class<? extends Attribute>, Attribute>(20);
	private TextAttribute textAttr;
	
	public static Word create(String text) {
		return new Word(new TextAttribute(text));
	}
	
	protected Word(TextAttribute text) {
		this.textAttr = text;
		attrs.put(TextAttribute.class, text);
	}
	
	public final static Word Empty = new Word(new TextAttribute(null));
	
	@Override
	public Iterator<Attribute> iterator() {
		return attrs.values().iterator();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Attribute> T getAttribute(Class<T> clazz) {
		return (T) attrs.get(clazz);
	}

	@Override
	public void addAttribute(Attribute attr) {
		attrs.put(attr.getClass(), attr);
	}
	
	public String text() {
		return textAttr.text();
	}
	
	@Override
	public String toString() {
		return textAttr.text();
	}
	
	@Override
	public int hashCode() {
		return this.text().hashCode();
	}

	@Override
	public Word merge(Word w) {
		w.attrs.forEach((k, v) -> {
			Attribute attr = attrs.get(k);
			if (attr == null) {
				attrs.put(k, v);
			} else {
				attr.merge(v);
			}
		});
		this.attrs.putAll(w.attrs);
		return this;
	}
}
