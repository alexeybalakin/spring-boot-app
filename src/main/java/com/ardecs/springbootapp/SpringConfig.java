package com.ardecs.springbootapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    SimpleBean simpleBean(){
        return new SimpleBean();
    }
}
