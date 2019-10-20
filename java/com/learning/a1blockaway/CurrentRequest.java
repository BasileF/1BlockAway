package com.learning.a1blockaway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CurrentRequest extends AppCompatActivity {
    String additionalInformation = "ADDITIONAL INFORMATION: \n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_request);

        //Bundle extras = getIntent().getExtras();
        Intent intent = getIntent();
        String issueArray = intent.getStringExtra("issueArray");
        boolean forSomeoneElse = intent.getBooleanExtra("forSomeoneElse", false);

        TextView name = findViewById(R.id.current_request_name);
        name.setText("Name: " + Helper.getName());

        TextView age = findViewById(R.id.current_request_age);
        age.setText("Age: " + Helper.getAge());

        TextView gender = findViewById(R.id.current_request_gender);
        gender.setText("Gender: " + Helper.getGender());

        TextView height = findViewById(R.id.current_request_height);
        height.setText("Height: " + Helper.getHeight());
    }
}
