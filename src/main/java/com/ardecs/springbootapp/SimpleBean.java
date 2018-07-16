package com.ardecs.springbootapp;

import org.springframework.stereotype.Component;

@Component
public class SimpleBean {
     String helloWorld(){
       return "Hello world!";
    }
}
