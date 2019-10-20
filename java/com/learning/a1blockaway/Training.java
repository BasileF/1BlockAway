package com.learning.a1blockaway;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Training extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        Button Basictraining = (Button)findViewById(R.id.Basictraining);
        Basictraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(Training.this, Basictraining.class));
            }
        });

        Button WoundsBleeding = (Button)findViewById(R.id.WoundsBleeding);
        WoundsBleeding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Training.this, WoundsBleeding.class));
            }
        });

        Button Chestpain = (Button)findViewById(R.id.Chestpain);
        Chestpain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Training.this, Chestpain.class));
            }
        });

        Button Headtrauma = (Button)findViewById(R.id.Headtrauma);
        Headtrauma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Training.this, Headtrauma.class));
            }
        });

        Button IntoxicationDrugoverdose = (Button)findViewById(R.id.IntoxicationDrugoverdose);
        IntoxicationDrugoverdose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Training.this, IntoxicationDrugoverdose.class));
            }
        });

        Button Breathingdifficulties = (Button)findViewById(R.id.Breathingdifficulties);
        Breathingdifficulties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Training.this, Breathingdifficulties.class));
            }
        });
    }
}
