package com.learning.a1blockaway;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import org.w3c.dom.Text;

public class CurrentRespondent extends AppCompatActivity implements OnMapReadyCallback, OnSuccessListener<Location> {
    private static final String TAG = "CurrentRespondent";
    
    private LocationCallback locationCallback;
    private GoogleMap map;
    private CircleOptions myDot;
    private CircleOptions theirDot;
    private LatLng me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_respondent);

        Request request = User.getRequestor();

        ImageView pic = findViewById(R.id.current_response_image);

        TextView info = findViewById(R.id.current_response_info);
        info.setText(request.getType());

        TextView name = findViewById(R.id.current_response_name);
        name.setText("Name: " + request.getName());

        TextView age = findViewById(R.id.current_response_age);
        age.setText("Age: " + request.getAge());

        TextView gender = findViewById(R.id.current_response_gender);
        gender.setText("Gender: " + request.getGender());

        TextView height = findViewById(R.id.current_response_height);
        height.setText("Height: " + request.getHeight());

        Toolbar toolbar = findViewById(R.id.current_response_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.current_response_map);

        theirDot = new CircleOptions();
        theirDot.center(new LatLng(User.getRequestor().getLatitude(), User.getRequestor().getLongitude()));
        theirDot.radius(2);
        theirDot.strokeColor(Color.BLACK);
        theirDot.fillColor(Color.RED);
        theirDot.strokeWidth(2);

        myDot = new CircleOptions();
        CustomLocationServices customLocationServices = new CustomLocationServices(this, this);
        customLocationServices.getLocation();

        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    Log.d(TAG, "onLocationResult: null");
                    return;
                }
                else {
                    Location last = locationResult.getLastLocation();
                    Log.d(TAG, "onLocationResult: logging");
                    myDot.center(new LatLng(last.getLatitude(),last.getLongitude()));
                    map.clear();
                    map.addCircle(myDot);
                    map.addCircle(theirDot);
                }
            }
        };

        mapFragment.getMapAsync(this);

        Button instructions = findViewById(R.id.current_response_instructions);
        instructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CurrentRespondent.this, Training.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(1000);
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        Log.d(TAG, "onMapReady: " + fusedLocationProviderClient);
        try {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
            Log.d(TAG, "onMapReady: good");
        }catch (SecurityException e){
            Log.d(TAG, "onMapReady: ttt");
        }
        googleMap.addCircle(theirDot);
        map = googleMap;
    }

    @Override
    public void onSuccess(Location o) {
        me = new LatLng(o.getLatitude(), o.getLongitude());
        myDot.center(me);
        myDot.radius(2);
        myDot.strokeColor(Color.BLACK);
        myDot.fillColor(Color.BLUE);
        myDot.strokeWidth(2);
        map.addCircle(myDot);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(me,18.0f));
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
