package com.ardecs.springbootapp.client;

import com.ardecs.springbootapp.entities.User;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


import java.util.List;

@RemoteServiceRelativePath("service")
public interface UserService extends RemoteService {

  List<User> list();
}
