package com.ardecs.springbootapp.entities;


import com.ardecs.springbootapp.client.dto.UserDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(generator="UserSeq")
    @SequenceGenerator(name="UserSeq",sequenceName="USER_SEQ")
    private Long id;
    private String login;
    private String password;
    private String name;
    @OneToMany(mappedBy = "user")
    private List<Document> documents;


    public User() {
    }

    public User(UserDTO userDTO) {
        this.id = userDTO.getId() != -1 ? userDTO.getId() : null;
        this.login = userDTO.getLogin();
        this.password = userDTO.getPassword();
        this.name = userDTO.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }
}
