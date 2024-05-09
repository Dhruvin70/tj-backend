package com.project.tj.com.project.tj.services;


import com.project.tj.com.project.tj.entities.CustomUser;
import com.project.tj.com.project.tj.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServices {
    @Autowired
    private final UserRepository userRepository;

    public UserServices(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<CustomUser> getAllUser(){
        List<CustomUser> user = userRepository.findAll();
        return user;
    }

    public CustomUser loadUserByEmail(String email){
        return  userRepository.getUserByEmail(email);
    }
    public CustomUser createUser(CustomUser user) {
        return userRepository.save(user);
    }

    public boolean isEmailExists(String email){return userRepository.existsByEmail(email);}



    public void deleteUserByEmail(String email){
       userRepository.deleteByEmail(email);
    }


}
