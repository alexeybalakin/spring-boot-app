package com.ardecs.springbootapp.client;

import java.util.List;

import com.ardecs.springbootapp.client.dto.UserDTO;
import com.ardecs.springbootapp.entities.User;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("gwt.user_service")
public interface UserService extends RemoteService {

    List<UserDTO> list();

    //UserDTO getUserById(int id);

    void delete(UserDTO user);

    UserDTO save(UserDTO user);

}
