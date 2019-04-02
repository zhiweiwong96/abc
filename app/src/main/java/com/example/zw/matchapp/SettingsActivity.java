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

public class SettingsActivity extends AppCompatActivity {

    private Spinner mRace, mInterest, mEducation, mHoroscope, mReligion, mState;
    private EditText mAge;

    private EditText mNameField, mPhoneField;

    private Button mBack, mConfirm;

    private RadioGroup mRadioGroup;
    private RadioButton mRadioButton;

    private ImageView mProfileImage;

    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;

    //add image
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private String userId, name, phone, profileImageUrl, userSex, race, interest, education, horoscope, age ,religion ,state;

    private Uri resultUri;
    private final int PICK_IMAGE_REQUEST = 71;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mAge = findViewById(R.id.age);
        mRace = findViewById(R.id.race);
        mInterest = findViewById(R.id.interest);
        mEducation = findViewById(R.id.education);
        mHoroscope = findViewById(R.id.horoscope);
        mReligion = findViewById(R.id.religion);
        mState = findViewById(R.id.states);

        mRadioButton = findViewById(R.id.male);

        mRadioGroup = findViewById(R.id.radioGroup);
        mNameField = findViewById(R.id.name);
        mPhoneField =  findViewById(R.id.phone);
        mProfileImage =  findViewById(R.id.profileImage);
        mBack = findViewById(R.id.back);
        mConfirm =  findViewById(R.id.confirm);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        //add image
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        getUserInfo();

        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
                */


                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);

            }
        });
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInformation();
            }
        });
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
                    if(map.get("phone") != null){
                        phone = map.get("phone").toString();
                        mPhoneField.setText(phone);
                    }
                    if(map.get("sex") != null){
                        userSex = map.get("sex").toString();

                        System.out.println("UserSex = "+ userSex);
                        if(userSex.equals("Male")){
                            mRadioGroup.check(R.id.male);
                        }else{
                            mRadioGroup.check(R.id.female);
                        }
                    }

                    // add on
                    if(map.get("age")!=null){
                        age = map.get("age").toString();
                        mAge.setText(age);
                    }
                    if(map.get("race")!=null){
                        race = map.get("race").toString();
                        //set spinner value
                        mRace.setSelection(((ArrayAdapter)mRace.getAdapter()).getPosition(race));
                    }
                    if(map.get("interest")!=null){
                        interest = map.get("interest").toString();
                        //set spinner value
                        mInterest.setSelection(((ArrayAdapter)mInterest.getAdapter()).getPosition(interest));
                    }
                    if(map.get("education")!=null){
                        education = map.get("education").toString();
                        //set spinner value
                        mEducation.setSelection(((ArrayAdapter)mEducation.getAdapter()).getPosition(education));
                    }
                    if(map.get("horoscope")!=null){
                        horoscope = map.get("horoscope").toString();
                        //set spinner value
                        mHoroscope.setSelection(((ArrayAdapter)mHoroscope.getAdapter()).getPosition(horoscope));
                    }
                    if(map.get("religion")!=null){
                        religion = map.get("religion").toString();
                        //set spinner value
                        mReligion.setSelection(((ArrayAdapter)mReligion.getAdapter()).getPosition(religion));
                    }
                    if(map.get("state")!=null){
                        state = map.get("state").toString();
                        //set spinner value
                        mState.setSelection(((ArrayAdapter)mState.getAdapter()).getPosition(state));
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

    private void saveUserInformation(){
        name = mNameField.getText().toString();
        phone = mPhoneField.getText().toString();

        //add on
        int selectId = mRadioGroup.getCheckedRadioButtonId();
        final RadioButton radioButton = findViewById(selectId);
        if(radioButton.getText() == null){
            return;
        }

        userSex = radioButton.getText().toString();

        age = mAge.getText().toString();
        race = mRace.getSelectedItem().toString();
        interest = mInterest.getSelectedItem().toString();
        education = mEducation.getSelectedItem().toString();
        horoscope = mHoroscope.getSelectedItem().toString();
        religion = mReligion.getSelectedItem().toString();
        state = mState.getSelectedItem().toString();


        Map userInfo = new HashMap<>();
        userInfo.put("name",name);
        userInfo.put("phone",phone);
        //add on
        userInfo.put("sex",userSex);
        userInfo.put("age",age);
        userInfo.put("race",race);
        userInfo.put("interest",interest);
        userInfo.put("education",education);
        userInfo.put("horoscope",horoscope);
        userInfo.put("religion",religion);
        userInfo.put("state",state);

        mUserDatabase.updateChildren(userInfo);
        if(resultUri != null){

            final StorageReference filepath = FirebaseStorage.getInstance().getReference().child("profileImages").child(userId);
            Bitmap bitmap = null;

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), resultUri);
            }catch(IOException e){
                e.printStackTrace();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20 ,baos);
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = filepath.putBytes(data);

            //when failed to upload image
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    finish();
                }
            });

            //when successfully upload image
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Map newImage = new HashMap();
                            newImage.put("profileImageUrl", uri.toString());
                            mUserDatabase.updateChildren(newImage);

                            System.out.println("Update image successful");

                            finish();
                            return;
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            finish();
                            return;
                        }
                    });
                }
            });

        }else{
            finish();
        }

        //uploadImage();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        /*
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            final Uri imageUri = data.getData();
            resultUri = imageUri;
            mProfileImage.setImageURI(resultUri);
        }
        */
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null){
            resultUri = data.getData();
            try{
                Bitmap bitmap  = MediaStore.Images.Media.getBitmap(getContentResolver(),resultUri);
                mProfileImage.setImageBitmap(bitmap);
            }catch(IOException e){
                e.printStackTrace();
            }

        }
    }


    /*
    private void uploadImage(){
        if (resultUri != null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("uploading ...");
            progressDialog.show();

            StorageReference filepath = FirebaseStorage.getInstance().getReference().child("profileImages").child(userId);

            filepath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(SettingsActivity.this,"Uploaded",Toast.LENGTH_SHORT ).show();
                }
            });
        }
    }
    */
}
