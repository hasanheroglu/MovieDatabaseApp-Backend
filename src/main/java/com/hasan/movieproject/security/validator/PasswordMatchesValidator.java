package com.hasan.movieproject.security.validator;

import com.hasan.movieproject.model.user.dto.PasswordConfirmationDto;
import com.hasan.movieproject.security.validator.annotation.PasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, PasswordConfirmationDto> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(PasswordConfirmationDto passwordConfirmationDto, ConstraintValidatorContext constraintValidatorContext) {
        return passwordConfirmationDto.getPassword().equals(passwordConfirmationDto.getMatchingPassword());
    }
}
