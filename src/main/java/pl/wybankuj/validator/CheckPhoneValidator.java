package pl.wybankuj.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class CheckPhoneValidator implements ConstraintValidator<CheckPhone, String> {

    @Override
    public void initialize(CheckPhone constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile("^\\d{9}$");
        return pattern.matcher(value).matches();
    }
}
