package com.ardecs.springbootapp.client;

import com.ardecs.springbootapp.entities.User;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;

import java.util.List;

public interface UserServiceAsync {
    void list(AsyncCallback<List<User>> async);
}
