package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.services.FilmService;
import ru.yandex.practicum.filmorate.storages.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storages.InMemoryUserStorage;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;

@SpringBootTest
public class FilmValidationTest {
    private Validator validator;
    @Autowired
    private FilmController filmController;
    @Autowired
    private UserController userController;
    @Autowired
    private FilmService filmService;
    @Autowired
    private InMemoryUserStorage inMemoryUserStorage;
    @Autowired
    private InMemoryFilmStorage inMemoryFilmStorage;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void positiveFilmValidationTest() {
        Film film1 = Film.builder() //should pass
                .name("testFilm")
                .description("testFilm")
                .releaseDate(LocalDate.of(2000, 12, 1))
                .duration(60)
                .build();

        Set<ConstraintViolation<Film>> violation1 = validator.validate(film1);
        Assertions.assertTrue(violation1.isEmpty());
    }

    @Test
    public void negativeFilmNameAbsentValidationTest() {
        Film film2 = Film.builder() //name is absent
                .description("testFilm")
                .releaseDate(LocalDate.of(2000, 12, 1))
                .duration(60)
                .build();

        Set<ConstraintViolation<Film>> violation2 = validator.validate(film2);
        Assertions.assertFalse(violation2.isEmpty());
    }

    @Test
    public void negativeLongDescriptionFilmValidationTest() {
        Film film3 = Film.builder() //description has more than 200 characters
                .name("testFilm")
                .description("-------------------------This description shoud be 200 characters maximum, but this" +
                        " description has more than 200 characters - it has 201 characters------------------------" +
                        "-----------------------------")
                .releaseDate(LocalDate.of(2000, 12, 1))
                .duration(60)
                .build();

        Set<ConstraintViolation<Film>> violation3 = validator.validate(film3);
        Assertions.assertFalse(violation3.isEmpty());
    }

    @Test
    public void negativeFilmReleaseAbsentValidationTest() {
        Film film4 = Film.builder() //date of release is absent
                .name("testFilm")
                .description("testFilm")
                .duration(60)
                .build();

        Set<ConstraintViolation<Film>> violation4 = validator.validate(film4);
        Assertions.assertFalse(violation4.isEmpty());
    }

    @Test
    public void negativeFilmNegativeDurationValidationTest() {
        Film film5 = Film.builder() //Negative duration
                .name("testFilm")
                .description("testFilm")
                .releaseDate(LocalDate.of(2000, 12, 1))
                .duration(-60)
                .build();

        Set<ConstraintViolation<Film>> violation5 = validator.validate(film5);
        Assertions.assertFalse(violation5.isEmpty());
    }

    @Test
    public void negativeFilmDescriptionAbsentValidationTest() {

        Film film6 = Film.builder() //description is absent
                .name("testFilm")
                .duration(60)
                .releaseDate(LocalDate.of(2000, 12, 1))
                .build();

        Set<ConstraintViolation<Film>> violation6 = validator.validate(film6);
        Assertions.assertFalse(violation6.isEmpty());
    }

    @Test
    public void negativeFilmIncorrectDateOfReleaseValidationTest() {
        Film film7 = Film.builder() //incorrect date of release
                .name("testFilm")
                .duration(60)
                .description("testFilm")
                .releaseDate(LocalDate.of(1810, 12, 1))
                .build();

        Set<ConstraintViolation<Film>> violation7 = validator.validate(film7);
        Assertions.assertFalse(violation7.isEmpty());
    }

    @Test
    public void positiveFilmAddLikeValidationTest() {

        User user = User.builder()
                .email("test@user.ru")
                .login("testUser")
                .name("TestUser")
                .birthday(LocalDate.of(1993, 3, 25))
                .build();
        inMemoryUserStorage.createUser(user);

        Film film8 = Film.builder()
                .name("testFilm8")
                .duration(60)
                .description("testFilm8")
                .releaseDate(LocalDate.of(2000, 12, 1))
                .build();
        inMemoryFilmStorage.createFilms(film8);
        inMemoryFilmStorage.addLike(1, 1);

        Set<ConstraintViolation<Film>> violation8 = validator.validate(film8);
        Assertions.assertTrue(violation8.isEmpty());
    }
}