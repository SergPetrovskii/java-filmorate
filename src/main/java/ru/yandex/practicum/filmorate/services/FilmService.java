package ru.yandex.practicum.filmorate.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.storages.FilmStorage;

import java.util.List;

@Service
@Slf4j
public class FilmService {
    private final FilmStorage filmStorage;
    private final LikeService likeService;
    private final GenreService genreService;

    @Autowired
    public FilmService(FilmStorage filmStorage, LikeService likeService, GenreService genreService) {
        this.filmStorage = filmStorage;
        this.likeService = likeService;
        this.genreService = genreService;
    }

    public void addLike(Integer filmId, Integer userId) {
        if (userId < 0) {
            throw new ValidationException("id пользователя должен быть положительным");
        } else {
            likeService.addLike(filmId, userId);
        }
    }

    public void deleteLike(Integer filmId, Integer userId) {
        if (userId < 0) {
            throw new ValidationException("id пользователя должен быть положительным");
        } else {
            likeService.deleteLike(filmId, userId);
        }
    }

    public List<Film> getPopularFilms(Integer end) {
        log.debug("Получен список популярных фильмов колличесвом {} фильмов.", end);
        List<Film> films = likeService.getPopularFilms(end);
        genreService.load(films);
        return films;
    }

    public List<Film> getAllFilms() {
        List<Film> films = filmStorage.getAllFilms();
        genreService.load(films);
        return films;
    }

    public Film createFilms(Film film) {
        filmStorage.createFilms(film);
        if (film.getGenres() != null) {
            genreService.addGenre(film);
        }
        return film;
    }

    public Film updateFilm(Film film) {
        if (film.getGenres() != null) {
            genreService.deleteGenre(film.getId());
            genreService.addGenre(film);
        }
        filmStorage.updateFilm(film);
        return film;
    }

    public Film getFilmForId(Integer id) {
        if (id != null) {
            Film film = filmStorage.getFilmForId(id);
            genreService.load(List.of(film));
            return film;
        } else {
            throw new ValidationException("Необходимо указать id фильма - вместо id пришел null");
        }
    }
}