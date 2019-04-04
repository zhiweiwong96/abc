package com.example.zw.matchapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class DatingTips extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dating_tips);
       // setContentView(R.layout.scrollable_textview);

        TextView textView = findViewById(R.id.text_view);
        textView.setMovementMethod(new ScrollingMovementMethod());
    }
}
