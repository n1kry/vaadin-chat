package com.iongroup.vaadinchat.service;

import com.iongroup.vaadinchat.dao.AuthorEntityDao;
import com.iongroup.vaadinchat.entity.AuthorEntity;
import com.iongroup.vaadinchat.entity.GenreEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthorEntityService {
    private AuthorEntityDao authorEntityDao;

    public List<AuthorEntity> findAll() {
        return authorEntityDao.findAll();
    }

    public AuthorEntityDao getDao() {
        return authorEntityDao;
    }
}
