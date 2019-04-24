package com.nsa.cubric.application.domain;

import com.nsa.cubric.application.dto.ProfileDto;

public class Profile {
    Long id;
    Long accountId;

    String username;
    Integer age;
    Boolean disability;
    Boolean genderSexMatch;
    String sex;

    Integer ethnicityId;
    Integer religionId;
    Integer sexualOrientationId;
    Integer relationshipId;
    Integer caringResponsibility;

    public Profile(Long id, Long accountId, String username, Integer age, Boolean disability, Boolean genderSexMatch, String sex, Integer ethnicityId, Integer religionId, Integer sexualOrientationId, Integer relationshipId, Integer caringResponsibility) {
        this.id = id;
        this.accountId = accountId;
        this.username = username;
        this.age = age;
        this.disability = disability;
        this.genderSexMatch = genderSexMatch;
        this.sex = sex;
        this.ethnicityId = ethnicityId;
        this.religionId = religionId;
        this.sexualOrientationId = sexualOrientationId;
        this.relationshipId = relationshipId;
        this.caringResponsibility = caringResponsibility;
    }

    public Profile(ProfileDto profileDto, Long profileId, Long accountId){
        this.id = profileId;
        this.accountId = accountId;
        this.username = profileDto.getUsername();
        this.age = profileDto.getAge();
        this.disability = profileDto.getDisability();
        this.genderSexMatch = profileDto.getGenderSexMatch();
        this.sex = profileDto.getSex();
        this.ethnicityId = profileDto.getEthnicity();
        this.religionId = profileDto.getReligion();
        this.sexualOrientationId = profileDto.getSexualOrientation();
        this.relationshipId = profileDto.getRelationship();
        this.caringResponsibility = profileDto.getCaringResponsibility();
    }

    public Integer getCaringResponsibility() {
        return caringResponsibility;
    }
    public void setCaringResponsibility(Integer caringResponsibility) {
        this.caringResponsibility = caringResponsibility;
    }
    public Long getAccountId() {
        return accountId;
    }
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public Boolean getDisability() {
        return disability;
    }
    public void setDisability(Boolean disability) {
        this.disability = disability;
    }
    public Boolean getGenderSexMatch() {
        return genderSexMatch;
    }
    public void setGenderSexMatch(Boolean genderSexMatch) {
        this.genderSexMatch = genderSexMatch;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public Integer getEthnicityId() {
        return ethnicityId;
    }
    public void setEthnicityId(Integer ethnicityId) {
        this.ethnicityId = ethnicityId;
    }
    public Integer getReligionId() {
        return religionId;
    }
    public void setReligionId(Integer religionId) {
        this.religionId = religionId;
    }
    public Integer getSexualOrientationId() {
        return sexualOrientationId;
    }
    public void setSexualOrientationId(Integer sexualOrientationId) {
        this.sexualOrientationId = sexualOrientationId;
    }
    public Integer getRelationshipId() {
        return relationshipId;
    }
    public void setRelationshipId(Integer relationshipId) {
        this.relationshipId = relationshipId;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}

