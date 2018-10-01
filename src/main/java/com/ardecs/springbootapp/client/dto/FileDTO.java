package com.ardecs.springbootapp.client.dto;

import java.io.Serializable;

public class FileDTO implements Serializable {
    private Long id;
    private String name;
    private DocumentDTO document;

    public FileDTO() {
    }

    public FileDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public FileDTO(Long id, String name, DocumentDTO document) {
        this.id = id;
        this.name = name;
        this.document = document;
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

    public DocumentDTO getDocument() {
        return document;
    }

    public void setDocument(DocumentDTO document) {
        this.document = document;
    }
}
