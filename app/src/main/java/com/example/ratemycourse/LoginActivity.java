package com.example.ratemycourse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    Button loginPageButton;
    EditText emailText;
    EditText passwordText;

    DatabaseReference databaseUsers;
    ArrayList<User> usersList;

    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Getting the users database instance
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        loginPageButton = findViewById(R.id.loginPageButton);
        emailText = findViewById(R.id.emailField);
        passwordText = findViewById(R.id.passwordField);

        loginPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();
                boolean loginValid = checkForValidLogin(email, password);
                if (loginValid) {
                    User user = searchForUser(email);

                    // store user in shared preferences as JSON
                    SharedPreferences mPrefs = getSharedPreferences("userObject", MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor = mPrefs.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(user);
                    prefsEditor.putString("user", json);
                    prefsEditor.apply();

                    Intent intent = new Intent(LoginActivity.this, HomePage.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "This login is invalid", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (databaseUsers != null) {
            databaseUsers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        usersList = new ArrayList<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            usersList.add(ds.getValue(User.class));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(LoginActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean checkForValidLogin(String email, String password) {
        boolean validLogin = false;
        User user = searchForUser(email);
        if (user != null && user.password.equals(password)) {
            validLogin = true;
        }
        return validLogin;
    }

    private User searchForUser(String email) {
        for (User user : usersList) {
            if (user.email.equals(email)) {
                return user;
            }
        }
        return null;
    }
}