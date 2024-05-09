package com.project.tj.com.project.tj.repo;

import com.project.tj.com.project.tj.entities.CustomUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<CustomUser,String> {


    void deleteByEmail(String email);

    CustomUser getUserByEmail(String email);

    boolean existsByEmail(String email);


}
