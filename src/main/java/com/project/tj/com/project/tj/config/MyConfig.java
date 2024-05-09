package com.project.tj.com.project.tj.config;

import com.project.tj.com.project.tj.entities.CustomUser;
import com.project.tj.com.project.tj.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;

@Configuration
public class MyConfig {

    private final InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

    @Autowired
    private UserServices userServices;

    @Bean
    public UserDetailsService userDetailsService() {
        List<CustomUser> users = userServices.getAllUser();
        for (CustomUser user : users) {
            addUserToManager(user.getUsername(),user.getPassword(), "USER");
        }
        return userDetailsManager;
    }

    public void addUserToManager(String username, String password, String role) {
        UserDetails userDetails = User.builder()
                .username(username)
                .password(password)
                .roles(role)  // Add roles as needed
                .build();
        userDetailsManager.createUser(userDetails);

    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
}