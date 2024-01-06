package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;

@SpringBootTest
public class UserValidatorTest {

    private Validator validator;
    @Autowired
    private UserController userController;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void positiveUserValidationTest() {
        User user = User.builder() //should pass
                .email("test@user.ru")
                .login("testUser")
                .name("testUser")
                .birthday(LocalDate.of(1990, 12, 26))
                .build();

        Set<ConstraintViolation<User>> violation = validator.validate(user);
        Assertions.assertTrue(violation.isEmpty());
    }

    @Test
    public void negativeUserIncorrectEmailValidationTest() {
        User user1 = User.builder() //incorrect email
                .email("testuser.ru")
                .login("testUser")
                .name("testUser")
                .birthday(LocalDate.of(1990, 12, 26))
                .build();

        Set<ConstraintViolation<User>> violation1 = validator.validate(user1);
        Assertions.assertFalse(violation1.isEmpty());
    }

    @Test
    public void negativeUserEmptyEmailValidationTest() {
        User user2 = User.builder() //email is absent
                .login("testUser")
                .name("testUser")
                .birthday(LocalDate.of(1990, 12, 26))
                .build();

        Set<ConstraintViolation<User>> violation2 = validator.validate(user2);
        Assertions.assertFalse(violation2.isEmpty());
    }

    @Test
    public void negativeUserEmptyLoginValidationTest() {
        User user3 = User.builder() //login is absent
                .email("test@user.ru")
                .name("testUser")
                .birthday(LocalDate.of(1990, 12, 26))
                .build();

        Set<ConstraintViolation<User>> violation3 = validator.validate(user3);
        Assertions.assertFalse(violation3.isEmpty());
    }

    @Test
    public void negativeUserEmptyBirthDateValidationTest() {
        User user4 = User.builder() //birth date is absent
                .login("testUser")
                .email("test@user.ru")
                .name("testUser")
                .build();

        Set<ConstraintViolation<User>> violation4 = validator.validate(user4);
        Assertions.assertFalse(violation4.isEmpty());
    }

    @Test
    public void negativeUserIncorrectBirthDateValidationTest() {

        User user5 = User.builder() //birth date in the future
                .login("testUser")
                .email("test@user.ru")
                .name("testUser")
                .birthday(LocalDate.of(2050, 12, 26))
                .build();

        Set<ConstraintViolation<User>> violation5 = validator.validate(user5);
        Assertions.assertFalse(violation5.isEmpty());
    }

    @Test
    public void positiveUserEmptyNameValidationTest() {

        User user6 = User.builder()
                .email("test@user.ru")
                .login("testUser")
                .birthday(LocalDate.of(1990, 12, 26))
                .build();
        userController.createUser(user6);
        Assertions.assertEquals(user6.getName(), user6.getLogin());
    }
}