package com.example.ratemycourse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePage extends AppCompatActivity {

    //Class variables
    Button schoolSearch;
    Button profileButton;
    Button databaseSample;
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //find buttons by id
        schoolSearch = findViewById(R.id.searchSchoolButton);
        profileButton = findViewById(R.id.editProfileButton);
        //reviewButton = findViewById(R.id.addRatingButton);
        databaseSample = findViewById(R.id.databaseSample);
        bottomNav = findViewById(R.id.bottomNavigationView);

        schoolSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(HomePage.this, SchoolSearch.class);
                startActivity(intent);
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, UserProfile.class);//
                User user = (User)getIntent().getSerializableExtra("user");
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        databaseSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, DatabaseSample.class);
                startActivity(intent);
            }
        });

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_homepage:
                        Toast.makeText(getApplicationContext(),"Already on homepage",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_profile:
                        Intent intent = new Intent(HomePage.this, UserProfile.class);//
                        User user = (User)getIntent().getSerializableExtra("user");
                        intent.putExtra("user", user);
                        startActivity(intent);
                        break;
                    case R.id.nav_logout:
                        intent = new Intent(HomePage.this, MainActivity.class);
                        startActivity(intent);
                        break;
                }

                return true;
            }
        });


    }
}