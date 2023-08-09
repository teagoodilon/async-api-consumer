package br.com.compass.pb.asyncapiconsumer.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IdExistsValidator.class)
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidIdExists {

    String message() default "This id has already been registered in the database";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
