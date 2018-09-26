package com.ardecs.springbootapp.server.services;

import java.util.List;

import com.ardecs.springbootapp.entities.User;
import com.ardecs.springbootapp.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> list() {
        return repository.findAll();
    }

    @Transactional
    public void delete(Long userId) {
        repository.delete(userId);
    }

    @Transactional
    public User save(User data) {
        User user = new User();
        user.setId(data.getId());
        user.setLogin(data.getLogin());
        user.setPassword(data.getPassword());
        user.setName(data.getName());
        repository.save(user);
        data.setId(user.getId());
        return data;
    }
}
