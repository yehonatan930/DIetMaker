package com.example.Dieter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class HowToUseScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_use_screen);

        getSupportActionBar().hide(); // hides the action bar

        // viewing the required text in a text view.
        TextView tv = (TextView) findViewById(R.id.HowToUseTextView);
        tv.setText(getString(R.string.HowToUseText));
        tv.setTextSize(25);
    }
}