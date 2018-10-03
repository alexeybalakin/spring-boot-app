package com.ardecs.springbootapp.client.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DocumentDTO implements Serializable {
    private Long id;
    private Date data;
    private String title;
    private String description;
    private List<FileDTO> files;
    private UserDTO user;

    public DocumentDTO() {
    }

    public DocumentDTO(Long id, Date data, String title, String description, List<FileDTO> files, UserDTO user) {
        this.id = id;
        this.data = data;
        this.title = title;
        this.description = description;
        this.files = files;
        this.user = user;
    }

    public DocumentDTO(Long id, Date data, String title, String description, UserDTO user) {
        this.id = id;
        this.data = data;
        this.title = title;
        this.description = description;
        this.user = user;
    }

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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DocumentDTO that = (DocumentDTO) o;

        return id.longValue() == that.id.longValue();
    }

    @Override
    public int hashCode() {
        return id.intValue();
    }
}
