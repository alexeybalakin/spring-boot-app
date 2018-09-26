package com.ardecs.springbootapp.client;

import java.util.List;
import javax.ws.rs.*;

import com.ardecs.springbootapp.entities.User;

import org.fusesource.restygwt.client.Attribute;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;


@Path("gwt/users")
public interface RestUserService extends RestService {

    @GET
    void list(MethodCallback<List<User>> callback);

//    User getUserById(int id);

    @DELETE
    @Path("/{userId}")
    void delete(@PathParam("userId") @Attribute("id") User user, MethodCallback<Void> callback);

    @PUT
    void save(User user, MethodCallback<User> callback);

}
