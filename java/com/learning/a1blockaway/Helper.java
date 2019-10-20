package com.learning.a1blockaway;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Helper {

    private static final String TAG = "User";

    private static ValueEventListener listener;

    private static String name;
    private static long age;
    private static String gender;
    private static long height;
    private static String userID;

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Helper.name = name;
    }

    public static long getAge() {
        return age;
    }

    public static void setAge(long age) {
        Helper.age = age;
    }

    public static String getGender() {
        return gender;
    }

    public static void setGender(String gender) {
        Helper.gender = gender;
    }

    public static long getHeight() {
        return height;
    }

    public static void setHeight(long height) {
        Helper.height = height;
    }

    public static String getUserID() {
        return userID;
    }

    public static void setUserID(String userID) {
        Helper.userID = userID;
    }

    public static void populateHelper(String helperUID,final ProgressDialog pd){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("/Users/" + helperUID);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    switch (data.getKey()) {
                        case "Name":
                            Helper.setName(data.getValue().toString());
                            break;
                        case "Age":
                            Helper.setAge((long) data.getValue());
                            break;
                        case "Height":
                            Helper.setHeight((long) data.getValue());
                            break;
                        case "Gender":
                            Helper.setGender(data.getValue().toString());
                            break;
                    }
                }
                pd.dismiss();
            }
        });
    }
}
