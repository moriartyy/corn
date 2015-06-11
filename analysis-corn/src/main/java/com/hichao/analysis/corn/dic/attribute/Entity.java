package com.hichao.analysis.corn.dic.attribute;

public class Entity {
    
    protected final String name;
    protected final String type;
    protected final int id;
    
    public static Entity create(int id, String name, String type) {
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
}
