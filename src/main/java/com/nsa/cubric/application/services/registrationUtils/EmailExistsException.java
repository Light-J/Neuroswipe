package com.nsa.cubric.application.services.registrationUtils;

public class EmailExistsException extends Exception {
    public EmailExistsException(String errorMessage){
        super(errorMessage);
    }
}
