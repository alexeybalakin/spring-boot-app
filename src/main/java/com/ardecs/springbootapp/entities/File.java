package com.ardecs.springbootapp.entities;


import com.ardecs.springbootapp.client.dto.FileDTO;
import com.google.gwt.user.client.rpc.IsSerializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "files")
public class File implements Serializable {
    @Id @GeneratedValue
    private Long id;
    private String name;

    public File() {
    }

    public File(FileDTO fileDTO){
        this.id = fileDTO.getId();
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
}
