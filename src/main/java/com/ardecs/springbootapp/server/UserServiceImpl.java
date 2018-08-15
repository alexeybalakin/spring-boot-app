package com.ardecs.springbootapp.server;

import com.ardecs.springbootapp.SpringConfig;
import com.ardecs.springbootapp.client.UserService;
import com.ardecs.springbootapp.entities.User;
import com.ardecs.springbootapp.repositories.UserRepository;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;


import java.util.List;
@Service
public class UserServiceImpl extends RemoteServiceServlet implements UserService {

//    ApplicationContext context = new AnnotationConfigWebApplicationContext();

    @Autowired
    private UserRepository repository ;
//     = context.getBean(UserRepository.class);


    @Override
    public List<User> list() {
        return repository.findAll();
    }
}
