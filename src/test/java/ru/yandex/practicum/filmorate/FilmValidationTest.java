package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;

public class FilmValidationTest {

    private Validator validator;
    private FilmController filmController;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        filmController = new FilmController();
    }

    @Test
    public void filmValidationTest() {
        Film film1 = Film.builder() //should pass
                .name("testFilm")
                .description("testFilm")
                .releaseDate(LocalDate.of(2000, 12, 01))
                .duration(60)
                .build();

        Set<ConstraintViolation<Film>> violation1 = validator.validate(film1);
        Assertions.assertTrue(violation1.isEmpty());

        Film film2 = Film.builder() //name is absent
                .description("testFilm")
                .releaseDate(LocalDate.of(2000, 12, 01))
                .duration(60)
                .build();

        Set<ConstraintViolation<Film>> violation2 = validator.validate(film2);
        Assertions.assertFalse(violation2.isEmpty());

        Film film3 = Film.builder() //description has more than 200 characters
                .name("testFilm")
                .description("-------------------------This description shoud be 200 characters maximum, but this" +
                        " description has more than 200 characters - it has 201 characters------------------------" +
                        "-----------------------------")
                .releaseDate(LocalDate.of(2000, 12, 01))
                .duration(60)
                .build();

        Set<ConstraintViolation<Film>> violation3 = validator.validate(film3);
        Assertions.assertFalse(violation3.isEmpty());

        Film film4 = Film.builder() //date of relise is absent
                .name("testFilm")
                .description("testFilm")
                .duration(60)
                .build();

        Set<ConstraintViolation<Film>> violation4 = validator.validate(film4);
        Assertions.assertFalse(violation4.isEmpty());

        Film film5 = Film.builder() //Negative duration
                .name("testFilm")
                .description("testFilm")
                .releaseDate(LocalDate.of(2000, 12, 01))
                .duration(-60)
                .build();

        Set<ConstraintViolation<Film>> violation5 = validator.validate(film5);
        Assertions.assertFalse(violation5.isEmpty());

        Film film6 = Film.builder() //description is absent
                .name("testFilm")
                .duration(60)
                .releaseDate(LocalDate.of(2000, 12, 01))
                .build();

        Set<ConstraintViolation<Film>> violation6 = validator.validate(film6);
        Assertions.assertFalse(violation6.isEmpty());

        Film film7 = Film.builder() // incorrect date of relise
                .name("testFilm")
                .duration(60)
                .description("testFilm")
                .releaseDate(LocalDate.of(1810, 12, 01))
                .build();

        Set<ConstraintViolation<Film>> violation7 = validator.validate(film7);
        Assertions.assertFalse(violation7.isEmpty());
    }
}