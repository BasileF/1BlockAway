package com.learning.a1blockaway;

import android.content.Context;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class CustomLocationServices {
    private OnSuccessListener mListener;
    private Context mContext;
    private FusedLocationProviderClient mFusedLocationClient;

    public CustomLocationServices(Context context, OnSuccessListener listener){
        this.mListener = listener;
        this.mContext = context;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);
    }

    public void getLocation(){
        try {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(mListener);
        }catch(SecurityException e){

        }
    }
}
