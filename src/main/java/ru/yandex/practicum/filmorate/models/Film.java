package ru.yandex.practicum.filmorate.models;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.validators.ReleaseDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.LinkedHashSet;


@Data
@Builder
public class Film {
    private int id;
    @NotBlank
    private String name;
    @NotNull
    @Size(max = 200)
    private String description;
    @NotNull
    @ReleaseDate
    private LocalDate releaseDate;
    @Positive
    private long duration;
    private LinkedHashSet<Genre> genres;
    @NotNull
    private Mpa mpa;

    public void addGenre(Genre genre) {
        genres.add(genre);
    }

}
