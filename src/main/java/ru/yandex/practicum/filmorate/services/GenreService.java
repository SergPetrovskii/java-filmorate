package ru.yandex.practicum.filmorate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.Genre;
import ru.yandex.practicum.filmorate.storages.GenreStorage;

import java.util.List;

@Service
public class GenreService {
    private GenreStorage genreStorage;

    @Autowired
    public GenreService(GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public List<Genre> getGenreList() {
        return genreStorage.getGenreList();
    }

    public Genre getGenre(int id) {
        return genreStorage.getGenre(id);
    }

    public void addGenre(Film film) {
        genreStorage.addGenre(film);
    }

    public void deleteGenre(int id) {
        genreStorage.deleteGenre(id);
    }

    public void load(List<Film> films) {
        genreStorage.load(films);
    }
}
