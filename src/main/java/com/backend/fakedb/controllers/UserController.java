package com.backend.fakedb.controllers;

import com.backend.fakedb.entities.UserEntity;
import com.backend.fakedb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAll")
    public List<UserEntity> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/get")
    public Optional<UserEntity> getUserById(@RequestParam(name = "id", required = true) Integer id) {
        return userService.getUserById(id);
    }

    @PostMapping("/register")
    public boolean registerUser(@RequestBody UserEntity user) {
        return userService.registerUser(user);
    }

    @DeleteMapping("/delete")
    public boolean deleteUser(@RequestParam(name = "id", required = true) Integer id) {
        return userService.deleteUser(id);
    }

    @DeleteMapping("/deleteAll")
    public boolean deleteAllUsers() {
        return userService.deleteAllUsers();
    }

    @PutMapping("/update")
    public boolean updateUser(@RequestParam(name = "id") Integer id,
                              @RequestParam(name = "bio", required = false, defaultValue = "") String bio) {
        return userService.updateUser(id, bio);
    }

    @PostMapping("/dummy")
    public void dummy() {
        for (int i = 1; i < 10; i++) {
            userService.registerUser(new UserEntity(i*i, "nume " + i, "www.avatar" + i + ".com", "Eu sunt user " + i, "user" + i + "@fakedb.com"));
        }
    }
}
