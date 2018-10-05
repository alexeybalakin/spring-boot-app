package com.ardecs.springbootapp.entities;


import com.ardecs.springbootapp.client.dto.FileDTO;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "files")
public class File implements Serializable {
    @Id
    @GeneratedValue(generator="FileSeq")
    @SequenceGenerator(name="FileSeq",sequenceName="FILE_SEQ")
    private Long id;
    private String name;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "doc_id")
    private Document document;

    public File() {
    }

    public File(FileDTO fileDTO){
        this.id = fileDTO.getId() != -1 ? fileDTO.getId() : null;
        this.name = fileDTO.getName();
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

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}
