package com.hichao.analysis.corn.dic;

import com.hichao.analysis.corn.dic.attribute.Attribute;

public interface AttributeSource<AS> extends Iterable<Attribute> {

	<T extends Attribute> T getAttribute(Class<T> clazz);
	void addAttribute(Attribute attribute);
	AS merge(AS o);
}
