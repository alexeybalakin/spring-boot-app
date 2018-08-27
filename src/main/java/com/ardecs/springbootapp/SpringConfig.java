package com.ardecs.springbootapp;

import com.ardecs.springbootapp.server.UserServiceImpl;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

//    @Bean
//    DataSource dataSource() throws SQLException {
//        OracleDataSource dataSource = new OracleDataSource();
//        dataSource.setUser("system");
//        dataSource.setPassword("oracle");
//        dataSource.setURL("jdbc:oracle:thin:@localhost:49161:xe");
//        dataSource.setImplicitCachingEnabled(true);
//        dataSource.setFastConnectionFailoverEnabled(true);
//        return dataSource;
//    }

    @Bean
    ServletRegistrationBean userServiceRegistration(UserServiceImpl userService) {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(
                userService, "/gwtApp/gwt.user_service/*");
        registrationBean.setServlet(userService);
        registrationBean.setLoadOnStartup(1);

        return registrationBean;
    }

}
