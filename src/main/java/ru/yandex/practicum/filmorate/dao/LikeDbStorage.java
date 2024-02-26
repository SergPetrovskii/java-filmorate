package ru.yandex.practicum.filmorate.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.Mpa;
import ru.yandex.practicum.filmorate.storages.LikeStorage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;

@Component
public class LikeDbStorage implements LikeStorage {

    private final JdbcTemplate jdbcTemplate;


    public LikeDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void deleteLike(Integer filmId, Integer userId) {
        String sqlLike = "delete from LIKES where FILM_ID = ? and USER_ID = ?";
        jdbcTemplate.update(sqlLike, filmId, userId);
    }

    @Override
    public void addLike(Integer filmId, Integer userId) {
        String sqlLike = "insert into LIKES (FILM_ID, USER_ID)"
                + " values(?,?)";
        jdbcTemplate.update(sqlLike, filmId, userId);
    }

    @Override
    public List<Film> getPopularFilms(Integer end) {
        String sqlFilms = "select * " +
                " from LIKES right JOIN FILMS ON LIKES.FILM_ID = FILMS.ID " +
                " join MPA on FILMS.MPA_ID = MPA.MPA_ID " +
                " GROUP BY FILMS.ID " +
                " ORDER BY COUNT(USER_ID) " +
                " DESC limit ?";
        List<Film> filmId = jdbcTemplate.query(sqlFilms, this::getFilmId, end);

        return filmId;
    }

    private Film getFilmId(ResultSet resultSet, int rowNum) throws SQLException {
        return Film.builder()
                .id(resultSet.getInt("ID"))
                .name(resultSet.getString("NAME"))
                .description(resultSet.getString("DESCRIPTION"))
                .releaseDate(resultSet.getDate("RELEASE_DATE").toLocalDate())
                .duration(resultSet.getLong("DURATION"))
                .mpa(Mpa.builder()
                        .id(resultSet.getInt("MPA_ID"))
                        .name(resultSet.getString("MPA_NAME")).build())
                .genres(new LinkedHashSet<>())
                .build();
    }
}
