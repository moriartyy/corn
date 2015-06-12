package com.hichao.nlp.analysis.corn.attribute;

import org.junit.Assert;
import org.junit.Test;

import com.hichao.analysis.corn.dic.attribute.Entity;
import com.hichao.analysis.corn.dic.attribute.EntityAttribute;
import com.hichao.analysis.corn.dic.attribute.TextAttribute;

public class EntityAttributeTests {

	@Test
	public void testMerge() {
		TextAttribute textAttribute = new TextAttribute("hello");
		EntityAttribute entityAttribute = new EntityAttribute(Entity.create(1, "red", "color"));
		int hash = entityAttribute.hashCode();
		entityAttribute.merge(textAttribute);
		Assert.assertEquals(hash, entityAttribute.hashCode());
		
		EntityAttribute entityAttribute2 = new EntityAttribute(Entity.create(1, "red", "color"));
		entityAttribute.merge(entityAttribute2);
		Assert.assertEquals(1, entityAttribute.count());
		
		entityAttribute.addEntity(Entity.create(2, "red", "color"));
		entityAttribute.addEntity(Entity.create(1, "red", "color"));
		Assert.assertEquals(2, entityAttribute.count());
	}
}
