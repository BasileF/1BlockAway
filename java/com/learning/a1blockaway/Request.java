package com.learning.a1blockaway;

import android.telephony.SmsManager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private double latitude;
    private double longitude;
    private String type;
    private String userID;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    private long age;
    private String gender;
    private long height;

    public Request(){
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void sendRequest(){
        Map<String, Object> map = new HashMap<>();
        map.put("/Requests/" + this.userID + "/Lat/", this.latitude);
        map.put("/Requests/" + this.userID + "/Long/", this.longitude);
        map.put("/Requests/" + this.userID + "/Info/", this.type);
        map.put("/Requests/" + this.userID + "/Name/", this.name);
        map.put("/Requests/" + this.userID + "/Age/", this.age);
        map.put("/Requests/" + this.userID + "/Gender/", this.gender);
        map.put("/Requests/" + this.userID + "/Height/", this.height);
        FirebaseDatabase.getInstance().getReference("Users/"+this.userID).child("Active").setValue(true);
        FirebaseDatabase.getInstance().getReference().updateChildren(map);
        User.setActiveRequest(true);
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("514-922-1216",null,"Test", null, null);
    }
}