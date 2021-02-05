package pl.wybankuj.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CheckZipCodeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckZipCode {
    String message() default "{checkZipCode.error.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
