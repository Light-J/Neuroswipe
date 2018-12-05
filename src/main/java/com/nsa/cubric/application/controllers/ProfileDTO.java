package com.nsa.cubric.application.controllers;

import com.nsa.cubric.application.services.registrationUtils.FirstFourLettersPostCode;
import com.nsa.cubric.application.services.registrationUtils.PasswordMatches;
import com.nsa.cubric.application.services.registrationUtils.ValidEmail;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public class ProfileDTO {
    Integer id;

    String username;

    @FirstFourLettersPostCode
    String postcode;

    long loggedInUserId;

    Integer age;
    String gender;

    public ProfileDTO() {
    }

    public ProfileDTO(String username, String postcode, long loggedInUserId, Integer age, String gender) {
        this.username = username;
        this.postcode = postcode;
        this.loggedInUserId = loggedInUserId;
        this.age = age;
        this.gender = gender;
    }

    public ProfileDTO(String username, String postcode, Integer age, String gender) {
        this.username = username;
        this.postcode = postcode;
        this.age = age;
        this.gender = gender;
    }

    public ProfileDTO(Integer id, String username, String postcode, Integer age, String gender) {
        this.id = id;
        this.username = username;
        this.postcode = postcode;
        this.age = age;
        this.gender = gender;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
