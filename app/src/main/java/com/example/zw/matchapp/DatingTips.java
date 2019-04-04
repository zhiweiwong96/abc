package com.example.zw.matchapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DatingTips extends AppCompatActivity {

    private Button mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dating_tips);
       // setContentView(R.layout.scrollable_textview);

        mBack = findViewById(R.id.back);

        TextView textView = findViewById(R.id.text_view);
        textView.setMovementMethod(new ScrollingMovementMethod());

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                return;
            }
        });
    }
}
