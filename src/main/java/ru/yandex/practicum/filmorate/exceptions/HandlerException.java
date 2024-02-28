package ru.yandex.practicum.filmorate.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice("ru.yandex.practicum.filmorate.controllers")
@Slf4j
public class HandlerException {

    @ExceptionHandler({FilmNotFoundException.class, UserNotFoundException.class, IncorrectIDException.class,
            ValidationException.class, GenreNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFoundError(final RuntimeException e) {
        log.debug("Ошибка пользователя.{}", e.getMessage(), e);
        return new ErrorResponse("Ошибка пользователя", e.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequest(final Exception e) {
        log.debug("Ошибка валидации.{}", e.getMessage(), e);
        return new ErrorResponse("Ошибка валидации", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse serverError(final Throwable e) {
        log.debug("Неизвестная ошибка.{}", e.getMessage(), e);
        return new ErrorResponse("Что-то пошло не так(", e.getMessage());
    }
}
