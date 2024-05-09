package com.project.tj.com.project.tj.controllers;

import com.project.tj.com.project.tj.entities.JwtRequest;
import com.project.tj.com.project.tj.entities.JwtResponse;
import com.project.tj.com.project.tj.security.JwtHelper;
import com.project.tj.com.project.tj.services.UserServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UserServices userServices;


    @Autowired
    private JwtHelper helper;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {

        JwtResponse response = null;
        try {
            this.doAuthenticate(request.getEmail(), request.getPassword());


            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            String token = this.helper.generateToken(userDetails);

            response = JwtResponse.builder()
                    .jwtToken(token)
                    .username(userDetails.getUsername()).build();
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
            }
    }

    @PostMapping("/login/oauth")
    public ResponseEntity<JwtResponse> loginOauth(@RequestBody JwtRequest request) {

        logger.info(request.getEmail() + "+" + request.getGoogleId());
//        this.doAuthenticate(request.getEmail(), String.valueOf(Double.parseDouble(request.getGid())));

        JwtResponse response = null;
        logger.info(String.valueOf(userServices.isEmailExists(request.getEmail())));

        if (userServices.isEmailExists(request.getEmail())) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            String token = this.helper.generateToken(userDetails);

            response = JwtResponse.builder()
                    .jwtToken(token)
                    .username(userDetails.getUsername()).build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);


        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }

}
