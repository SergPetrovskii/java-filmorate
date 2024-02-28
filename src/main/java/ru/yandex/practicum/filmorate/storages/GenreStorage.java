package ru.yandex.practicum.filmorate.storages;

import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.Genre;

import java.util.List;

public interface GenreStorage {

    List<Genre> getGenreList();

    Genre getGenre(int id);

    void addGenre(Film film);

    void load(List<Film> films);

    void deleteGenre(int id);
}
