package com.nsa.cubric.application.controllers;

import com.nsa.cubric.application.services.registrationUtils.PasswordMatches;
import com.nsa.cubric.application.services.registrationUtils.ValidEmail;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@PasswordMatches()
public class ProfileDTO {
    String username;
    String postcode;
    Integer age;
    String gender;

}
