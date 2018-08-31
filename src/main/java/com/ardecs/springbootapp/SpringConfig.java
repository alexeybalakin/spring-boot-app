package com.ardecs.springbootapp;

import com.ardecs.springbootapp.server.UserServiceImpl;

import oracle.jdbc.pool.OracleDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

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

//@Bean
//public DataSource dataSource() {
//    DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//    dataSourceBuilder.url("jdbc:postgresql://horton.elephantsql.com:5432/uncbcvhz");
//    dataSourceBuilder.username("uncbcvhz");
//    dataSourceBuilder.password("m2tx2mVUwhiicDmg7dRqabkHFV2T_B5N");
//    return dataSourceBuilder.build();
//}
    @Bean
    ServletRegistrationBean userServiceRegistration(UserServiceImpl userService) {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(
                userService, "/gwtApp/gwt.user_service/*");
        registrationBean.setServlet(userService);
        registrationBean.setLoadOnStartup(1);

        return registrationBean;
    }

}
