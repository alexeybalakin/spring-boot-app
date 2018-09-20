package com.ardecs.springbootapp.server.services;

import java.util.List;

import com.ardecs.springbootapp.client.UserService;
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
    public List<User> list() {
        return repository.findAll();
    }

    @Override
    public User getUserById(int id) {
        return repository.getOne(new Long(id));
    }

    @Override
    public void delete(User user) {
        repository.delete(user.getId());
    }

    @Override
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
