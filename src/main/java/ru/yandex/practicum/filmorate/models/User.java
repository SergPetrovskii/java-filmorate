package ru.yandex.practicum.filmorate.models;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.validators.BirthDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class User {
    @NotNull
    private int id;
    private Set<Integer> friendIds;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9^\\S]+$")
    private String login;
    private String name;
    @BirthDate
    private LocalDate birthday;
}
