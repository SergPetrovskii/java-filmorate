package ru.yandex.practicum.filmorate.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.Mpa;
import ru.yandex.practicum.filmorate.storages.FilmStorage;

import java.sql.*;
import java.sql.Date;
import java.util.*;

@Component
@Qualifier("FilmDbStorage")
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Film> getAllFilms() {
        String sqlQuery = "select * " +
                "from MPA, FILMS " +
                " where MPA.ID = FILMS.MPA_ID ";
        return jdbcTemplate.query(sqlQuery, this::findFilm);
    }

    @Override
    public Film createFilms(Film film) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sqlQuery = "insert into FILMS (NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_ID)"
                + " values(?,?,?,?,?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"ID"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, Date.valueOf(film.getReleaseDate()));
            stmt.setLong(4, film.getDuration());
            stmt.setInt(5, film.getMpa().getId());

            return stmt;
        }, keyHolder);
        film.setId(keyHolder.getKey().intValue());
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (getFilmForId(film.getId()) == null) {
            throw new FilmNotFoundException("Нет такого фильма");
        }

        String sqlQuery = "update FILMS set " +
                "NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ?, MPA_ID = ?  " +
                "where ID = ?";

        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());
        return film;
    }

    @Override
    public void addLike(Integer filmId, Integer userId) {

    }

    @Override
    public void deleteLike(Integer filmId, Integer userId) {

    }

    @Override
    public Film getFilmForId(Integer id) {
        return null;
    }

    @Override
    public Film getFilmForId(int id) {
        String sqlQuery = "select * " +
                "from  MPA, FILMS  where FILMS.MPA_ID = MPA.ID and FILMS.ID = ?";
        try {
            return jdbcTemplate.query(sqlQuery, this::findFilm, id).iterator().next();
        } catch (RuntimeException e) {
            throw new FilmNotFoundException("Не найден");
        }
    }

    private Film findFilm(ResultSet resultSet, int rowNum) throws SQLException {
        return Film.builder()
                .id(resultSet.getInt("ID"))
                .name(resultSet.getString("NAME"))
                .description(resultSet.getString("DESCRIPTION"))
                .releaseDate(resultSet.getDate("RELEASE_DATE").toLocalDate())
                .duration(resultSet.getLong("DURATION"))
                .mpa(Mpa.builder()
                        .id(resultSet.getInt("ID"))
                        .name(resultSet.getString("NAME")).build())
                .genres(new LinkedHashSet<>())
                .build();
    }
}
