package com.ardecs.springbootapp.server.services;

import java.util.ArrayList;
import java.util.List;

import com.ardecs.springbootapp.client.UserService;
import com.ardecs.springbootapp.client.dto.UserDTO;
import com.ardecs.springbootapp.entities.User;
import com.ardecs.springbootapp.repositories.UserRepository;
import com.ardecs.springbootapp.server.util.ExtRemoteServiceServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ExtRemoteServiceServlet implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public List<UserDTO> list() {
        List<UserDTO> users = new ArrayList<>();
        for(User user : repository.findAll()){
            users.add(new UserDTO(user.getId(), user.getLogin(), user.getPassword(), user.getName()));
        }
        return users;
    }

    @Override
    public void delete(UserDTO user) {
        repository.delete(user.getId());
    }

    @Override
    public UserDTO save(UserDTO data) {
        User user = new User();
        user.setId(data.getId());
        user.setLogin(data.getLogin());
        user.setPassword(data.getPassword());
        user. setName(data.getName());
        repository.save(user);
        data.setId(user.getId());
        return data;
    }

}
