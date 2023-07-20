package com.iongroup.vaadinchat.entity.dto;

import com.iongroup.vaadinchat.component.ApplicationContextHolder;
import com.iongroup.vaadinchat.dao.GenreEntityDao;
import com.iongroup.vaadinchat.entity.BookEntity;
import com.iongroup.vaadinchat.entity.GenreEntity;
import com.iongroup.vaadinchat.service.GenreEntityService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GenreEntityDto {
    private Long id;

    private String name;

    private List<BookEntityDto> books;

    public static GenreEntityDto genreEntityToDto(GenreEntity genre) {
        GenreEntityService service = ApplicationContextHolder.getBean(GenreEntityService.class);
        genre = service.getDao().findById(genre.getId()).orElse(null);
        GenreEntityDto bookEntityDto = new GenreEntityDto();
        bookEntityDto.setId(genre.getId());
        bookEntityDto.setName(genre.getName());
        bookEntityDto.setBooks(genre.getBooks().stream().map(BookEntityDto::bookEntityToDto).toList());

        return bookEntityDto;
    }

    public static GenreEntity dtoToGenreEntity(GenreEntityDto genreEntityDto) {
        GenreEntityService service = ApplicationContextHolder.getBean(GenreEntityService.class);
        GenreEntity genreEntity = new GenreEntity();
        genreEntity.setId(genreEntityDto.getId());
        genreEntity.setName(genreEntityDto.getName());
        genreEntity.setBooks(service.getDao().findById(genreEntityDto.getId()).get().getBooks());

        return genreEntity;
    }
}
