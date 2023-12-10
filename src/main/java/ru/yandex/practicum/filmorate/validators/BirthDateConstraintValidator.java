package ru.yandex.practicum.filmorate.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class BirthDateConstraintValidator implements ConstraintValidator<BirthDate, LocalDate> {
    private static final LocalDate NOW = LocalDate.now();

    @Override
    public void initialize(BirthDate birthDateDate) {
    }

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext cxt) {
        if(birthDate == null) {
            return false;
        }
        return birthDate.isBefore(NOW);
    }
}
