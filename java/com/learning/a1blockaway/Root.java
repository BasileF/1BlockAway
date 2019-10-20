package com.learning.a1blockaway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Root extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Root";

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private EditText mUsername;
    private EditText mPassword;
    private Button mSignIn;
    private Button mRegister;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);

        mAuth = FirebaseAuth.getInstance();

        mProgressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Signing You In...");

        int locationPerm = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(locationPerm == PackageManager.PERMISSION_DENIED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle("Location Permission Necessary");
                alertBuilder.setMessage("1BlockAway needs access to your location in order to send and receive help requests. " +
                        "The app will not operate without your permission.");
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(Root.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                0);
                    }
                });

                AlertDialog alert = alertBuilder.create();
                alert.show();
            }else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            }
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED){
                finish();
            }
        }

        mUsername = findViewById(R.id.login_username);
        mPassword = findViewById(R.id.login_password);
        mRegister = findViewById(R.id.login_register);
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Root.this, Register.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        mSignIn = findViewById(R.id.login_sign_in);
        mSignIn.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mUser = mAuth.getCurrentUser();
        if(mUser != null){
            login();
        }
    }

    @Override
    public void onClick(View view) {
        login();
    }

    private void login(){
        mProgressDialog.show();
        mUser = mAuth.getCurrentUser();
        if(mUser != null){
            populateUser(mUser.getUid());
        }else{
            String email = mUsername.getText().toString().trim();
            String password = mPassword.getText().toString();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d(TAG, "onComplete: " + task.isSuccessful());
                    if(task.isSuccessful()) {
                        mUser = task.getResult().getUser();
                        populateUser(mUser.getUid());
                    }else{
                        mProgressDialog.dismiss();
                    }
                }
            });
        }
    }

    private void populateUser(final String userID){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userID);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    switch(data.getKey()){
                        case "Name":
                            User.setName(data.getValue().toString());
                            break;
                        case "Age":
                            User.setAge((long)data.getValue());
                            break;
                        case "Height":
                            User.setHeight((long)data.getValue());
                            break;
                        case "Gender":
                            User.setGender(data.getValue().toString());
                            break;
                        case "Address":
                            User.setAddress(data.getValue().toString());
                            break;
                        case "Respondent":
                            User.setIsRespondent((boolean)data.getValue());
                            break;
                        case "Active":
                            User.setActiveRequest((boolean)data.getValue());
                            break;
                        case "Accepted":
                            User.setAccepted((boolean)data.getValue());
                            break;
                    }
                }
                User.setUserID(userID);
                mProgressDialog.dismiss();
                if(User.isActiveRequest()){
                    Intent intent = new Intent(getBaseContext(), CurrentRequest.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                }
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
