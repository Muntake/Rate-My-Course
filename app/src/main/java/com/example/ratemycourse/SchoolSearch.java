package com.example.ratemycourse;

//Imports
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

//This activity is where the user is going to search for the school they want to find a course for
public class SchoolSearch extends AppCompatActivity {

    //Database Constants
    public static final String SCHOOL_ID = "schoolid";
    public static final String SCHOOL_NAME = "schoolname";

    //Class variables
    SearchView searchView;
    ListView listViewSchools;
    ArrayList<School> schoolList;
    DatabaseReference databaseSchools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_search);

        //getting school database
        databaseSchools = FirebaseDatabase.getInstance().getReference("schools");

        //Find the views by there ids
        searchView = findViewById(R.id.searchSchools);
        listViewSchools = findViewById(R.id.listOfSchools);

        schoolList = new ArrayList<>();

        listViewSchools.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                School school = schoolList.get(i);
                Intent intent = new Intent(getApplicationContext(), AddCourseActivity.class);
                intent.putExtra(SCHOOL_ID, school.getSchoolID());
                intent.putExtra(SCHOOL_NAME, school.getSchoolName());
                startActivity(intent);
            }
        });

        listViewSchools.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                School school = schoolList.get(i);
                showUpdateDialog(school.getSchoolID(), school.getSchoolName() );
                return true;
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
        if(databaseSchools != null){
            databaseSchools.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        schoolList = new ArrayList<>();
                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            schoolList.add(ds.getValue(School.class));
                        }
                        SchoolList adapter = new SchoolList(SchoolSearch.this, schoolList);
                        listViewSchools.setAdapter(adapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(SchoolSearch.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        if (searchView != null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String schoolName) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String schoolName) {
                    searchSchool(schoolName);
                    return true;
                }
            });
        }

    }

    private void searchSchool(String schoolName){
        ArrayList<School> schoolTempList = new ArrayList<>();
        for(School object:schoolList){
            if(object.getSchoolName().toLowerCase().contains(schoolName.toLowerCase())){
                schoolTempList.add(object);
            }
        }
        SchoolList adapter = new SchoolList(SchoolSearch.this, schoolTempList);
        listViewSchools.setAdapter(adapter);
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
