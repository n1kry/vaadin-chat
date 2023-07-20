package com.iongroup.vaadinchat.dao;

import com.iongroup.vaadinchat.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserEntityDao extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    List<UserEntity> findAllByUsernameNot(String username);
}
