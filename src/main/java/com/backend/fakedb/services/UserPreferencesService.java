package com.backend.fakedb.services;

import com.backend.fakedb.entities.ProviderEntity;
import com.backend.fakedb.entities.UserPreferencesEntity;
import com.backend.fakedb.entities.UserPreferencesPK;
import com.backend.fakedb.repositories.ProviderRepository;
import com.backend.fakedb.repositories.UserPreferencesRepository;
import com.backend.fakedb.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserPreferencesService {

    @Autowired
    private final UserPreferencesRepository upRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final ProviderRepository providerRepository;

    public UserPreferencesService(UserPreferencesRepository upRepository, UserRepository userRepository, ProviderRepository providerRepository) {
        this.upRepository = upRepository;
        this.userRepository = userRepository;
        this.providerRepository = providerRepository;
    }

    public List<UserPreferencesEntity> getPreferences() {
        return upRepository.findAll();
    }

    public boolean subscribeUserToProviders(int uid, List<Integer> providerIDs) {

        for (var prov_id : providerIDs) {
            var user = userRepository.findById(uid);
            var provider = providerRepository.findById(prov_id);
            if (user.isEmpty() || provider.isEmpty()) {
                return false;
            }
            upRepository.save(new UserPreferencesEntity(new UserPreferencesPK(user.get(), provider.get()), true));
        }
        return true;
    }

    public boolean isSubscribed(int uid, int prov_id) {
        var user = userRepository.findById(uid);
        var provider = providerRepository.findById(prov_id);
        if (user.isEmpty() || provider.isEmpty()) {
            return false;
        }

        var result = upRepository.findById(new UserPreferencesPK(user.get(), provider.get()));
        if (result.isEmpty()) {
            return false;
        }
        return result.get().isSubscribed();
    }

    public List<ProviderEntity> getProviderListForUser(int uid, int skip, int count) {

        var providerIDListForCurrentUser = upRepository.findAll().stream()
                .filter(UserPreferencesEntity::isSubscribed)
                .filter(entry -> entry.getUserID() == uid)
                .map(UserPreferencesEntity::getProviderID)
                .collect(Collectors.toSet());

        return providerRepository.findAll().stream()
                .filter(entry -> providerIDListForCurrentUser.contains(entry.getId()))
                .skip(skip)
                .limit(count)
                .collect(Collectors.toList());
    }

    public boolean updateSubscriptionStatus(int uid, int prov_id, boolean status) {
        var user = userRepository.findById(uid);
        var provider = providerRepository.findById(prov_id);
        if (user.isEmpty() || provider.isEmpty()) {
            return false;
        }

        var preferencesID = new UserPreferencesPK(user.get(), provider.get());
        if (upRepository.findById(preferencesID).isPresent()) {
            upRepository.deleteById(preferencesID);
        }
        upRepository.save(new UserPreferencesEntity(preferencesID, status));
        return true;
    }
}
