package ru.yandex.practicum.filmorate.dao;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.GenreNotFoundException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.Genre;
import ru.yandex.practicum.filmorate.storages.GenreStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

@Component
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> getGenreList() {
        String genre = "select * from GENRES ";
        return jdbcTemplate.query(genre, this::findGenre);
    }

    @Override
    public Genre getGenre(int id) {
        String genre = "select * from GENRES where ID = ?";
        try {
            return jdbcTemplate.queryForObject(genre, this::findGenre, id);
        } catch (RuntimeException e) {
            throw new GenreNotFoundException("Нет такого жанра");
        }
    }

    public void deleteGenre(int id) {
        String a = "delete from FILMS_GENRES where FILM_ID = ?";
        jdbcTemplate.update(a, id);
    }

    public void addGenre(Film film) {
        String addGenre = "insert into FILMS_GENRES (FILM_ID, GENRE_ID) values(?,?)";

        List<Integer> genres = film.getGenres()
                .stream()
                .map(s -> s.getId())
                .collect(Collectors.toList());

        jdbcTemplate.batchUpdate(addGenre, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, film.getId());
                ps.setInt(2, genres.get(i));
            }

            @Override
            public int getBatchSize() {
                return genres.size();
            }
        });
    }


    private Genre findGenre(ResultSet resultSet, int rowNum) throws SQLException {
        return Genre.builder()
                .id(resultSet.getInt("ID"))
                .name(resultSet.getString("NAME"))
                .build();

    }

    @Override
    public void load(List<Film> films) {
        final Map<Integer, Film> filmById = films.stream().collect(Collectors.toMap(Film::getId, identity()));

        String inSql = String.join(",", Collections.nCopies(films.size(), "?"));

        final String sqlQuery = "select * from GENRES g, " +
                "FILMS_GENRES fg where fg.GENRE_ID = g.ID AND fg.FILM_ID in (" + inSql + ")";

        jdbcTemplate.query(sqlQuery, (rs) -> {
            final Film film = filmById.get(rs.getInt("ID"));
            film.addGenre(findGenre(rs, 0));
        }, films.stream().map(Film::getId).toArray());
    }
}
