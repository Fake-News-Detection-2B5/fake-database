package com.backend.fakedb.services;

import com.backend.fakedb.entities.UserEntity;
import com.backend.fakedb.repositories.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Public method that returns all users from the database.
     * @return a List of UserEntity representing all users from the database
     */
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    /**
     * Public method that gets an user by a specified ID.
     * @param id the ID to search by
     * @return if the user is found, it is returned as a UserEntity. Otherwise, null is returned.
     */
    public UserEntity getById(Integer id) {
        Optional<UserEntity> returnUser = userRepository.findById(id);
        if (returnUser.isEmpty()) return null;
        return returnUser.get();
    }
}
