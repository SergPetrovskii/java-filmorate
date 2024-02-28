package ru.yandex.practicum.filmorate.storages;

import ru.yandex.practicum.filmorate.models.Film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface FilmStorage {
    List<Film> getAllFilms();

    Film createFilms(Film film);

    Film updateFilm(Film film);


    Film getFilmForId(Integer id);

    Film findFilm(ResultSet resultSet, int rowNum) throws SQLException;
}