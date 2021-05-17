package com.backend.fakedb.services;

import com.backend.fakedb.entities.ProviderEntity;
import com.backend.fakedb.entities.UserPreferencesEntity;
import com.backend.fakedb.entities.UserPreferencesPK;
import com.backend.fakedb.repositories.ProviderRepository;
import com.backend.fakedb.repositories.SessionRepository;
import com.backend.fakedb.repositories.UserPreferencesRepository;
import com.backend.fakedb.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserPreferencesService {

    private final UserPreferencesRepository upRepository;
    private final UserRepository userRepository;
    private final ProviderRepository providerRepository;
    private final SessionRepository sessionRepository;

    @Autowired
    public UserPreferencesService(
            UserPreferencesRepository upRepository, UserRepository userRepository,
            ProviderRepository providerRepository, SessionRepository sessionRepository) {
        this.upRepository = upRepository;
        this.userRepository = userRepository;
        this.providerRepository = providerRepository;
        this.sessionRepository = sessionRepository;
    }

    public List<UserPreferencesEntity> getPreferences() {
        return upRepository.findAll();
    }

    public boolean subscribeUserToProviders(int auth_id, String token, int uid, List<Integer> providerIDs) {
        if (sessionRepository.findAll().stream().noneMatch(session -> session.getUser_id() == auth_id && session.getToken().equals(token))) {
            return false;
        }

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

    public boolean isSubscribed(int auth_id, String token, int uid, int prov_id) {
        if (sessionRepository.findAll().stream().noneMatch(session -> session.getUser_id() == auth_id && session.getToken().equals(token))) {
            return false;
        }

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

    public List<ProviderEntity> getProviderListForUser(int auth_id, String token, int uid, int skip, int count) {
        if (sessionRepository.findAll().stream().noneMatch(session -> session.getUser_id() == auth_id && session.getToken().equals(token))) {
            return null;
        }

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

    public boolean updateSubscriptionStatus(int auth_id, String token, int uid, int prov_id, boolean status) {
        if (sessionRepository.findAll().stream().noneMatch(session -> session.getUser_id() == auth_id && session.getToken().equals(token))) {
            return false;
        }

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
