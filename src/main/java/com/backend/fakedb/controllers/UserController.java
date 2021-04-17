package com.backend.fakedb.controllers;

import com.backend.fakedb.entities.UserEntity;
import com.backend.fakedb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    public List<UserEntity> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/get/{id}")
    public Optional<UserEntity> getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @PostMapping("/register")
    public boolean registerUser(@RequestBody UserEntity user) {
        return userService.registerUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteUser(@PathVariable Integer id) {
        return userService.deleteUser(id);
    }

    @DeleteMapping("/deleteAll")
    public boolean deleteAllUsers() {
        return userService.deleteAllUsers();
    }

    @PutMapping("/update/{id}")
    public boolean updateUser(@PathVariable Integer id, @RequestBody UserEntity user) {
        return userService.updateUser(id, user);
    }
}
