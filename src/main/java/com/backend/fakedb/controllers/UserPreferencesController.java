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
    public boolean subscribeUserToProviders(@RequestParam(name = "uid", required = true) int uid, @RequestBody List<Integer> providerIDs) {
        return upService.subscribeUserToProviders(uid, providerIDs);
    }

    @GetMapping("/isSubscribed")
    public boolean isSubscribed(@RequestParam(name = "uid", required = true) int uid, @RequestParam(name = "prov_id", required = true) int prov_id) {
        return upService.isSubscribed(uid, prov_id);
    }

    @GetMapping("/getByUserId")
    public List<ProviderEntity> getProviderListForUser(@RequestParam(name = "uid", required = true) int uid,
                                                       @RequestParam(name = "skip", required = false, defaultValue = "0") int skip,
                                                       @RequestParam(name = "count", required = false, defaultValue = "100") int count) {
        return upService.getProviderListForUser(uid, skip, count);
    }

    @PutMapping("/updateSubscriptionStatus")
    public void updateSubscriptionStatus(@RequestParam(name = "uid", required = true) int uid,
                                            @RequestParam(name = "prov_id", required = true) int prov_id,
                                            @RequestParam(name = "status", required = true) boolean status) {
        upService.updateSubscriptionStatus(uid, prov_id, status);
    }
}

