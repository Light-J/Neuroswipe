package com.nsa.cubric.application.services.accountUtils;

public class EmailExistsException extends Exception {
    public EmailExistsException(String errorMessage){
        super(errorMessage);
    }
}
