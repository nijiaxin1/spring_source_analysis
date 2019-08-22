package com.njx.spring.source.analysis.entity;

import com.njx.spring.source.analysis.annotation.Entity;

@Entity(value = "city")
public class CityEntity {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
