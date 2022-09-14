package com.example.ratemycourse;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewRating extends AppCompatActivity {

    private TextView courseNameRate;
    private TextView schoolNameRate;

    private String courseID;
    private String courseName;
    private String schoolName;
    private String userString;

    // rating input fields
    private Spinner spinnerGrades;
    private RatingBar rateBar;
    private EditText profName;
    private EditText ratingText;

    private Button submitRating;


    DatabaseReference databaseRatings;
    DatabaseReference databaseUsers;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_review);

        databaseRatings = FirebaseDatabase.getInstance().getReference("ratings");
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        courseNameRate = findViewById(R.id.courseNameRate);
        schoolNameRate = findViewById(R.id.schoolNameRate);

        // initialize rating input fields
        spinnerGrades = findViewById(R.id.spinnerGrades);
        rateBar = findViewById(R.id.rateBar);
        profName = findViewById(R.id.profName);
        ratingText = findViewById(R.id.ratingText);

        submitRating = findViewById(R.id.submitRating);

        Intent intent = getIntent();

        courseID = intent.getStringExtra(CourseLanding.COURSE_ID);
        courseName = intent.getStringExtra(CourseLanding.COURSE_NAME_RATING);
        schoolName = intent.getStringExtra(CourseLanding.SCHOOL_NAME_RATING);

        SharedPreferences sp = getSharedPreferences("userObject", MODE_PRIVATE);
        userString = sp.getString("user", "");
        Gson gson = new Gson();
        user = gson.fromJson(userString, User.class);

        courseNameRate.setText(courseName);
        schoolNameRate.setText(schoolName);

        List<String> grades = new ArrayList<>(Arrays.asList("A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "D-", "F", "N/A"));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, grades);

        spinnerGrades.setAdapter(adapter);

        submitRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRating();
            }
        });
    }

    private void addRating() {
        String grade = spinnerGrades.getSelectedItem().toString();
        int rate = (int) rateBar.getRating();
        String prof = profName.getText().toString().trim();
        String text = ratingText.getText().toString().trim();

        if (!TextUtils.isEmpty(grade) || !TextUtils.equals(Integer.toString(rate), "0") || !TextUtils.isEmpty(prof) || !TextUtils.isEmpty(text)) {
            String id = databaseRatings.push().getKey();
            Rating rating = new Rating(id, courseID, rate, grade, prof, text, user.username);
            databaseRatings.child(id).setValue(rating);

            // update user review count
            databaseUsers.child(user.id).child("userNumberOfReviews").setValue(user.userNumberOfReviews + 1);

            // redirect user back to course landing page after rating is created
            Intent intent = new Intent(NewRating.this, HomePage.class);
            startActivity(intent);

            Toast.makeText(this, "Rating added.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_LONG).show();
        }
    }
}