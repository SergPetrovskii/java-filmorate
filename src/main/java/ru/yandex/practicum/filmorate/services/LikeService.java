package ru.yandex.practicum.filmorate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.storages.LikeStorage;

import java.util.List;

@Service
public class LikeService {

    private LikeStorage likeStorage;

    @Autowired
    public LikeService(LikeStorage likeStorage) {
        this.likeStorage = likeStorage;
    }

    public void deleteLike(Integer filmId, Integer userId) {
        likeStorage.deleteLike(filmId, userId);
    }

    public void addLike(Integer filmId, Integer userId) {
        likeStorage.addLike(filmId, userId);
    }

    public List<Film> getPopularFilms(Integer end) {
        return likeStorage.getPopularFilms(end);
    }
}
