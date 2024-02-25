package ru.yandex.practicum.filmorate.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.models.Mpa;
import ru.yandex.practicum.filmorate.storages.MpaStorage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class MpaDbStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;

    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Mpa> getMpaList() {
        String sqlMpa = "select * from MPA ";
        return jdbcTemplate.query(sqlMpa,this::findMpa);
    }

    @Override
    public Mpa getMpaById(Integer id) {
        String sqlMpa = "select * from MPA where ID = ?";
        try {
            return jdbcTemplate.queryForObject(sqlMpa, this::findMpa, id);
        } catch (RuntimeException e) {
            throw new ValidationException("Нет такого рейтинга");
        }
    }

    private Mpa findMpa(ResultSet resultSet, int i) throws SQLException {
        return Mpa.builder()
                .id(resultSet.getInt("ID"))
                .name(resultSet.getString("NAME"))
                .build();
    }
}