package com.ardecs.springbootapp.server;

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

    public String testMethod() {
        return "Hello, user!";
    }

}
