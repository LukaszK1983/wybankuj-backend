package pl.wybankuj.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class CheckZipCodeValidator implements ConstraintValidator<CheckZipCode, String> {

    @Override
    public void initialize(CheckZipCode constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile("^\\d{2}-\\d{3}$");
        return pattern.matcher(value).matches();
    }
}
