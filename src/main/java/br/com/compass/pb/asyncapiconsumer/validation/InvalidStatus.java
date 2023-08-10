package br.com.compass.pb.asyncapiconsumer.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = InvalidStatusValidator.class)
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface InvalidStatus {

    String message() default "This post does not have the appropriate status for this operation";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}