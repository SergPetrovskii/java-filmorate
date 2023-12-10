package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;

public class UserValidatorTest {

    private Validator validator;
    private UserController userController;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        userController = new UserController();
    }

    @Test
    public void userValidationTest() {
        User user = User.builder() //should pass
                .email("test@user.ru")
                .login("testUser")
                .name("testUser")
                .birthday(LocalDate.of(1990, 12, 26))
                .build();


        Set<ConstraintViolation<User>> violation = validator.validate(user);
        Assertions.assertTrue(violation.isEmpty());

        User user1 = User.builder() //incorrect email
                .email("testuser.ru")
                .login("testUser")
                .name("testUser")
                .birthday(LocalDate.of(1990, 12, 26))
                .build();

        Set<ConstraintViolation<User>> violation1 = validator.validate(user1);
        Assertions.assertFalse(violation1.isEmpty());

        User user2 = User.builder() //email is absent
                .login("testUser")
                .name("testUser")
                .birthday(LocalDate.of(1990, 12, 26))
                .build();

        Set<ConstraintViolation<User>> violation2 = validator.validate(user2);
        Assertions.assertFalse(violation2.isEmpty());

        User user3 = User.builder() //login is absent
                .email("test@user.ru")
                .name("testUser")
                .birthday(LocalDate.of(1990, 12, 26))
                .build();

        Set<ConstraintViolation<User>> violation3 = validator.validate(user3);
        Assertions.assertFalse(violation3.isEmpty());

        User user4 = User.builder() //birth date is absent
                .login("testUser")
                .email("test@user.ru")
                .name("testUser")
                .build();

        Set<ConstraintViolation<User>> violation4 = validator.validate(user4);
        Assertions.assertFalse(violation4.isEmpty());

        User user5 = User.builder() //birth date in the future
                .login("testUser")
                .email("test@user.ru")
                .name("testUser")
                .birthday(LocalDate.of(2050, 12, 26))
                .build();

        Set<ConstraintViolation<User>> violation5 = validator.validate(user5);
        Assertions.assertFalse(violation5.isEmpty());

        User user6 = User.builder()
                .email("test@user.ru")
                .login("testUser")
                .birthday(LocalDate.of(1990, 12, 26))
                .build();
        userController.createUser(user6);
        Assertions.assertEquals(user1.getName(), user1.getLogin());
    }
}