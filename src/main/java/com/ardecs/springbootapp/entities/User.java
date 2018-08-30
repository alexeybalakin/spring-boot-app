package com.ardecs.springbootapp.entities;


import com.google.gwt.user.client.rpc.IsSerializable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements IsSerializable {
    @Id
    @GeneratedValue
    private Long id;
    private String login;
    private String password;
    private String name;
//    @OneToMany
//    @JoinColumn(name = "user_id")
//    private List<Document> documents;

    public User() {
    }

    public User(String login, String password, String name) {
        this.login = login;
        this.password = password;
        this.name = name;
    }

    public User(Long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
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

//    public List<Document> getDocuments() {
//        return documents;
//    }
//
//    public void setDocuments(List<Document> documents) {
//        this.documents = documents;
//    }
}
