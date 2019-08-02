package com.hasan.movieproject.security.validator;

import com.hasan.movieproject.model.user.dao.UserEntity;
import com.hasan.movieproject.model.user.repository.UserRepository;
import com.hasan.movieproject.security.validator.annotation.ValidEmail;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    private Pattern pattern;
    private Matcher matcher;

    @Autowired
    private UserRepository userRepository;

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";

    @Override
    public void initialize(ValidEmail constraintAnnotation) {

    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return validateEmail(email);
    }

    private boolean validateEmail(String email){
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
/*
    private boolean isUnique(String email){
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);

        if(userEntity.isPresent()){ return false; }

        return true;
    }
 */
}
