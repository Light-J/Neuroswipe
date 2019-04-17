package com.nsa.cubric.application.dto;

import com.nsa.cubric.application.services.registrationUtils.FirstFourLettersPostCode;


public class ProfileDto {
    Long id;

    String username;

    @FirstFourLettersPostCode
    String postcode;

    Long userAccountId;

    Integer age;
    String gender;




    Integer disability;
    Integer ethnicity;
    Integer genderSexMatch;
    Integer religion;
    String sex;
    Integer sexualOrientation;
    Integer relationship;

    //Create an empty DTO for initial registration
    public ProfileDto(){}

    public ProfileDto(Long id, String username, String postcode, Long userAccountId, Integer age, String gender) {
        this.id = id;
        this.username = username;
        this.postcode = postcode;
        this.userAccountId = userAccountId;
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


    public Integer getDisability() {
        return disability;
    }

    public void setDisability(Integer disability) {
        this.disability = disability;
    }

    public Integer getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(Integer ethnicity) {
        this.ethnicity = ethnicity;
    }

    public Integer getGenderSexMatch() {
        return genderSexMatch;
    }

    public void setGenderSexMatch(Integer genderSexMatch) {
        this.genderSexMatch = genderSexMatch;
    }

    public Integer getReligion() {
        return religion;
    }

    public void setReligion(Integer religion) {
        this.religion = religion;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getSexualOrientation() {
        return sexualOrientation;
    }

    public void setSexualOrientation(Integer sexualOrientation) {
        this.sexualOrientation = sexualOrientation;
    }

    public Integer getRelationship() {
        return relationship;
    }

    public void setRelationship(Integer relationship) {
        this.relationship = relationship;
    }

    public boolean hasPostcode(){
        if(this.postcode == null){
            return false;
        }
        return true;
    }
}
