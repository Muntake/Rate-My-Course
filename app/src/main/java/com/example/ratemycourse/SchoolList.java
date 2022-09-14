package com.example.ratemycourse;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class SchoolList extends ArrayAdapter<School> {

    private Activity context;
    private List<School> schoolList;

    public SchoolList(Activity context, List<School> schoolList) {
        super(context, R.layout.list_layout, schoolList);
        this.context = context;
        this.schoolList = schoolList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView textViewSchoolName = listViewItem.findViewById(R.id.textViewSchoolName);
        TextView textViewSchoolProvince = listViewItem.findViewById(R.id.textViewSchoolProvince);
        TextView textViewSchoolCity = listViewItem.findViewById(R.id.textViewSchoolCity);
        TextView textViewSchoolEmail = listViewItem.findViewById(R.id.textViewSchoolEmail);
        TextView textViewSchoolAddress = listViewItem.findViewById(R.id.textViewSchoolAddress);
        TextView textViewSchoolPhone = listViewItem.findViewById(R.id.textViewSchoolPhone);
        TextView textViewSchoolPostalCode = listViewItem.findViewById(R.id.textViewSchoolPostalCode);

        School school = schoolList.get(position);

        textViewSchoolName.setText(school.getSchoolName());
        textViewSchoolProvince.setText(school.getSchoolProvince());
        textViewSchoolCity.setText(school.getSchoolCity());
        textViewSchoolEmail.setText(school.getSchoolEmail());
        textViewSchoolAddress.setText(school.getSchoolAddress());
        textViewSchoolPhone.setText(school.getSchoolPhone());
        textViewSchoolPostalCode.setText(school.getSchoolPostalCode());

        return listViewItem;
    }
}
