package com.example.Dieter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class StartScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        getSupportActionBar().hide(); // hides the action bar

        Button aboutButton = (Button) findViewById(R.id.AboutButton); // button for screen w/ info about project
        Button howToUseButton = (Button) findViewById(R.id.HowToUseButton); // button for screen w/ info about  how to use the app
        Button enterDietButton = (Button) findViewById(R.id.EnterDietButton); // button for screen for physical data input or output
        Button resetDietButton = (Button) findViewById(R.id.ResetDietButton); // button for resetting diet data

        enterDietButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // when tapping on enterDietButton
                // leads to DietFirstScreen activity if there is no diet data, if there is then leads to DietSecondScreen activity.
                String data = readFromFile("DATA"); // reading data
                if (data == "") {startActivity(new Intent(getApplicationContext(), DietFirstScreen.class));}
                else {startActivity(new Intent(getApplicationContext(), DietSecondScreen.class));}
            }
        });

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // leads to AboutScreen activity
                startActivity(new Intent(getApplicationContext(), AboutScreen.class));
            }
        });

        howToUseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // leads to HowToUseScreen activity
                startActivity(new Intent(getApplicationContext(), HowToUseScreen.class));
            }
        });

        resetDietButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // resetting data by rewriting the data file
                writeToFile("DATA", "", "w");
            }
        });

    }
    public String readFromFile(String filename) {
        // receive a string containing a file's name.
        // reads string from file
        String ret = "";

        try {
            // entering file
            InputStream inputStream = openFileInput(filename+".txt");

            if ( inputStream != null ) {
                // as long as file has a value
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    // as there is still text ahead
                    // appends the next text, byte by byte
                    stringBuilder.append(receiveString);
                }
                // closing file
                inputStream.close();
                // bytes to string
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            // send error to be shown in ErrorScreen activity
            Intent intent = new Intent(getBaseContext(), ErrorScreen.class);
            intent.putExtra("ErrorMessage2", "File not found: " + e.toString());
            startActivity(intent);
        } catch (IOException e) {
            // send error to be shown in ErrorScreen activity
            Intent intent = new Intent(getBaseContext(), ErrorScreen.class);
            intent.putExtra("ErrorMessage2", "Can not read file: " + e.toString());
            startActivity(intent);
        }

        return ret;
    }
    public void writeToFile(String filename, String data, String mode) {
        // recieves string filename, data as string and mode as string
        // write data to file w/ filename according to mode
        try {
            FileOutputStream fos = null;
            // "w" => rewrite file w/ data. "a" => appending data to file.
            if (mode.equals("w")) {fos = openFileOutput(filename+".txt", MODE_PRIVATE);}
            if (mode.equals("a")) {fos = openFileOutput(filename+".txt", MODE_APPEND);}

            // entering file
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);
            outputStreamWriter.write(data); // writing data to file
            outputStreamWriter.close(); // closing file
        }
        catch (Exception e) {
            // send error to be shown in ErrorScreen activity
            Intent intent = new Intent(getBaseContext(), ErrorScreen.class);
            intent.putExtra("ErrorMessage2", "File write failed: " + e.toString());
            startActivity(intent);
        }
    }

}