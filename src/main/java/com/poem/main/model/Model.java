package com.poem.main.model;

import java.util.List;

public class Model {
    
    private String name;
    private List<String[]> elements;

    public Model(String name, List<String[]> elements) {
        this.name = name;
        this.elements = elements;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String[]> getElements() {
        return elements;
    }

    public void setElements(List<String[]> elements) {
        this.elements = elements;
    }  
}
