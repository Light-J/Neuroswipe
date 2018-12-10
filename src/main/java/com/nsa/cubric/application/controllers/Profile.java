package com.nsa.cubric.application.controllers;

import com.nsa.cubric.application.services.registrationUtils.FirstFourLettersPostCode;


public class Profile {
    Long id;

    String username;

    @FirstFourLettersPostCode
    String postcode;

    Long userAccountId;

    Integer age;
    String gender;

    public Profile() {
    }

    public Profile(String username, String postcode, Long userAccountId, Integer age, String gender) {
        this.username = username;
        this.postcode = postcode;
        this.userAccountId = userAccountId;
        this.age = age;
        this.gender = gender;
    }

    public Profile(String username, String postcode, Integer age, String gender) {
        this.username = username;
        this.postcode = postcode;
        this.age = age;
        this.gender = gender;
    }

    public Profile(Long id, String username, String postcode, Integer age, String gender) {
        this.id = id;
        this.username = username;
        this.postcode = postcode;
        this.age = age;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public long getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(long userAccountId) {
        this.userAccountId = userAccountId;
    }
}
