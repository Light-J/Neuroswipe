package com.nsa.cubric.application.domain;

import org.thymeleaf.util.DateUtils;

import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class PasswordResetToken {
    private String token;
    private Long accountId;
    private Date expiryDate;


    public PasswordResetToken(Long accountId) {
        this.accountId = accountId;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1);

        this.expiryDate = calendar.getTime();

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

    public Long getAccountId() {
        return accountId;
    }

    public Date getExpiryDate() {
        return expiryDate;
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
