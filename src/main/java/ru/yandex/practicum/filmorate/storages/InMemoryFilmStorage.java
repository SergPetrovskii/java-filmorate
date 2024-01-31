package ru.yandex.practicum.filmorate.storages;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.models.Film;
import javax.validation.ValidationException;
import java.util.*;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private int filmNextId = 1;

    private Map<Integer, Film> films = new HashMap();

    public List<Film> getAllFilms() {
        log.debug("Коллекция фильмов получена, текущее количество {}", films.size());
        return new ArrayList<>(films.values());
    }

    public Film createFilms(Film film) {
        log.debug("Фильм добавлен в коллекцию");
        film.setId(filmNextId);
        film.setLike(new TreeSet<>());
        films.put(filmNextId++, film);

        return film;
    }

    public Film updateFilm(Film film) {
        if (films.containsKey(film.getId())) {
            log.debug("Фильм c id={} обновлен в коллекции", film.getId());
            film.setLike(new TreeSet<>());
            films.put(film.getId(), film);
            return film;
        }
        log.debug("Попытка изменить фильм по не существующему id");
        throw new ValidationException("Фильма с данным id нет");
    }

    public Film getFilmForId(Integer filmId) {
        if (films.containsKey(filmId)) {
        log.debug("Получен фильм с id={}", filmId);
        return films.get(filmId);
        } else {
            throw new FilmNotFoundException("Фильм не найден.");
        }
    }

    public void addLike(Integer filmId, Integer userId) {
        if (films.containsKey(filmId)) {
            Film film = films.get(filmId);
            film.getLike().add(userId);
            log.debug("Пользователем с id={} поставлен лайк фильму с id={}.", userId, filmId);
        } else {
            throw new FilmNotFoundException("Фильм не найден.");
        }
    }

    public void deleteLike(Integer filmId, Integer userId) {
        if (films.containsKey(filmId)) {
            Film film = films.get(filmId);
            film.getLike().remove(userId);
            log.debug("Пользователем с id={} был удален лайк с фильма с id={}.", userId, filmId);
        } else {
            throw new FilmNotFoundException("Фильм не найден.");
        }
    }
}
