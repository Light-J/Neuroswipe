package com.nsa.cubric.application.dto;

import com.nsa.cubric.application.services.registrationUtils.PasswordMatches;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ChangePasswordDto {

    @NotNull
    @NotEmpty
    private String password;
    private String matchingPassword;

    public ChangePasswordDto(@NotNull @NotEmpty String password, String matchingPassword) {
        this.password = password;
        this.matchingPassword = matchingPassword;
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
}
