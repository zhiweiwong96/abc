package com.example.zw.matchapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserProfile extends AppCompatActivity {

    //Bundle b = this.getIntent().getExtras();
     //String id = b.getString("userId");

    private static String id;

    private EditText mRace, mInterest, mEducation, mHoroscope, mReligion, mState;
    private EditText mAge,mGender;
    private EditText mNameField;
    private Button mBack;
    private ImageView mProfileImage;

    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;

    private FirebaseStorage storage;
    private StorageReference storageReference;

    private String userId, name, phone, profileImageUrl, userSex, race, interest, education, horoscope, age ,religion ,state;
    private Uri resultUri;
    private final int PICK_IMAGE_REQUEST = 71;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mAge = findViewById(R.id.age);
        mRace = findViewById(R.id.race);
        mInterest = findViewById(R.id.interest);
        mEducation = findViewById(R.id.education);
        mHoroscope = findViewById(R.id.horoscope);
        mReligion = findViewById(R.id.religion);
        mState = findViewById(R.id.states);
        mNameField = findViewById(R.id.name);
        mProfileImage =  findViewById(R.id.profileImage);
        mBack = findViewById(R.id.back);
        mGender = findViewById(R.id.gender);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        id = MainActivity.getOppUserId();

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(id);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        System.out.println(id);
        System.out.println("Inside userProfile");

        getUserInfo();

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                return;
            }
        });
    }


    private void getUserInfo() {
        mUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0 ){
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if(map.get("name") != null){
                        name = map.get("name").toString();
                        mNameField.setText(name);
                    }
                    if(map.get("sex") != null){
                        userSex = map.get("sex").toString();
                        mGender.setText(userSex);
                    }
                    if(map.get("age")!=null){
                        age = map.get("age").toString();
                        mAge.setText(age);
                    }
                    if(map.get("race")!=null){
                        race = map.get("race").toString();
                        mRace.setText(race);

                    }
                    if(map.get("interest")!=null){
                        interest = map.get("interest").toString();
                        mInterest.setText(interest);

                    }
                    if(map.get("education")!=null){
                        education = map.get("education").toString();
                        mEducation.setText(education);

                    }
                    if(map.get("horoscope")!=null){
                        horoscope = map.get("horoscope").toString();
                        mHoroscope.setText(horoscope);

                    }
                    if(map.get("religion")!=null){
                        religion = map.get("religion").toString();
                        mReligion.setText(religion);
                    }
                    if(map.get("state")!=null){
                        state = map.get("state").toString();
                        mState.setText(state);
                    }
                    if(map.get("profileImageUrl") != null){
                        profileImageUrl = map.get("profileImageUrl").toString();
                        switch(profileImageUrl){
                            case "default":
                                Glide.with(getApplication()).load(R.mipmap.ic_launcher).into(mProfileImage);
                                break;

                            default:
                                Glide.with(getApplication()).load(profileImageUrl).into(mProfileImage);;
                                break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
