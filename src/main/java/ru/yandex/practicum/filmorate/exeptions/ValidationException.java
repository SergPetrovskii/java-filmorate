package ru.yandex.practicum.filmorate.exeptions;

public class ValidationException extends RuntimeException {
    public ValidationException(final String massage) {
        super(massage);
    }
}
