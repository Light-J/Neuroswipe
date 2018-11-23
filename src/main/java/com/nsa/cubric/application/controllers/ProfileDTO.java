package com.nsa.cubric.application.controllers;

import com.nsa.cubric.application.services.registrationUtils.FirstFourLettersPostCode;
import com.nsa.cubric.application.services.registrationUtils.PasswordMatches;
import com.nsa.cubric.application.services.registrationUtils.ValidEmail;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public class ProfileDTO {
    String username;

    @FirstFourLettersPostCode
    String postcode;

    long loggedInUserId;

    Integer age;
    String gender;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getLoggedInUserId() {
        return loggedInUserId;
    }

    public void setLoggedInUserId(long loggedInUserId) {
        this.loggedInUserId = loggedInUserId;
    }
}
