package br.com.compass.pb.asyncapiconsumer.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IdNotExistsValidator.class)
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface InvalidIdNotExists {

    String message() default "This id has not yet been registered in the database";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}