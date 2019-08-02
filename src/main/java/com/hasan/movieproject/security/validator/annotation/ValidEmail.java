package com.hasan.movieproject.security.validator.annotation;

import com.hasan.movieproject.security.validator.EmailValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface ValidEmail {
    String message() default "Invalid email!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
