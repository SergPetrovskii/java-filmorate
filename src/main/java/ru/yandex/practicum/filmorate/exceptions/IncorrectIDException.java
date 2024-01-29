package ru.yandex.practicum.filmorate.exceptions;

public class IncorrectIDException extends RuntimeException {
    public IncorrectIDException(final String massage) {
        super(massage);
    }
}
