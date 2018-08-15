package com.ardecs.springbootapp.server;

import com.ardecs.springbootapp.SpringConfig;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppContext implements ApplicationContextAware {
   private static ApplicationContext appContext = new AnnotationConfigApplicationContext(SpringConfig.class);

     public AppContext() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appContext = applicationContext;
    }

    public static ApplicationContext getAppContext() {
        return appContext;
    }
}
