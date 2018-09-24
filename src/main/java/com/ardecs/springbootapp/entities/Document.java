package com.ardecs.springbootapp.entities;


import com.ardecs.springbootapp.client.dto.DocumentDTO;
import com.ardecs.springbootapp.client.dto.FileDTO;
import com.google.gwt.user.client.rpc.IsSerializable;

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

    //private int user_id;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "doc_id")
    private List<File> files;

    public Document() {
    }

    public Document(DocumentDTO documentDTO) {
        this.id = documentDTO.getId();
        this.data = documentDTO.getData();
        this.title = documentDTO.getTitle();
        this.description = documentDTO.getDescription();
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

//    public int getUser_id() {
//        return user_id;
//    }
//
//    public void setUser_id(int user_id) {
//        this.user_id = user_id;
//    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }
}
