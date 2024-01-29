package ru.yandex.practicum.filmorate.storages;
import ru.yandex.practicum.filmorate.models.Film;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface FilmStorage {
    public List<Film> getAllFilms();

    public Film createFilms(Film film);

    public Film updateFilm(Film film);

    public Map<Integer, Film> getMapFilms();

    public void addLike(Integer filmId, Integer userId);

    public void deleteLike(Integer filmId, Integer userId);

    public Optional<Film> getFilmForId(int id);
}