package com.iongroup.vaadinchat.service;

import com.iongroup.vaadinchat.dao.UserEntityDao;
import com.iongroup.vaadinchat.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserEntityService {

    private final UserEntityDao userEntityDao;

    private final BCryptPasswordEncoder passwordEncoder;

    public void addUser(final UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userEntityDao.save(user);
    }

    public boolean existsByUserName(String username) {
        return userEntityDao.existsByUsername(username);
    }
    public boolean existsByEmail(String email) {
        return userEntityDao.existsByEmail(email);
    }
}
