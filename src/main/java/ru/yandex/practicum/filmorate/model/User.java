package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.validators.BirthDate;

import java.time.LocalDate;

@Data
@Builder
public class User {
    private int id;

    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String login;
    @NotBlank
    private String name;
    @BirthDate
    private LocalDate birthday;
}
