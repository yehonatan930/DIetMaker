package com.example.Dieter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ErrorScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_screen);

        // a screen for showing error messages.
        // gets string error messages form intents and views them in TextView fields
        // each error message comes from a different place to debug error's location.

        // first one
        String errorMessage = getIntent().getStringExtra("ErrorMessage");
        TextView tv = (TextView) findViewById(R.id.ErrorTextView1);
        tv.setText(errorMessage);
        // second one
        String errorMessage2 = getIntent().getStringExtra("ErrorMessage2");
        TextView tv2 = (TextView) findViewById(R.id.ErrorTextView2);
        tv2.setText(errorMessage2);
    }
}