package org.regeneration.controllers;

import org.regeneration.models.User;
import org.regeneration.security.MyUserDetails;
import org.regeneration.security.Role;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

@TestConfiguration
public class SpringSecurityWebAuxTestConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        User doctorUser = new User("doc1", "doc1password", Role.DOCTOR);
        MyUserDetails myTestUserDetails1 = new MyUserDetails(doctorUser);

        User citizenUser = new User("cit1", "cit1password", Role.CITIZEN);
        MyUserDetails myTestUserDetails2 = new MyUserDetails(citizenUser);

        return new InMemoryUserDetailsManager(Arrays.asList(
                myTestUserDetails1, myTestUserDetails2
        ));
    }
}