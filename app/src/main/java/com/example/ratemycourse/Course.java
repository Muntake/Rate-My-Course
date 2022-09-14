package com.example.ratemycourse;

public class Course {
    private String courseID;
    private String courseName;
    private int courseRating;
    private String currentInstructor;
    private String courseDepartment;
    private String courseYear;

    public Course() {

    }

    public Course(String courseID, String courseName, int courseRating, String currentInstructor, String courseDepartment, String courseYear) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.courseRating = courseRating;
        this.currentInstructor = currentInstructor;
        this.courseDepartment = courseDepartment;
        this.courseYear = courseYear;
    }

    public String getCourseID() {
        return courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCourseRating() {
        return courseRating;
    }

    public String getCurrentInstructor() {
        return currentInstructor;
    }

    public String getCourseDepartment() {
        return courseDepartment;
    }

    public String getCourseYear() {
        return courseYear;
    }
}
