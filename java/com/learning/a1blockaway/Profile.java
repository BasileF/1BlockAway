package com.learning.a1blockaway;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        overridePendingTransition(R.anim.enter,R.anim.exit);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView name = findViewById(R.id.profile_name);
        name.setText(User.getName());

        TextView address = findViewById(R.id.profile_address);
        address.setText(User.getAddress());

        TextView age = findViewById(R.id.profile_age);
        age.setText("Age: " + User.getAge());

        TextView gender = findViewById(R.id.profile_gender);
        gender.setText("Gender: " + User.getGender());

        TextView height = findViewById(R.id.profile_height);
        height.setText("Height: " + User.getHeight()+"cm");

        Switch respondent = findViewById(R.id.profile_respondent);
        respondent.setChecked(User.isIsRespondent());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
