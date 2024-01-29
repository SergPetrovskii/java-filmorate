package ru.yandex.practicum.filmorate.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.validators.BirthDate;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class User {
    @NotNull
    private int id;
    private Set<Integer> friendVault;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String login;

    private String name;
    @BirthDate
    private LocalDate birthday;
}
