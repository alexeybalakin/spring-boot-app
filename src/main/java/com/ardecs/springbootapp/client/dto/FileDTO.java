package com.ardecs.springbootapp.client.dto;

import java.io.Serializable;

public class FileDTO implements Serializable {
    private Long id;
    private String name;

    public FileDTO() {
    }

    public FileDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
