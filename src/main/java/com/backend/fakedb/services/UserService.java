package com.backend.fakedb.services;

import com.backend.fakedb.entities.SessionEntity;
import com.backend.fakedb.entities.UserEntity;
import com.backend.fakedb.repositories.SessionRepository;
import com.backend.fakedb.repositories.UserRepository;
import com.backend.fakedb.utilities.LoginResponseWrapper;
import org.apache.catalina.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    @Autowired
    public UserService(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
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
            String plaintextPassword = user.getPasswordHash(); // at this point, the password is still plaintext; it hasn't been hashed yet
            user.setPasswordHash(DigestUtils.sha256Hex(plaintextPassword));
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

    @Transactional
    public boolean updateUser(Integer auth_id, String token, Integer id, String password, String avatar, String bio) {
        var maybeUser = userRepository.findById(id);
        if (maybeUser.isPresent()) {
            if (sessionRepository.findAll().stream().anyMatch(s -> s.getUser_id() == auth_id && s.getToken().equals(token))) {
                String newPass = password.equals("") ? maybeUser.get().getPasswordHash() : password;
                String newAvatar = avatar.equals("") ? maybeUser.get().getAvatarUrl() : avatar;
                String newBio = bio.equals("") ? maybeUser.get().getBio() : bio;
                userRepository.update(id, newPass, newAvatar, newBio);
                return true;
            }
        }
        return false;
    }

    public Optional<LoginResponseWrapper> loginUser(String username, String password) {
        var maybeUser = userRepository.findAll().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();

        if (maybeUser.isPresent()) {
            var user = maybeUser.get();
            String hash = DigestUtils.sha256Hex(password);
            if (!hash.equals(user.getPasswordHash())) {
                return Optional.empty();
            }

            var maybeSession = sessionRepository.findAll().stream()
                    .filter(s -> s.getUser_id() == user.getId())
                    .findFirst();
            if (maybeSession.isPresent()) {
                return Optional.of(new LoginResponseWrapper(user.getId(), maybeSession.get().getToken()));
            } else {
                String token = RandomStringUtils.randomAlphanumeric(64);
                var newSession = new SessionEntity((int) (sessionRepository.count() + 1), user.getId(), token);
                sessionRepository.save(newSession);
                return Optional.of(new LoginResponseWrapper(user.getId(), token));
            }
        }
        return Optional.empty();
    }

    public void logout(Integer auth_id, String token) {
        var maybeSession = sessionRepository.findAll().stream()
                .filter(s -> s.getUser_id() == auth_id && s.getToken().equals(token))
                .findFirst();
        if (maybeSession.isPresent()) {
            sessionRepository.deleteById(maybeSession.get().getId());
        }
    }
}
