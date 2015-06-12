package com.hichao.analysis.corn.dic.attribute;

public class Entity {
    
    protected final String name;
    protected final String type;
    protected final int id;
    
    public static Entity create(int id, String name, String type) {
    	if (id <= 0) {
    		throw new IllegalArgumentException("Parameter 'id' must greater then 0.");
    	}
    	if (name == null || name.length() == 0) {
    		throw new IllegalArgumentException("Parameter 'name' can not be null or empty.");
    	}
    	if (type == null || type.length() == 0) {
    		throw new IllegalArgumentException("Parameter 'type' can not be null or empty.");
    	}
    	return new Entity(id, name, type);
    }
    
    private Entity(int id, String name, String type){
        this.id = id;
        this.name = name;
        this.type = type;
    }
    
    public int id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String type() {
        return type;
    }
    
    @Override 
    public String toString() {
    	return this.name;
    }
    
    @Override
    public boolean equals(Object o) {
    	if (o == null) {
    		return false;
    	}
    	if (o == this) {
    		return true;
    	}
    	if (o.getClass() != this.getClass()) {
    		return false;
    	}
    	Entity e = (Entity)o;
    	return this.id == e.id
    			&& this.name == e.name
    			&& this.type == e.type;
    	
    }
    
    @Override
    public int hashCode() {
    	int hash = this.id;
    	hash = hash * 31 + this.name.hashCode();
    	hash = hash * 31 + this.type.hashCode();
    	return hash;
    }
}
