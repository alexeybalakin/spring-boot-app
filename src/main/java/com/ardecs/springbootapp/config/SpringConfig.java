package com.ardecs.springbootapp.config;

import com.ardecs.springbootapp.server.services.remote.RemoteDocServiceImpl;
import com.ardecs.springbootapp.server.services.remote.RemoteUserServiceImpl;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    ServletRegistrationBean userServiceRegistration(RemoteUserServiceImpl userService) {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(
                userService, "/gwtApp/gwt.user_service/*");
        registrationBean.setServlet(userService);
        registrationBean.setLoadOnStartup(1);
        return registrationBean;
    }

    @Bean
    ServletRegistrationBean docServiceRegistration(RemoteDocServiceImpl docService) {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(
                docService, "/gwtApp/gwt.doc_service/*");
        registrationBean.setServlet(docService);
        registrationBean.setLoadOnStartup(1);
        return registrationBean;
    }

}
