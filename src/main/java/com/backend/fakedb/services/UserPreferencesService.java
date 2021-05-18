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

    /**
     * public method for getting all the user's preferences
     * @return a list of UserPreferencesEntities
     */
    public List<UserPreferencesEntity> getPreferences() {
        return upRepository.findAll();
    }

    /**
     * public method that subscribes a specified user to a list of providers
     * @param auth_id user's authentification id
     * @param token user's authentification token
     * @param uid user's id
     * @param providerIDs list of provider's ids
     * @return true, if the process has been successful and false, otherwise
     */
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

    /**
     * public method for checking if a specified user is subscribed to a specified provider
     * @param auth_id user's authentification id
     * @param token user's authentification token
     * @param uid user's id
     * @param prov_id provider's id
     * @return true, if the user is subscribed to the specified provider and false, otherwise
     */
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

    /**
     * public method for getting the providers a specified user is subscribed to
     * @param auth_id user's authentification id
     * @param token user's authentification token
     * @param uid user's id
     * @param skip skip a specified number of entries
     * @param count the specified number of entries the method returns
     * @return a list of ProviderEntities (the providers the user is subscribed to)
     */
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

    /**
     * public method that updates the subscription status of a specified user for a specified provider
     * @param auth_id user's authentification id
     * @param token user's authentification token
     * @param uid user's id
     * @param prov_id provider's id
     * @param status subscription status (true, if the user wants to be subscribed and false, otherwise)
     * @return true, if the process was successful and false, otherwise
     */
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
