package com.learning.a1blockaway;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class WoundsBleeding extends AppCompatActivity implements View.OnClickListener {

    private MediaPlayer current = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wounds_bleeding);

        ImageView Image1 = (ImageView)findViewById(R.id.Image1);
        Image1.setOnClickListener(this);

        ImageView Image2 = (ImageView)findViewById(R.id.Image2);
        Image2.setOnClickListener(this);

        ImageView Image3 = (ImageView)findViewById(R.id.Image3);
        Image3.setOnClickListener(this);

        ImageView Image4 = (ImageView)findViewById(R.id.Image4);
        Image4.setOnClickListener(this);

        ImageView Image5 = (ImageView)findViewById(R.id.Image5);
        Image5.setOnClickListener(this);

        ImageView Image6 = (ImageView)findViewById(R.id.Image6);
        Image6.setOnClickListener(this);

        ImageView Image7 = (ImageView)findViewById(R.id.Image7);
        Image7.setOnClickListener(this);

        ImageView Image8 = (ImageView)findViewById(R.id.Image8);
        Image8.setOnClickListener(this);
    }

    protected void onPause(Bundle savedInstanceState){

    }

    @Override
    public void onClick(View view) {
        if(current!=null){
            if(current.isPlaying()){
                current.pause();
                return;
            }
        }
        switch(view.getId()){
            case R.id.Image1:
                current = MediaPlayer.create(this, R.raw.bleeding1);
                current.seekTo(0);
                current.start();
                break;
            case R.id.Image2:
                current = MediaPlayer.create(this, R.raw.bleeding2);
                current.seekTo(0);
                current.start();
                break;
            case R.id.Image3:
                current = MediaPlayer.create(this, R.raw.bleeding3);
                current.seekTo(0);
                current.start();
                break;
            case R.id.Image4:
                current = MediaPlayer.create(this, R.raw.bleeding4);
                current.seekTo(0);
                current.start();
                break;
            case R.id.Image5:
                current = MediaPlayer.create(this, R.raw.bleeding5);
                current.seekTo(0);
                current.start();
                break;
            case R.id.Image6:
                current = MediaPlayer.create(this, R.raw.bleeding6);
                current.seekTo(0);
                current.start();
                break;
            case R.id.Image7:
                current = MediaPlayer.create(this, R.raw.bleeding7);
                current.seekTo(0);
                current.start();
                break;
            case R.id.Image8:
                current = MediaPlayer.create(this, R.raw.bleeding8);
                current.seekTo(0);
                current.start();
                break;
        }
    }
}
