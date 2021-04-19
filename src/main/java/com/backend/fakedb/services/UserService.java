package com.backend.fakedb.services;

import com.backend.fakedb.entities.UserEntity;
import com.backend.fakedb.repositories.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public boolean registerUser(UserEntity user) {
        var maybeUser = userRepository.findAll().stream()
                .filter(u -> u.getUsername().equals(user.getUsername()))
                .findFirst();
        if (maybeUser.isEmpty()) {
            userRepository.save(user);
            return true;
        }

        return false;
    }

    public boolean deleteUser(Integer id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean deleteAllUsers() {
        userRepository.deleteAll();
        return true;
    }

    @Transactional
    public boolean updateUser(Integer id, String bio) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.update(bio, id);
            return true;
        }

        return false;
    }
}
