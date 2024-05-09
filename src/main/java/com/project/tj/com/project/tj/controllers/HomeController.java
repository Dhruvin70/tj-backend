package com.project.tj.com.project.tj.controllers;

import com.project.tj.com.project.tj.config.MyConfig;
import com.project.tj.com.project.tj.entities.CustomUser;
import com.project.tj.com.project.tj.entities.JwtResponse;
import com.project.tj.com.project.tj.entities.PasswordReset;
import com.project.tj.com.project.tj.services.MailService;
import com.project.tj.com.project.tj.services.UserServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class HomeController {
    @Autowired
    private UserServices userServices;
    @Autowired
    private MyConfig myConfig;

    @Autowired
    private UserDetailsManager userDetailsManager;

    @Autowired
    private MailService mailService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping(path = "/users/get-users")
    public List<CustomUser> getAllUsers() {
        return userServices.getAllUser();
    }

    @PostMapping(path="/users/otp-verification-email")
    public String sendOTPByEmail(@RequestParam String email, @RequestParam String otp){
        mailService.sendEmail(email, otp);
        return "OTP sent successfully to " + email;
    }

    @GetMapping(path = "/users/current-user")
    public String getLoggedInUser(Principal principal) {
        return principal.getName();
    }


    @GetMapping(path = "/users/exists/{email}")
    public boolean isUserExists(@PathVariable String email) {
        return userServices.isEmailExists(email);
    }

//    @GetMapping(path = "/users/exists/party-auth/gid={gid}&email={email}")
//    public  ResponseEntity<?>  isUserGidExists(@PathVariable String gid , @PathVariable String email) {
//        String pass = "pass";
//        if (userServices.isEmailExists(email) && userServices.isGidExists((gid))) {
//            pass = userServices.getPasswordByGid(gid);
//
//            logger.info(pass);
//
//        }
//        return ResponseEntity.status(HttpStatus.CREATED).body(pass);
//    }
    @PostMapping(path = "/users")
    public ResponseEntity<?> createUser(@RequestBody CustomUser user) {
        if (userServices.isEmailExists(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        } else {
            // Create the user in your database
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userServices.createUser(user);

            myConfig.addUserToManager(user.getUsername(), user.getPassword(), "USER");
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        }
    }

    @PostMapping(path="/users/reset-password")
    public ResponseEntity<?> resetMyPassword(@RequestBody PasswordReset passwordReset){
logger.info(passwordReset.getEmail());

        try {
            CustomUser user = userServices.loadUserByEmail(passwordReset.getEmail());

            if(user != null) {
                user.setPassword(passwordEncoder.encode(passwordReset.getPassword()));
                UserDetails updatedUserDetails = User.withUsername(user.getEmail())
                        .password(user.getPassword())
                        .build();
                userDetailsManager.updateUser(updatedUserDetails);
                return ResponseEntity.status(HttpStatus.OK).body("PASSWORD CHANGED");
            }else  throw new UsernameNotFoundException("User not found");

        } catch (Exception e) {

            throw new UsernameNotFoundException("User not found");

        }
    }

    @GetMapping(path = "/user/{email}")
    public CustomUser getUserByEmail(@PathVariable String email) {
        return userServices.loadUserByEmail(email);
    }

    @DeleteMapping(path = "/users/{email}")
    public void deleteUserByEmail(@PathVariable String email) {
        userServices.deleteUserByEmail(email);
        // You may also need to delete the user from the UserDetailsManager

    }
}