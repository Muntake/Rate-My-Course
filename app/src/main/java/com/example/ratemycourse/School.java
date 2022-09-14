package com.example.ratemycourse;

public class School {
    String schoolID;
    String schoolName;
    String schoolProvince;
    String schoolCity;
    String schoolEmail;
    String schoolAddress;
    String schoolPhone;
    String schoolPostalCode;

    public School() {

    }

    public School(String schoolID, String schoolName, String schoolProvince, String schoolCity, String schoolEmail, String schoolAddress, String schoolPhone, String schoolPostalCode) {
        this.schoolID = schoolID;
        this.schoolName = schoolName;
        this.schoolProvince = schoolProvince;
        this.schoolCity = schoolCity;
        this.schoolEmail = schoolEmail;
        this.schoolAddress = schoolAddress;
        this.schoolPhone = schoolPhone;
        this.schoolPostalCode = schoolPostalCode;
    }

    public String getSchoolID() {
        return schoolID;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getSchoolProvince() {
        return schoolProvince;
    }

    public String getSchoolCity() {
        return schoolCity;
    }

    public String getSchoolEmail() {
        return schoolEmail;
    }

    public String getSchoolAddress() {
        return schoolAddress;
    }

    public String getSchoolPhone() {
        return schoolPhone;
    }

    public String getSchoolPostalCode() {
        return schoolPostalCode;
    }
}
