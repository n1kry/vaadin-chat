package com.iongroup.vaadinchat.dao;

import com.iongroup.vaadinchat.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookEntityDao extends JpaRepository<BookEntity, Long> {
    @Query("SELECT DISTINCT b FROM BookEntity b JOIN FETCH b.genres")
    List<BookEntity> findAllWithGenres();
}
