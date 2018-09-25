package com.ardecs.springbootapp.entities;


import com.ardecs.springbootapp.client.dto.DocumentDTO;
import com.ardecs.springbootapp.client.dto.FileDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "docs")
public class Document implements Serializable {
    @Id @GeneratedValue
    private Long id;
    private Date data;
    private String title;
    private String description;

    @OneToMany
    @JoinColumn(name = "doc_id")
    private List<File> files;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Document() {
    }

    public Document(DocumentDTO documentDTO) {
        this.id = documentDTO.getId();
        this.data = documentDTO.getData();
        this.title = documentDTO.getTitle();
        this.description = documentDTO.getDescription();
        this.user = new User(documentDTO.getUser());
        List<FileDTO> fileDTOS = documentDTO.getFiles();
        if (fileDTOS !=null){
            List<File> files = new ArrayList<>();
            for(FileDTO fileDTO : fileDTOS){
                files.add(new File(fileDTO));
            }
            this.files = files;
        }
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

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
