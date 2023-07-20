package com.iongroup.vaadinchat.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "book")
@ToString
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private List<AuthorEntity> authors;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private List<GenreEntity> genres;

    private String source;
}
