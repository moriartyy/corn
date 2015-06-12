package com.hichao.analysis.corn.dic;

import com.hichao.analysis.corn.dic.attribute.Entity;
import com.hichao.analysis.corn.dic.attribute.EntityAttribute;
import com.hichao.analysis.corn.dic.attribute.TextAttribute;

public class EntityWord extends Word {
	
	public static EntityWord create(String name, Entity entity) {
		EntityWord word = new EntityWord(name);
		word.addAttribute(new EntityAttribute(entity));
		return word;
	}
	
	private EntityWord(String name) {
		super(new TextAttribute(name));
	}

}
