package com.project.tj.com.project.tj.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {


    @Autowired
    private JwtAuthenticationEntryPoint point;
    @Autowired
    private JwtAuthenticationFilter filter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .authorizeRequests()
                .requestMatchers("/auth/login").permitAll()
                .requestMatchers("/auth/login/oauth").permitAll()
                .requestMatchers("/api/users").permitAll()
                .requestMatchers("/api/users/reset-password").permitAll()
                .requestMatchers("/api/users/exists/party-auth").permitAll()
                .requestMatchers("/api/users/exists/**").permitAll()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling(ex -> ex.authenticationEntryPoint(point))

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .logout((logout)->
                                        logout
                                                .logoutUrl("/auth/logout")
                                                .clearAuthentication(true)
                                                .logoutSuccessHandler((request, response, authentication) -> response.setStatus(200)));

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}