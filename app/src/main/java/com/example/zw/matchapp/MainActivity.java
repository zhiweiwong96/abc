package com.example.zw.matchapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private cards cards_data[];
    private com.example.zw.matchapp.arrayAdapter arrayAdapter;
    private int i;

    public Button checkProfile;
    private FirebaseAuth mAuth;

    private String currentUId;

    private DatabaseReference usersDb;

    public static String oppUserId;

    ListView listView;
    List<cards> rowItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usersDb = FirebaseDatabase.getInstance().getReference().child("Users");

        mAuth = FirebaseAuth.getInstance();
        currentUId = mAuth.getCurrentUser().getUid();

        checkProfile = findViewById(R.id.userProfile);
        checkProfile.setVisibility(View.GONE);

        checkUserSex();

        rowItems = new ArrayList<cards>();

        arrayAdapter = new arrayAdapter(this, R.layout.item, rowItems);

        SwipeFlingAdapterView flingContainer = findViewById(R.id.frame);

        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                rowItems.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                cards obj = (cards) dataObject;
                String userId = obj.getUserId();
                usersDb.child(userId).child("connection").child("nope").child(currentUId).setValue(true);
                checkProfile.setVisibility(View.GONE);
                //Toast.makeText(MainActivity.this, "Disliked"+ userId, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                cards obj = (cards) dataObject;
                String userId = obj.getUserId();
                System.out.println("UserID = "+ userId);
                usersDb.child(userId).child("connection").child("yeps").child(currentUId).setValue(true);
                isConnectionMatch(userId);
                System.out.println("passed isConnectionMatch");
                checkProfile.setVisibility(View.GONE);
                //Toast.makeText(MainActivity.this, "Liked"+ userId, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {

            }
        });

        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                cards obj = (cards) dataObject;
                String userId = obj.getUserId();
                oppUserId = userId;
                checkProfile.setVisibility(View.VISIBLE);
                //Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }



    private void isConnectionMatch(String userId) {
        System.out.println("Inside isConnectionMatch");

        DatabaseReference currentUserConnectionDb = usersDb.child(currentUId).child("connection").child("yeps").child(userId);

        currentUserConnectionDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("Inside onDataChange (isConnectionMatch)");

                if(dataSnapshot.exists()){
                    Toast.makeText(MainActivity.this, "Matched Successful", Toast.LENGTH_LONG).show();

                    //when both of them swipe right for each other, create matches
                    usersDb.child(dataSnapshot.getKey()).child("connection").child("matches").child(currentUId).setValue(true);
                    usersDb.child(currentUId).child("connection").child("matches").child(dataSnapshot.getKey()).setValue(true);
                    System.out.println("Match should be created successfully");
                }else{
                    System.out.println("dataSnapshot not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private String userSex;
    private String oppUserSex;
    public void checkUserSex(){

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    userSex = dataSnapshot.child("sex").getValue().toString();

                    switch (userSex){
                        case "Male":
                            oppUserSex = "Female";
                            break;
                        case "Female":
                            oppUserSex = "Male";
                            break;
                    }
                    getOppositeSexUsers();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void getOppositeSexUsers(){

        usersDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists() && !dataSnapshot.child("connection").child("nope").hasChild(currentUId) && !dataSnapshot.child("connection").child("yeps").hasChild(currentUId) && dataSnapshot.child("sex").getValue().toString().equals(oppUserSex)){
                    String profileImageUrl = "default";
                    if(!dataSnapshot.child("profileImageUrl").getValue().equals("default")){
                        profileImageUrl = dataSnapshot.child("profileImageUrl").getValue().toString();
                    }else{
                        profileImageUrl = "default";
                    }
                    cards item = new cards(dataSnapshot.getKey(), dataSnapshot.child("name").getValue().toString(),profileImageUrl);
                    rowItems.add(item);
                    arrayAdapter.notifyDataSetChanged();
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

    public void logoutUser(View view) {
        mAuth.signOut();
        Intent intent = new Intent(MainActivity.this, LoginRegistrationActivity.class);
        startActivity(intent);
        finish();
        return;
    }

    public void goToSettings(View view) {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
        return;
    }

    public void goToMatches(View view){
        Intent intent = new Intent(MainActivity.this, MatchesActivity.class);
        startActivity(intent);
        return;
    }

    public void goToFilter(View view){
        Intent intent = new Intent(MainActivity.this, FilterActivity.class);
        startActivity(intent);
        return;
    }

    //opposite user profile
    public void goToUserProfile(View view){
        Intent intent = new Intent(MainActivity.this,UserProfile.class );
        //intent.putExtra("userId",oppUserId);
        startActivity(intent);
        return;
    }

    //go to tips
    public void goToTips(View view){
        Intent intent = new Intent(MainActivity.this, DatingTips.class);
        startActivity(intent);
        return;
    }

    public static String getOppUserId()
    {
        return oppUserId;
    }

}
