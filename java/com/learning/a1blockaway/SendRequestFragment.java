package com.learning.a1blockaway;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

public class SendRequestFragment extends Fragment implements OnMapReadyCallback, OnSuccessListener<Location> {
    private static final String TAG = "SendRequestFragment";
    
    private GoogleMap map;
    private CircleOptions myDot;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_send_request, container, false);
        Button btn = myView.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SendRequestFragment.this.getActivity(), RequestSpecifications.class);
                startActivity(intent);
            }
        });
        MapFragment mapView = (MapFragment)getActivity().getFragmentManager().findFragmentById(R.id.send_request_map);
        mapView.getMapAsync(this);
        return myView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        CustomLocationServices customLocationServices = new CustomLocationServices(getContext(), this);
    }

    @Override
    public void onSuccess(Location location) {
        LatLng me = new LatLng(location.getLatitude(), location.getLongitude());
        Log.d(TAG, "onSuccess: me");
        myDot = new CircleOptions();
        myDot.center(me);
        myDot.radius(2);
        myDot.strokeColor(Color.BLACK);
        myDot.fillColor(Color.BLUE);
        myDot.strokeWidth(2);
        map.addCircle(myDot);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(me,18.0f));
    }
}
