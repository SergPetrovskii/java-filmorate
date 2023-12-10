package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private int filmNextId = 1;
    private final Map<Integer, Film> films = new HashMap<>();

    @GetMapping
    public List<Film> getAllFilms() {
        log.info("Количество фильмов в коллекции {}", films.size());
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film createFilms(@Valid @RequestBody Film film) {
        film.setId(filmNextId);
        films.put(filmNextId++, film);
        log.info("Фильм {} добавлен в коллекцию, id = {}", film.getName(), film.getId());
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        filmCheckId(film);
        if(films.containsKey(film.getId())) {
            log.info("Фильм c id={} обновлен в коллекции", film.getId());
            films.put(film.getId(), film);
            return film;
        }
        log.info("Попытка изменить фильм с некорректным id");
        throw new ValidationException("Фильма с таким id нет");
    }

    private void filmCheckId(Film film) {
        if(film.getId() < 0) {
            log.info("Попытка добавить фильм с id меньше единицы");
            throw new ValidationException("id фильма не может быть отрицательным или 0");
        }
    }
}