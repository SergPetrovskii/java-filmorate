package ru.yandex.practicum.filmorate.storages;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
        log.info("Попытка изменить фильм по не существующему id");
        throw new ValidationException("Фильма с данным id нет");
    }

    public Optional<Film> getFilmForId(int id) {
        log.debug("Получен фильм с id={}", id);
        return Optional.ofNullable(films.get(id));
    }

    public Map<Integer, Film> getMapFilms() {
        return new HashMap<>(films);
    }

    public void addLike(Integer filmId, Integer userId) {
        films.get(filmId).getLike().add(userId);
    }

    public void deleteLike(Integer filmId, Integer userId) {
        films.get(filmId).getLike().remove(userId);
    }
}