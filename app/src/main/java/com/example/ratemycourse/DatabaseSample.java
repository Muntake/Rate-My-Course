package com.example.ratemycourse;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import android.widget.Spinner;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.List;

public class DatabaseSample extends AppCompatActivity {

    public static final String SCHOOL_ID = "schoolid";
    public static final String SCHOOL_NAME = "schoolname";

    EditText editTextName;
    Spinner spinnerProvinces;
    EditText editTextCity;
    EditText editTextEmail;
    EditText editTextAddress;
    EditText editTextPhone;
    EditText editTextPostalCode;
    Button buttonAddSchool;

    List<School> schoolList;

    DatabaseReference databaseSchools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_sample);

        databaseSchools = FirebaseDatabase.getInstance().getReference("schools");

        editTextName = findViewById(R.id.editTextName);
        spinnerProvinces = findViewById(R.id.spinnerProvinces);
        editTextCity = findViewById(R.id.editTextCity);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextPostalCode = findViewById(R.id.editTextPostalCode);

        buttonAddSchool = findViewById(R.id.buttonAddSchool);

        schoolList = new ArrayList<>();

        buttonAddSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSchool();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void addSchool() {

        String name = editTextName.getText().toString().trim();
        String province = spinnerProvinces.getSelectedItem().toString();
        String city = editTextCity.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String postalCode = editTextPostalCode.getText().toString().trim();

        if (!TextUtils.isEmpty(name)) {
            String id = databaseSchools.push().getKey();
            School school = new School(id, name, province, city, email, address, phone, postalCode);
            databaseSchools.child(id).setValue(school);



            Toast.makeText(this, "School added.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Please enter name.", Toast.LENGTH_LONG).show();
        }
    }

    private void showUpdateDialog(String schoolID, String schoolName) {

        // create alert dialog builder
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        // inflate xml layout
        LayoutInflater inflater = getLayoutInflater();

        // create view for dialog and inflate
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);

        // set view for dialog builder as the inflated view
        dialogBuilder.setView(dialogView);

        final EditText updateSchoolName = dialogView.findViewById(R.id.updateSchoolName);
        final Spinner updateSpinner = dialogView.findViewById(R.id.updateSpinnerSchool);
        final EditText updateSchoolCity = dialogView.findViewById(R.id.updateSchoolCity);
        final EditText updateSchoolEmail = dialogView.findViewById(R.id.updateSchoolEmail);
        final EditText updateSchoolAddress = dialogView.findViewById(R.id.updateSchoolAddress);
        final EditText updateSchoolPhone = dialogView.findViewById(R.id.updateSchoolPhone);
        final EditText updateSchoolPostalCode = dialogView.findViewById(R.id.updateSchoolPostalCode);

        final Button updateButton = dialogView.findViewById(R.id.buttonUpdateSchool);
        final Button deleteButton = dialogView.findViewById(R.id.buttonDelete);

        dialogBuilder.setTitle("Updating School: " + schoolName);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = updateSchoolName.getText().toString().trim();
                String province = updateSpinner.getSelectedItem().toString();
                String city = updateSchoolCity.getText().toString().trim();
                String email = updateSchoolEmail.getText().toString().trim();
                String address = updateSchoolAddress.getText().toString().trim();
                String phone = updateSchoolPhone.getText().toString().trim();
                String postalCode = updateSchoolPostalCode.getText().toString().trim();
                if (!TextUtils.isEmpty(name)) {
                    updateSchool(schoolID, name, province, city, email, address, phone, postalCode);
                    alertDialog.dismiss();
                } else {
                    updateSchoolName.setError("Name is required");
                    return;
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSchool(schoolID);
            }
        });
    }

    private void deleteSchool(String id) {
        DatabaseReference deleteSchools = FirebaseDatabase.getInstance().getReference("schools").child(id);
        DatabaseReference deleteCourses = FirebaseDatabase.getInstance().getReference("courses").child(id);

        deleteSchools.removeValue();
        deleteCourses.removeValue();

        Toast.makeText(this, "School is deleted", Toast.LENGTH_LONG).show();
    }

    public boolean updateSchool(String id, String name, String province, String city, String email, String address, String phone, String postalCode) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("schools").child(id);
        School school = new School(id, name, province, city, email, address, phone, postalCode);
        databaseReference.setValue(school);
        Toast.makeText(this, "School Updated", Toast.LENGTH_LONG).show();
        return true;
    }

}