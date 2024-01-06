package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.validators.BirthDate;

import java.time.LocalDate;

@Data
@Builder
public class User {
    @NotNull
    private int id;

    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String login;

    private String name;
    @BirthDate
    private LocalDate birthday;
}
