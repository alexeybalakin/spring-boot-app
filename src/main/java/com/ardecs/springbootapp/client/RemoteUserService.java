package com.ardecs.springbootapp.client;

import com.ardecs.springbootapp.client.dto.UserDTO;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;


@RemoteServiceRelativePath("gwt.user_service")
public interface RemoteUserService extends RemoteService {
    List<UserDTO> list();

    void delete(UserDTO user);

    UserDTO save(UserDTO user);

}
