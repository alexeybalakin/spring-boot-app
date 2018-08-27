package com.ardecs.springbootapp.server.services;

import org.springframework.stereotype.Component;

@Component
public class SimpleBean {
    public String helloWorld() {
        return "Hello world!";
    }
}
