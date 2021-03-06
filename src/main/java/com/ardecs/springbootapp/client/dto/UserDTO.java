package com.ardecs.springbootapp.client.dto;

import java.io.Serializable;
import java.util.List;

public class UserDTO implements Serializable {

    private Long id;
    private String login;
    private String password;
    private String name;

    private List<DocumentDTO> documents;

    public UserDTO() {
    }

    public UserDTO(Long id, String login, String password, String name) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
    }

    public UserDTO(String login, String password, String name) {
        this.login = login;
        this.password = password;
        this.name = name;
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

    public List<DocumentDTO> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentDTO> documents) {
        this.documents = documents;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDTO that = (UserDTO) o;

        return id.longValue() == that.id.longValue();
    }

    @Override
    public int hashCode() {
        return id.intValue();
    }

}
