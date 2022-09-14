package com.example.ratemycourse;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CourseList extends ArrayAdapter<Course> {

    private Activity context;
    private List<Course> courseList;

    public CourseList(Activity context, List<Course> courseList) {
        super(context, R.layout.list_layout_two, courseList);
        this.context = context;
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout_two, null, true);

        TextView textViewCourseName = (TextView) listViewItem.findViewById(R.id.textViewCourseName);
        RatingBar textCourseRating = (RatingBar) listViewItem.findViewById(R.id.textCourseRating);
        TextView textCourseCurrentInstructor = (TextView) listViewItem.findViewById(R.id.textCourseCurrentInstructor);
        TextView textCourseDepartment = (TextView) listViewItem.findViewById(R.id.textCourseDepartment);
        TextView textCourseYear = (TextView) listViewItem.findViewById(R.id.textCourseYear);

        Course course = courseList.get(position);

        textViewCourseName.setText(course.getCourseName());
        textCourseRating.setRating(course.getCourseRating());
        textCourseCurrentInstructor.setText(course.getCurrentInstructor());
        textCourseDepartment.setText(course.getCourseDepartment());
        textCourseYear.setText(course.getCourseYear());

        return listViewItem;
    }
}
