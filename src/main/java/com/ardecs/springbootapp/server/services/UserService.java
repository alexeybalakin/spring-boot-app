package com.ardecs.springbootapp.server.services;

import com.ardecs.springbootapp.client.dto.UserDTO;
import com.ardecs.springbootapp.entities.User;
import com.ardecs.springbootapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<UserDTO> list() {
        List<UserDTO> users = new ArrayList<>();
        for(User user : repository.findAll()){
            users.add(new UserDTO(user.getId(), user.getLogin(), user.getPassword(), user.getName()));
        }
        return users;
    }

    public void delete(UserDTO user) {
        repository.delete(user.getId());
    }

    public UserDTO save(UserDTO data) {
        User user = new User(data);
        repository.save(user);
        data.setId(user.getId());
        return data;
    }
}
