package com.example.kenzan_api_demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

enum Roles {Admin};

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    /* This provides authentication through the Basic HTTP authentication scheme. The username/password for the
    protected endpoints is the not-quite secure combination of admin/admin. In a real production setting, this would
    be using some stronger form of authentication, hopefully OAuth with an external authentication provider, but this
    serves for the purposes of demonstration.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
             Require all modification endpoints (DELETE, PATCH, PUT, and POST) to be authenticated, while allowing all
             read operations without authentication.
         */

        http.csrf().disable()
                .authorizeRequests()
                    .antMatchers(HttpMethod.DELETE, "/employees/{id}").authenticated()
//                .anyRequest().permitAll()
                    .anyRequest().permitAll()
                    .and()
                    .httpBasic();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        // Define a default admin user with the password `admin`
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .roles(Roles.Admin.name())
                .build();
        InMemoryUserDetailsManager userDetails = new InMemoryUserDetailsManager(admin);
        return userDetails;
    }
}
