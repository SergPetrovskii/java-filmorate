package ru.yandex.practicum.filmorate.exceptions;

public class GenreNotFoundException extends RuntimeException {
    public GenreNotFoundException(final String massage) {
        super(massage);
    }
}
