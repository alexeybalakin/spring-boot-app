package com.ardecs.springbootapp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class SpringBootAppApplication {
    private static final Logger logger = LogManager.getLogger();

	public static void main(String[] args) {
		SpringApplication.run(SpringBootAppApplication.class, args);
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        SimpleBean simpleBean = context.getBean(SimpleBean.class);
       logger.info(simpleBean.helloWorld());
	}
}
