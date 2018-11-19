package com.nsa.cubric.application.services.registrationUtils;

import com.nsa.cubric.application.controllers.AccountDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation){
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){

        AccountDTO account = (AccountDTO) obj;
        return account.getPassword().equals(account.getMatchingPassword());
    }
}
