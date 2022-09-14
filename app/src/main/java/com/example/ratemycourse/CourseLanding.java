package com.example.ratemycourse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CourseLanding extends AppCompatActivity {

    private Button new_review;
    private TextView courseTitle;
    private RatingBar courseRating;
    private TextView courseDept;
    private TextView courseYear;
    private TextView schoolName;

    // rating variables used for overall course rating
    float overall = 0;

    public static final String COURSE_ID = "courseID";
    public static final String COURSE_NAME_RATING = "courseNameRate";
    public static final String SCHOOL_NAME_RATING = "schoolNameRate";

    DatabaseReference databaseRatings;

    // list variables to display ratings
    private ListView listReviews;
    private List<Rating> ratings;

    private String courseID;
    private String schoolID;
    private String name;
    private String prof;
    private String dept;
    private String year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_landing);

        databaseRatings = FirebaseDatabase.getInstance().getReference("ratings");

        new_review = findViewById(R.id.newRev);

        courseTitle = findViewById(R.id.courseTitle);
        courseRating = findViewById(R.id.courseRating);
        courseDept = findViewById(R.id.courseDept);
        courseYear = findViewById(R.id.courseYear);
        schoolName = findViewById(R.id.schoolName);

        Intent intent = getIntent();

        courseID = intent.getStringExtra(AddCourseActivity.COURSE_ID);
        schoolID = intent.getStringExtra(AddCourseActivity.SCHOOL_ID);
        name = intent.getStringExtra(AddCourseActivity.COURSE_NAME);
        prof = intent.getStringExtra(AddCourseActivity.COURSE_INSTRUCTOR);
        dept = intent.getStringExtra(AddCourseActivity.COURSE_DEPT);
        year = intent.getStringExtra(AddCourseActivity.COURSE_YEAR);
        String school = intent.getStringExtra(AddCourseActivity.SCHOOL_NAME);

        courseTitle.setText(name);
        courseDept.setText(dept);
        courseYear.setText(year);
        schoolName.setText(school);

        listReviews = findViewById(R.id.listReviews);
        ratings = new ArrayList<>();

        new_review.setOnClickListener(new View.OnClickListener() {
            @Override
            //open the new review page.
            public void onClick(View view) {
                Intent intent = new Intent(CourseLanding.this, NewRating.class);
                intent.putExtra(COURSE_NAME_RATING, name);
                intent.putExtra(SCHOOL_NAME_RATING, school);
                intent.putExtra(COURSE_ID, courseID);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseRatings.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ratings.clear();
                int courseRatings = 0;
                for (DataSnapshot ratingSnapshot: dataSnapshot.getChildren()) {
                    Rating rating = ratingSnapshot.getValue(Rating.class);
                    if (rating.getCourseID().equals(courseID)) {
                        courseRatings += rating.getRating();
                        ratings.add(rating);
                    }
                }
                RatingList adapter = new RatingList(CourseLanding.this, ratings);
                listReviews.setAdapter(adapter);
                if (ratings.size() > 0) {
                    overall = courseRatings / ratings.size();
                }
                courseRating.setRating(overall);

                // update course overall rating
                updateCourseRating(courseID, name, (int) overall, prof, dept, year);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public boolean updateCourseRating(String courseID, String name, int newRating, String prof, String dept, String year) {
        DatabaseReference databaseCourse = FirebaseDatabase.getInstance().getReference("courses").child(schoolID).child(courseID);
        Course updatedCourse = new Course(courseID, name, newRating, prof, dept, year);
        databaseCourse.setValue(updatedCourse);
        return true;
    }
}