package com.ardecs.springbootapp.client.dto;

import com.ardecs.springbootapp.entities.File;

import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DocumentDTO implements Serializable {
    private Long id;
    private Date data;
    private String title;
    private String description;

    //private int user_id;

    public DocumentDTO() {
    }

    public DocumentDTO(Long id, Date data, String title, String description, List<FileDTO> files) {
        this.id = id;
        this.data = data;
        this.title = title;
        this.description = description;
        this.files = files;
    }

    private List<FileDTO> files;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<FileDTO> getFiles() {
        return files;
    }

    public void setFiles(List<FileDTO> files) {
        this.files = files;
    }
}
