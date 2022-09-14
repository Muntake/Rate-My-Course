package com.example.ratemycourse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class UserOverview extends AppCompatActivity {

    private TextView userName;
    private TextView numberOfRatings;
    private TextView numberOfEndorsements;
    private TextView fullNameField;
    private TextView majorField;
    private TextView interestsField;

    private DatabaseReference databaseUsers;
    private String userNameString;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_overview);

        Intent intent = getIntent();

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        userName = findViewById(R.id.userNameTitle);
        numberOfRatings = findViewById(R.id.userRating);
        numberOfEndorsements = findViewById(R.id.numberOfEndorsements);
        fullNameField = findViewById(R.id.fullNameFieldProfile);
        majorField = findViewById(R.id.majorFieldProfile);
        interestsField = findViewById(R.id.interestsFieldProfile);

        userNameString = intent.getStringExtra("usernameOverview");

        // populating user info
        userName.setText(userNameString);
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                    user = userSnapshot.getValue(User.class);
                    String x = user.getUsername();
                    if (user.getUsername().equals(userNameString)) {
                        numberOfRatings.setText(String.valueOf(user.getUserNumberOfReviews()));
                        numberOfEndorsements.setText(String.valueOf(user.getUserNumberOfEndorsements()));
                        fullNameField.setText(user.getUserFullName());
                        majorField.setText(user.getUserMajor());
                        interestsField.setText(user.getInterests());
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}