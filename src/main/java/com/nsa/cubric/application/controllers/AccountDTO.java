package com.nsa.cubric.application.controllers;

import com.nsa.cubric.application.services.registrationUtils.PasswordMatches;
import com.nsa.cubric.application.services.registrationUtils.ValidEmail;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@PasswordMatches()
public class AccountDTO {
    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;


    @NotNull
    @NotEmpty
    private String password;
    private String matchingPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDTO that = (AccountDTO) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(password, that.password) &&
                Objects.equals(matchingPassword, that.matchingPassword);
    }

    @Override
    public int hashCode() {

        return Objects.hash(email, password, matchingPassword);
    }
}
