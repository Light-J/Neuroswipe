package com.nsa.cubric.application.domain;

import java.time.Duration;
import java.util.Date;
import java.util.UUID;

public class PasswordResetToken {
    private String token;
    private Long accountId;
    private Date expiryDate;


    public PasswordResetToken(Long accountId) {
        this.accountId = accountId;

        Date currentDate = new Date();
        currentDate.toInstant().plus(Duration.ofHours(24));
        this.expiryDate = currentDate;

        this.token = UUID.randomUUID().toString();
    }

    public PasswordResetToken(String token, Long accountId, Date expiryDate) {
        this.token = token;
        this.accountId = accountId;
        this.expiryDate = expiryDate;
    }

    public String getToken() {
        return token;
    }

    public boolean isValid(){
        Date currentDate = new Date();

        if(currentDate.after(expiryDate)){
            return false;
        } else {
            return true;
        }
    }
}
