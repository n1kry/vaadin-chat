package com.iongroup.vaadinchat.service;

import com.iongroup.vaadinchat.dao.BookEntityDao;
import com.iongroup.vaadinchat.dao.GenreEntityDao;
import com.iongroup.vaadinchat.entity.BookEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookEntityService {

    private BookEntityDao bookEntityDao;

    public List<BookEntity> findAll() {
        return bookEntityDao.findAll();
    }

    public void save(BookEntity book) {
        bookEntityDao.save(book);
    }

    public void delete(BookEntity book) {
        bookEntityDao.delete(book);
    }

    public BookEntityDao getDao() {
        return bookEntityDao;
    }
}
