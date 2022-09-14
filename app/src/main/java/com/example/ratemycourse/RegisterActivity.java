package com.example.ratemycourse;

//Imports
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class RegisterActivity extends AppCompatActivity {

    Button signUpPageButton;
    EditText usernameText;
    EditText fullNameText;
    EditText emailText;
    EditText majorText;
    EditText passwordText;

    DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        usernameText = findViewById(R.id.userNameField);
        fullNameText = findViewById(R.id.fullNameField);
        emailText = findViewById(R.id.emailField);
        majorText = findViewById(R.id.majorField);
        passwordText = findViewById(R.id.passwordField);
        signUpPageButton = findViewById(R.id.signUpPageButton);

        signUpPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = createUser();
                if(user != null){
                    Intent intent = new Intent(RegisterActivity.this, HomePage.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
            }
        });
    }

    private User createUser(){
        String username = usernameText.getText().toString().trim();
        String fullName = fullNameText.getText().toString().trim();
        String email = emailText.getText().toString().trim();
        String major = majorText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();

        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(fullName) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(major) && !TextUtils.isEmpty(password) ){
            String id = databaseUsers.push().getKey();
            User user = new User(id, username, email, fullName, major , password, 0, 0, 0, "");

            // store user in shared preferences as JSON
            SharedPreferences mPrefs = getSharedPreferences("userObject", MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor = mPrefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(user);
            prefsEditor.putString("user", json);
            prefsEditor.apply();

            databaseUsers.child(id).setValue(user);
            Toast.makeText(this, "Account Created.", Toast.LENGTH_LONG).show();
            return user;
        }else {
            Toast.makeText(this, "Please fill all of the fields", Toast.LENGTH_LONG).show();
        }
        return null;
    }
}