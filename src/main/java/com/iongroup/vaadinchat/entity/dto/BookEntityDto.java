package com.iongroup.vaadinchat.entity.dto;

import com.iongroup.vaadinchat.entity.AuthorEntity;
import com.iongroup.vaadinchat.entity.BookEntity;
import com.iongroup.vaadinchat.entity.GenreEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookEntityDto {
    private Long id;

    private String title;

    private List<AuthorEntity> authors;

    private List<GenreEntity> genres;

    private String source;

    public String getStringAuthors() {
        return genres.stream().map(GenreEntity::getName).collect(Collectors.joining(", "));
    }
    public List<AuthorEntity> getAuthors() {
        return authors;
    }

    public String getStringGenres() {
        return genres.stream().map(GenreEntity::getName).collect(Collectors.joining(", "));
    }
    public List<GenreEntity> getGenres() {
        return genres;
    }

    public static BookEntityDto bookEntityToDto(BookEntity book) {
        BookEntityDto bookEntityDto = new BookEntityDto();
        bookEntityDto.setTitle(book.getTitle());
        bookEntityDto.setAuthors(new ArrayList<>(book.getAuthors()));
        bookEntityDto.setGenres(new ArrayList<>(book.getGenres()));
        bookEntityDto.setSource(book.getSource());

        return bookEntityDto;
    }

        public static BookEntity dtoToBookEntity(BookEntityDto book) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle(book.getTitle());
        bookEntity.setAuthors(book.getAuthors());
        bookEntity.setGenres(book.getGenres());
        bookEntity.setSource(book.getSource());

        return bookEntity;
    }
}
