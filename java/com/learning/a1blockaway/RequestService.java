package com.learning.a1blockaway;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class RequestService extends Service {

    private DatabaseReference mDatabaseReference;

    private static final String TAG = "RequestService";
    
    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    public static ObservableCollection activeRequests;
    private ArrayList<String> UIDS = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        activeRequests = new ObservableCollection();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Requests");
        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String userID = dataSnapshot.getKey();
                if(!userID.equals(User.getUserID()) && !User.getAccepted()) {
                    final Request newRequest = new Request();
                    UIDS.add(userID);
                    newRequest.setUserID(userID);
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Log.d(TAG, "onChildAdded: " + data);
                        Log.d(TAG, "onChildAdded: " + dataSnapshot.getChildren());
                        Log.d(TAG, "onChildAdded: " + data.getKey());
                        switch (data.getKey()) {
                            case "Info":
                                newRequest.setType(data.getValue().toString());
                                break;
                            case "Lat":
                                newRequest.setLatitude((double) data.getValue());
                                break;
                            case "Long":
                                Log.d(TAG, "onChildAdded: " + newRequest.getLongitude());
                                newRequest.setLongitude((double) data.getValue());
                                break;
                            case "Age":
                                newRequest.setAge((long)data.getValue());
                                break;
                            case "Height":
                                newRequest.setHeight((long)data.getValue());
                                break;
                            case "Gender":
                                newRequest.setGender(data.getValue().toString());
                                break;
                            case "Name":
                                newRequest.setName(data.getValue().toString());
                                break;
                        }
                    }
                    OnSuccessListener<Location> onSuccessListener = new OnSuccessListener<Location>(){
                        @Override
                        public void onSuccess(Location location) {
                            Log.d(TAG, "onSuccess: " + location.getLatitude() + " - " + location.getLongitude() + " - " + newRequest.getLatitude() + " - " + newRequest.getLongitude());
                            Log.d(TAG, "onSuccess: " + Haversine.isWithinDistance(location.getLatitude(),location.getLongitude(),newRequest.getLatitude(),newRequest.getLongitude()));
                            if(Haversine.isWithinDistance(location.getLatitude(),location.getLongitude(),newRequest.getLatitude(),newRequest.getLongitude())) {
                                Intent intent = new Intent(getBaseContext(), Root.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                int uniqueInt = (int) (System.currentTimeMillis() & 0xfffffff);
                                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), uniqueInt, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "default")
                                        .setSmallIcon(R.drawable.logo)
                                        .setContentTitle("A user has requested help!")
                                        .setContentText("Please tap here for the details.")
                                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                                        .setContentIntent(pendingIntent)
                                        .setAutoCancel(true);
                                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                                notificationManager.notify(0, builder.build());
                                activeRequests.add(newRequest);
                            }
                        }
                    };
                    CustomLocationServices customLocationServices = new CustomLocationServices(getApplicationContext(), onSuccessListener);
                    customLocationServices.getLocation();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                activeRequests.remove(UIDS.indexOf(dataSnapshot.getKey()));
                UIDS.remove(dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notifIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notifIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("1BlockAway - Active Respondent")
                .setContentText("You are on standby as a responder.")
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= 26) {
            NotificationChannel serviceChannel = new NotificationChannel(CHANNEL_ID, "Foreground Service Channel", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
