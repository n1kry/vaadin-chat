package com.iongroup.vaadinchat.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
@Table(name = "users")
public class UserEntity {
    @ToString.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is mandatory")
//    @Length(min = 5, message = "Nickname should have more than 5 characters")
    @Column(nullable = false, unique = true)
    private String username;

    private String firstName;

    private String lastName;

    @ToString.Exclude
//    @Length(min = 8, message = "Password length must be more than 7 characters")
//    @NotBlank(message = "Password is mandatory")
    @Column(nullable = false)
    private String password;

    @ToString.Exclude
    @Column(nullable = false, unique = true)
//    @Email(message = "Please enter a correct email")
    private String email;
}