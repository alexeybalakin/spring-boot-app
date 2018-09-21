package com.ardecs.springbootapp.client;

import java.util.List;

import com.ardecs.springbootapp.entities.User;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import javax.servlet.Servlet;


@RemoteServiceRelativePath("gwt.user_service")
public interface RemoteUserService extends RemoteService {
    List<User> list();

    User getUserById(int id);

    void delete(User user);

    User save(User user);

}
