package com.learning.a1blockaway;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashSet;
import java.util.Iterator;

public class RequestSpecifications extends AppCompatActivity {
    private static final String TAG = "RequestSpecifications";
    
    private HashSet<String> issue = new HashSet<String>();
    private String information = "";
    boolean forSomeoneElse = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_specifications);

        //Send issue hashset to firebase and ProcessRequest activity
        Button Send = (Button)findViewById(R.id.Send);
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Transfer hashset to array
                int index = 0;
                String[] issueArray = new String[issue.size()];
                Iterator<String> it = issue.iterator();
                while(it.hasNext()){
                    issueArray[index] = it.next();
                    if(issueArray[index]==null || issueArray[index].equals("null")){
                        index++;
                        continue;
                    }
                    if(index!=0) information += "\n";
                    information += issueArray[index];
                    index++;
                }

                sendRequest();

                //next activity with intent
                final Intent intent = new Intent(RequestSpecifications.this, CurrentRequest.class);
                intent.putExtra("issueArray", information);
                intent.putExtra("forSomeoneElse",forSomeoneElse);
                final ProgressDialog mProgressDialog = new ProgressDialog(RequestSpecifications.this, R.style.AppTheme_Dark_Dialog);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setCancelable(false);
                mProgressDialog.setMessage("The authorities have been notified and are on their way!\nAwaiting civilian response.");
                mProgressDialog.show();
                Log.d(TAG, "onClick: stuck");
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("/Requests/"+User.getUserID()+"/");
                databaseReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Log.d(TAG, "onChildAdded: " + dataSnapshot.toString());
                        if(dataSnapshot.getKey().equals("Helper")){
                            Log.d(TAG, "onChildAdded: heyguuuggu");
                            Helper.populateHelper(dataSnapshot.getValue().toString(), mProgressDialog);
                            startActivity(intent);
                            finishAffinity();
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


        //Send for whom request is
        Switch SendingForSomeoneElse = (Switch)findViewById(R.id.SendingForSomeoneElse);
        SendingForSomeoneElse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    forSomeoneElse = true;
                }
                else{
                    forSomeoneElse = false;
                }
            }
        });

        final int colorOn = getResources().getColor(R.color.secondaryLightColor);
        final int colorOff = getResources().getColor(R.color.secondaryTextColor);

        //toggle in/out issue from hashset
        final Button WoundsBleeding = (Button)findViewById(R.id.WoundsBleeding);
        WoundsBleeding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!issue.contains("Wounds, Bleeding")){
                    issue.add("Wounds, Bleeding");
                    WoundsBleeding.setBackgroundColor(colorOn);
                    WoundsBleeding.setTextColor(colorOff);
                }
                else{
                    issue.remove("Wounds, Bleeding");
                    WoundsBleeding.setBackgroundColor(colorOff);
                    WoundsBleeding.setTextColor(colorOn);
                }
            }
        });
        final Button Chestpain = (Button)findViewById(R.id.Chestpain);
        Chestpain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!issue.contains("Chest Pain")){
                    issue.add("Chest Pain");
                    Chestpain.setBackgroundColor(colorOn);
                    Chestpain.setTextColor(colorOff);
                }
                else {
                    issue.remove("Chest Pain");
                    Chestpain.setBackgroundColor(colorOff);
                    Chestpain.setTextColor(colorOn);
                }
            }
        });
        final Button Headtrauma = (Button)findViewById(R.id.Headtrauma);
        Headtrauma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!issue.contains("Head Trauma")){
                    issue.add("Head Trauma");
                    Headtrauma.setBackgroundColor(colorOn);
                    Headtrauma.setTextColor(colorOff);
                }
                else {
                    issue.remove("Head Trauma");
                    Headtrauma.setBackgroundColor(colorOff);
                    Headtrauma.setTextColor(colorOn);
                }
            }
        });
        final Button IntoxicationDrugoverdose = (Button)findViewById(R.id.IntoxicationDrugoverdose);
        IntoxicationDrugoverdose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!issue.contains("Intoxication, Drug Overdose")){
                    issue.add("Intoxication, Drug Overdose");
                    IntoxicationDrugoverdose.setBackgroundColor(colorOn);
                    IntoxicationDrugoverdose.setTextColor(colorOff);
                }
                else {
                    issue.remove("Intoxication, Drug Overdose");
                    IntoxicationDrugoverdose.setBackgroundColor(colorOff);
                    IntoxicationDrugoverdose.setTextColor(colorOn);
                }
            }
        });
        final Button Breathingdifficulties = (Button)findViewById(R.id.Breathingdifficulties);
        Breathingdifficulties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!issue.contains("Breathing Difficulties")){
                    issue.add("Breathing Difficulties");
                    Breathingdifficulties.setBackgroundColor(colorOn);
                    Breathingdifficulties.setTextColor(colorOff);
                }
                else {
                    issue.remove("Breathing Difficulties");
                    Breathingdifficulties.setBackgroundColor(colorOff);
                    Breathingdifficulties.setTextColor(colorOn);
                }
            }
        });

        //Send issue as "other"
        final Button Other = (Button)findViewById(R.id.Other);
        Other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!issue.contains("Other")){
                    issue.add("Other");
                    Other.setBackgroundColor(colorOn);
                    Other.setTextColor(colorOff);
                }
                else {
                    issue.remove("Other");
                    Other.setBackgroundColor(colorOff);
                    Other.setTextColor(colorOn);
                }
            }
        });
    }

    private void sendRequest(){
        final Request request = new Request();
        request.setUserID(User.getUserID());
        request.setAge(User.getAge());
        request.setGender(User.getGender());
        request.setHeight(User.getHeight());
        request.setName(User.getName());
        request.setType(information);
        OnSuccessListener<Location> onSuccessListener = new OnSuccessListener<Location>(){
            @Override
            public void onSuccess(Location location) {
                request.setLatitude(location.getLatitude());
                request.setLongitude(location.getLongitude());
                request.sendRequest();
            }
        };
        CustomLocationServices customLocationServices = new CustomLocationServices(this, onSuccessListener);
        customLocationServices.getLocation();
    }
}
