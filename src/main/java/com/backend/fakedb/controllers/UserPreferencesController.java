package com.backend.fakedb.controllers;

import com.backend.fakedb.entities.ProviderEntity;
import com.backend.fakedb.entities.UserPreferencesEntity;
import com.backend.fakedb.services.UserPreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("preferences")
public class UserPreferencesController {

    private final UserPreferencesService upService;

    @Autowired
    public UserPreferencesController(UserPreferencesService upService) {
        this.upService = upService;
    }

    @GetMapping("/getAll")
    public List<UserPreferencesEntity> getPreferences() {
        return upService.getPreferences();
    }

    @PostMapping("/subscribeUserToProviders")
    public boolean subscribeUserToProviders(
            @RequestHeader(name = "X-Auth-User") Integer auth_id,
            @RequestHeader(name = "X-Auth-Token") String token,
            @RequestParam(name = "uid", required = true) int uid,
            @RequestBody List<Integer> providerIDs) {
        if (auth_id == uid) {
            return upService.subscribeUserToProviders(auth_id, token, uid, providerIDs);
        }
        return false;
    }

    @GetMapping("/isSubscribed")
    public boolean isSubscribed(
            @RequestHeader(name = "X-Auth-User") Integer auth_id,
            @RequestHeader(name = "X-Auth-Token") String token,
            @RequestParam(name = "uid", required = true) int uid,
            @RequestParam(name = "prov_id", required = true) int prov_id) {
        return upService.isSubscribed(auth_id, token, uid, prov_id);
    }

    @GetMapping("/getByUserId")
    public List<ProviderEntity> getProviderListForUser(
            @RequestHeader(name = "X-Auth-User") Integer auth_id,
            @RequestHeader(name = "X-Auth-Token") String token,
            @RequestParam(name = "uid", required = true) int uid,
            @RequestParam(name = "skip", required = false, defaultValue = "0") int skip,
            @RequestParam(name = "count", required = false, defaultValue = "100") int count) {
        return upService.getProviderListForUser(auth_id, token, uid, skip, count);
    }

    @PutMapping("/updateSubscriptionStatus")
    public boolean updateSubscriptionStatus(
            @RequestHeader(name = "X-Auth-User") Integer auth_id,
            @RequestHeader(name = "X-Auth-Token") String token,
            @RequestParam(name = "uid", required = true) int uid,
            @RequestParam(name = "prov_id", required = true) int prov_id,
            @RequestParam(name = "status", required = true) boolean status) {
        if (auth_id.equals(uid)) {
            return upService.updateSubscriptionStatus(auth_id, token, uid, prov_id, status);
        }
        return false;
    }
}

