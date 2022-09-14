package com.example.ratemycourse;

import java.io.Serializable;

public class User implements Serializable {
    String id;
    String username;
    String email;
    String userFullName;
    String userMajor;
    String password;
    float userRating;
    int userNumberOfReviews;
    int userNumberOfEndorsements;
    String interests;

    public User(){

    }

    public User(String id, String username, String email, String userFullName, String userMajor, String password, float userRating, int userNumberOfReviews, int userNumberOfEndorsements, String interests){
        this.id = id;
        this.username = username;
        this.email = email;
        this.userFullName = userFullName;
        this.userMajor = userMajor;
        this.password = password;
        this.userRating = userRating;
        this.userNumberOfReviews = userNumberOfReviews;
        this.userNumberOfEndorsements = userNumberOfEndorsements;
        this.interests = interests;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserMajor() {
        return userMajor;
    }

    public void setUserMajor(String userMajor) {
        this.userMajor = userMajor;
    }

    public float getUserRating() {
        return userRating;
    }

    public void setUserRating(float userRating) {
        this.userRating = userRating;
    }

    public int getUserNumberOfReviews() {
        return userNumberOfReviews;
    }

    public void setUserNumberOfReviews(int userNumberOfReviews) {
        this.userNumberOfReviews = userNumberOfReviews;
    }

    public int getUserNumberOfEndorsements() {
        return userNumberOfEndorsements;
    }

    public void setUserNumberOfEndorsements(int userNumberOfEndorsements) {
        this.userNumberOfEndorsements = userNumberOfEndorsements;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
