package com.example.Dieter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AboutScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_screen);

        getSupportActionBar().hide(); // hides the action bar

        // viewing the required text in a text view.
        TextView tv = (TextView) findViewById(R.id.AboutTextView);
        tv.setText(getString(R.string.aboutText));
        tv.setTextSize(30);
    }
}