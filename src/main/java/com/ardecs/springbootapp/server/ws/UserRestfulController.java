package com.ardecs.springbootapp.server.ws;

import java.util.List;

import com.ardecs.springbootapp.entities.User;
import com.ardecs.springbootapp.server.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
public class UserRestfulController {

    @Autowired
    UserService userService;

    @GetMapping(path = "/gwtApp/gwt/users")
    List<User> list() {
        return userService.list();
    }

    @DeleteMapping("/gwtApp/gwt/users/{userId}")
    void delete(@PathVariable("userId") Long userId) {
        userService.delete(userId);
    }

    @PutMapping("/gwtApp/gwt/users")
    void save(@RequestBody User user) {
        userService.save(user);
    }

}
