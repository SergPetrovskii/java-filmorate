package ru.yandex.practicum.filmorate.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(final String massage) {
        super(massage);
    }
}
