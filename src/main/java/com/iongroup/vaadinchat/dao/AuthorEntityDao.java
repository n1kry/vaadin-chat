package com.iongroup.vaadinchat.dao;

import com.iongroup.vaadinchat.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorEntityDao extends JpaRepository<AuthorEntity, Long> {
}
