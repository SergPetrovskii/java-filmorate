package ru.yandex.practicum.filmorate.storages;

import ru.yandex.practicum.filmorate.models.Film;

import java.util.List;

public interface LikeStorage {

    void deleteLike(Integer filmId, Integer userId);

    void addLike(Integer filmId, Integer userId);

    List<Film> getPopularFilms(Integer end);
}
