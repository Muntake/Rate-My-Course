package com.example.ratemycourse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddCourseActivity extends AppCompatActivity {

    //Class constants
    public static final String COURSE_ID = "courseID";
    public static final String SCHOOL_ID = "schoolID";
    public static final String COURSE_NAME = "courseName";
    public static final String COURSE_RATING = "courseRating";
    public static final String COURSE_INSTRUCTOR = "currentInstructor";
    public static final String COURSE_DEPT = "courseDepartment";
    public static final String COURSE_YEAR = "courseYear";
    public static final String SCHOOL_NAME = "schoolName";
    public static final int DEFAULT_RATING = 0; // default rating for courses

    TextView textViewSchoolName;
    EditText editTextCourseName;
    EditText editTextCourseInstructor;
    EditText editTextCourseDepartment;
    EditText editTextCourseYear;
    Button buttonAddCourse;

    ListView listViewCourses;
    List<Course> courseList;

    DatabaseReference databaseCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        textViewSchoolName = findViewById(R.id.textViewSchoolName);
        editTextCourseName = findViewById(R.id.editTextCourseName);
        editTextCourseInstructor = findViewById(R.id.editTextCourseInstructor);
        editTextCourseDepartment = findViewById(R.id.editTextCourseDepartment);
        editTextCourseYear = findViewById(R.id.editTextCourseYear);
        buttonAddCourse = findViewById(R.id.buttonAddCourse);

        listViewCourses = findViewById(R.id.listViewCourses);
        courseList = new ArrayList<>();

        Intent intent = getIntent();

        String id = intent.getStringExtra(DatabaseSample.SCHOOL_ID);
        String name = intent.getStringExtra(DatabaseSample.SCHOOL_NAME);

        textViewSchoolName.setText(name);

        databaseCourses = FirebaseDatabase.getInstance().getReference("courses").child(id);

        buttonAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCourse();
            }
        });

        listViewCourses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Course course = courseList.get(i);
                Intent intent = new Intent(getApplicationContext(), CourseLanding.class);
                intent.putExtra(COURSE_ID, course.getCourseID());
                intent.putExtra(SCHOOL_ID, id);
                intent.putExtra(COURSE_NAME, course.getCourseName());
                intent.putExtra(COURSE_RATING, Integer.toString(course.getCourseRating()));
                intent.putExtra(COURSE_INSTRUCTOR, course.getCurrentInstructor());
                intent.putExtra(COURSE_DEPT, course.getCourseDepartment());
                intent.putExtra(COURSE_YEAR, course.getCourseYear());
                intent.putExtra(SCHOOL_NAME, name);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseCourses.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                courseList.clear();
                for (DataSnapshot courseSnapshot: dataSnapshot.getChildren()) {
                    Course course = courseSnapshot.getValue(Course.class);
                    courseList.add(course);
                }
                CourseList courseListAdapter = new CourseList(AddCourseActivity.this, courseList);
                listViewCourses.setAdapter(courseListAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void saveCourse() {
        String courseName = editTextCourseName.getText().toString().trim();
        String currentInstructor = editTextCourseInstructor.getText().toString().trim();
        String department = editTextCourseDepartment.getText().toString().trim();
        String year = editTextCourseYear.getText().toString().trim();

        if (!TextUtils.isEmpty(courseName)) {
            String id = databaseCourses.push().getKey();
            Course course = new Course(id, courseName, DEFAULT_RATING, currentInstructor, department, year);
            databaseCourses.child(id).setValue(course);
            Toast.makeText(this, "Course saved successfully.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Please add a course name.", Toast.LENGTH_LONG).show();
        }
    }
}