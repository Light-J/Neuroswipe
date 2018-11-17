package com.nsa.cubric.application.domain;

public class EmailExistsException extends Exception {
    public EmailExistsException(String errorMessage){
        super(errorMessage);
    }
}
