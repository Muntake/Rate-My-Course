package com.example.ratemycourse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.List;

public class RatingList extends ArrayAdapter<Rating> {

    private Activity context;
    private List<Rating> ratingList;

    public RatingList(Activity context, List<Rating> ratingList) {
        super(context, R.layout.list_layout, ratingList);
        this.context = context;
        this.ratingList = ratingList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        // to update the users endorsements
        DatabaseReference databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        View listViewItem = inflater.inflate(R.layout.rating_list_layout, null, true);

        TextView prof = listViewItem.findViewById(R.id.prof);
        RatingBar rate = listViewItem.findViewById(R.id.rate);
        TextView grade = listViewItem.findViewById(R.id.grade);
        TextView ratingText = listViewItem.findViewById(R.id.rating);
        TextView username = listViewItem.findViewById(R.id.username);

        ImageButton endorseButton = listViewItem.findViewById(R.id.endorseButton);

        Rating rating = ratingList.get(position);

        prof.setText(rating.getCurrentProf());
        rate.setRating(rating.getRating());
        grade.setText(rating.getGradeReceived());
        ratingText.setText(rating.getRatingText());

        String user = context.getString(R.string.user);

        username.setText(user + " " + rating.getUsername());

        Button userOverview = listViewItem.findViewById(R.id.userOverview);

        userOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserOverview.class);
                intent.putExtra("usernameOverview", rating.getUsername());
                context.startActivity(intent);
            }
        });

        endorseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = context.getSharedPreferences("userObject", Context.MODE_PRIVATE);
                String userString = sp.getString("user", "");
                Gson gson = new Gson();
                User user = gson.fromJson(userString, User.class);
                databaseUsers.child(user.id).child("userNumberOfEndorsements").setValue(user.userNumberOfEndorsements + 1);

                // use snack bar to notify user endorsement was successful
                Snackbar snackbar = Snackbar.make(listViewItem,"Rating endorsed!",Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });

        return listViewItem;
    }
}
