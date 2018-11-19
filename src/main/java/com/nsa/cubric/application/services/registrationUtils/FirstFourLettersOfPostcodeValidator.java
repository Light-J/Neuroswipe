package com.nsa.cubric.application.services.registrationUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FirstFourLettersOfPostcodeValidator implements ConstraintValidator<FirstFourLettersPostCode, String> {
        private Pattern pattern;
        private Matcher matcher;
        private static final String POSTCODE_PATTERN = "^([A-z]){2}?([0-9]{2})$|^()$";

        @Override
        public void initialize(FirstFourLettersPostCode constraintAnnotation) {
        }

        @Override
        public boolean isValid(String postcode, ConstraintValidatorContext context){
            return (validatePostcode(postcode));
        }

        private boolean validatePostcode(String postcode) {
            pattern = Pattern.compile(POSTCODE_PATTERN);
            matcher = pattern.matcher(postcode);
            return matcher.matches();
        }
}
