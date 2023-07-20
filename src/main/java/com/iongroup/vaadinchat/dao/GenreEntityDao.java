package com.iongroup.vaadinchat.dao;

import com.iongroup.vaadinchat.entity.BookEntity;
import com.iongroup.vaadinchat.entity.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GenreEntityDao extends JpaRepository<GenreEntity, Long> {
    @Transactional
    void deleteGenreEntityById(Long id);
}
