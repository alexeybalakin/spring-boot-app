package com.ardecs.springbootapp.server.services.remote;

import com.ardecs.springbootapp.client.RemoteUserService;
import com.ardecs.springbootapp.client.dto.UserDTO;
import com.ardecs.springbootapp.server.services.UserService;
import com.ardecs.springbootapp.server.util.ExtRemoteServiceServlet;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RemoteServiceRelativePath("gwt.user_service")
public class RemoteUserServiceImpl extends ExtRemoteServiceServlet implements RemoteUserService {

    @Autowired
    private UserService userService;

    @Override
    public List<UserDTO> list() {
        return userService.list();
    }
    @Override
    public void delete(UserDTO user) {
        userService.delete(user);
    }
    @Override
    public UserDTO save(UserDTO data) {
        return userService.save(data);
    }

}
