package com.iongroup.vaadinchat.service;

import com.iongroup.vaadinchat.dao.BookEntityDao;
import com.iongroup.vaadinchat.dao.GenreEntityDao;
import com.iongroup.vaadinchat.entity.BookEntity;
import com.iongroup.vaadinchat.entity.GenreEntity;
import com.iongroup.vaadinchat.entity.dto.BookEntityDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GenreEntityService {
    private GenreEntityDao genreEntityDao;
    private BookEntityDao bookEntityDao;

    public List<GenreEntity> findAll() {
        return genreEntityDao.findAll();
    }

    public void delete(GenreEntity genre) {
        List<BookEntity> books = new ArrayList<>();
        for (BookEntity book : genre.getBooks()) {
            book.getGenres().remove(genre);
            if (book.getGenres().isEmpty()) {
                books.add(book);
            }
            bookEntityDao.save(book);
        }
        if (!books.isEmpty()) {
            bookEntityDao.deleteAll(books);
        }
        genreEntityDao.delete(genre);
    }

    public GenreEntityDao getDao() {
        return genreEntityDao;
    }
}
