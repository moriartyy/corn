package com.hichao.analysis.corn.dic.attribute;

import java.util.HashSet;
import java.util.Set;

public class EntityAttribute implements Attribute {

	private Set<Entity> entities = new HashSet<Entity>();
	
	public EntityAttribute(Entity entity) {
		entities.add(entity);
	}
	
	public Set<Entity> entities() {
		return entities;
	}

	public int count() {
		return entities.size();
	}
}
