package com.ardecs.springbootapp;

import com.ardecs.springbootapp.repositories.UserRepository;
import com.ardecs.springbootapp.server.AppContext;
import oracle.jdbc.pool.OracleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
//@ComponentScan("com.ardecs.springbootapp")
//@EnableJpaRepositories("com.ardecs.springbootapp.repositories")
public class SpringConfig {
    @Bean
    SimpleBean simpleBean(){
        return new SimpleBean();
    }

    @Bean
    DataSource dataSource() throws SQLException {
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setUser("system");
        dataSource.setPassword("oracle");
        dataSource.setURL("jdbc:oracle:thin:@localhost:49161:xe");
        dataSource.setImplicitCachingEnabled(true);
        dataSource.setFastConnectionFailoverEnabled(true);
        return dataSource;
    }

}
