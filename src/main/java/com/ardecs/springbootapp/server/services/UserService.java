package com.ardecs.springbootapp.server.services;

import com.ardecs.springbootapp.entities.User;
import com.ardecs.springbootapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> list() {
        return repository.findAll();
    }

    public User getUserById(int id) {
        return repository.getOne(new Long(id));
    }

    public void delete(User user) {
        repository.delete(user.getId());
    }

    public User save(User data) {
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
