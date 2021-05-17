package com.backend.fakedb.controllers;

import com.backend.fakedb.entities.UserEntity;
import com.backend.fakedb.services.UserService;
import com.backend.fakedb.utilities.LoginResponseWrapper;
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

    @PostMapping("/login")
    public Optional<LoginResponseWrapper> loginUser(
            @RequestParam(name = "username", required = true) Integer id,
            @RequestParam(name = "password", required = true) String password) {
        var maybeToken = userService.loginUser(id, password);
        if (maybeToken.isPresent()) {
            return Optional.of(new LoginResponseWrapper(id, maybeToken.get()));
        }
        return Optional.empty();
    }

    @DeleteMapping("/delete")
    public boolean deleteUser(@RequestParam(name = "id", required = true) Integer id) {
        return userService.deleteUser(id);
    }

    @DeleteMapping("/logout")
    public void logout(
            @RequestHeader(name = "X-Auth-User") Integer auth_id,
            @RequestHeader(name = "X-Auth-Token") String token) {
        userService.logout(auth_id, token);
    }

    @PutMapping("/update")
    public boolean updateUser(
            @RequestHeader(name = "X-Auth-User") Integer auth_id,
            @RequestHeader(name = "X-Auth-Token") String token,
            @RequestParam(name = "id") Integer id,
            @RequestParam(name = "password", required = false, defaultValue = "") String password,
            @RequestParam(name = "avatar", required = false, defaultValue = "") String avatar,
            @RequestParam(name = "bio", required = false, defaultValue = "") String bio) {
        if (auth_id.equals(id)) {
            return userService.updateUser(auth_id, token, id, password, avatar, bio);
        }
        return false;
    }
}
