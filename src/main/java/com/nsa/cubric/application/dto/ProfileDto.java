package com.nsa.cubric.application.dto;

import com.nsa.cubric.application.domain.Profile;
import com.nsa.cubric.application.services.registrationUtils.FirstFourLettersPostCode;

public class ProfileDto {

    String username;
    Integer age;
    Boolean disability;
    Integer ethnicity;
    Boolean genderSexMatch;
    Integer religion;
    String sex;
    Integer sexualOrientation;
    Integer relationship;
    Integer caringResponsibility;

    public ProfileDto(){}

    public ProfileDto(Profile profile){
        this.username = profile.getUsername();
        this.age = profile.getAge();
        this.sex = profile.getSex();
        this.ethnicity = profile.getEthnicityId();
        this.genderSexMatch = profile.getGenderSexMatch();
        this.religion = profile.getReligionId();
        this.disability = profile.getDisability();
        this.sexualOrientation = profile.getSexualOrientationId();
        this.relationship = profile.getRelationshipId();
        this.caringResponsibility = profile.getCaringResponsibility();
    }



    public Integer getCaringResponsibility() {
        return caringResponsibility;
    }
    public void setCaringResponsibility(Integer caringResponsibility) {
        this.caringResponsibility = caringResponsibility;
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
    public Integer getEthnicity() {
        return ethnicity;
    }
    public void setEthnicity(Integer ethnicity) {
        this.ethnicity = ethnicity;
    }
    public Boolean getGenderSexMatch() {
        return genderSexMatch;
    }
    public void setGenderSexMatch(Boolean genderSexMatch) {
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


}
