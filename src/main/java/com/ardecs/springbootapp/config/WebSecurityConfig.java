package com.ardecs.springbootapp.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
//                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, DataSource dataSource,
                                ObjectPostProcessor<Object> objectPostProcessor) throws Exception {


        AuthenticationManagerBuilder parent = new AuthenticationManagerBuilder(objectPostProcessor);
        parent
                .inMemoryAuthentication()
                .withUser("user")
                .password("password")
                .roles("USER");

        auth
                .parentAuthenticationManager(parent.build())
                .jdbcAuthentication()
//                .withUser("user").password("password").roles("USER")
//                .and()
                .dataSource(dataSource)
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .usersByUsernameQuery("select login,password,'true' from users where login = ?")
                .authoritiesByUsernameQuery("select login,'USER' from users where login = ?");
    }
}
