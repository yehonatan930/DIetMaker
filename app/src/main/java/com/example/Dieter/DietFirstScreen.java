package com.example.Dieter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class DietFirstScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_first_screen);

        getSupportActionBar().hide(); // hides the action bar

        Button calcDietButton = (Button) findViewById(R.id.CalcDietButton);
        // all the actions takes place when the user's tapping calcDietButton
        calcDietButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // the gender spinnre
                Spinner genderSpinner = (Spinner) findViewById(R.id.GenderSpinner);
                // getting the gender as string from it.
                final String gender = genderSpinner.getSelectedItem().toString();

                EditText heightEditText = (EditText) findViewById(R.id.HeightEditText);
                EditText weightEditText = (EditText) findViewById(R.id.WeightEditText);
                EditText ageEditText = (EditText) findViewById(R.id.AgeEditText);
                // getting the physical data from EditTexts
                double height = Double.parseDouble(heightEditText.getText().toString());
                double weight = Double.parseDouble(weightEditText.getText().toString());
                double age = Double.parseDouble(ageEditText.getText().toString());

                // setting bmi by the BMI formula
                double bmi = weight / Math.pow(height / 100, 2);
                bmi = Double.parseDouble(String.format("%.2f", bmi)); // limiting it to 2 digits after dot.

                // setting calories
                int calories = 0;
                // formula from https://www.calculator.net/calorie-calculator.html
                if (gender.equals("Male")) {
                    calories = (int) (13.397 * weight + 4.799 * height - 5.677 * age + 88.362);
                }
                if (gender.equals("Female")) {
                    calories = (int) (9.247 * weight + 3.098 * height - 4.330 * age + 447.593);
                }

                // setting the state and workout time by the bmi value
                String state = " ";
                double wrkout = 0.0;
                if (bmi < 18.5) {
                    state = "UNDERWEIGHT";
                    wrkout = 0.5;
                }
                if (bmi >= 18.5 && bmi <= 20) {
                    state = "Healthy";
                    wrkout = 1;
                }
                if (bmi > 20 && bmi < 30) {
                    state = "OVERWEIGHT";
                    wrkout = 2;
                }
                if (bmi >= 30) {
                    state = "OBESE!";
                    wrkout = 3;
                }

                // writing to file all data to be used by DietSecondScreen activity
                writeToFile("DATA", bmi + "", "w");
                writeToFile("DATA", "-" + height, "a");
                writeToFile("DATA", "-" + weight, "a");
                writeToFile("DATA", "-" + age, "a");
                writeToFile("DATA", "-" + gender, "a");
                writeToFile("DATA", "-" + state, "a");
                writeToFile("DATA", "-" + calories, "a");
                writeToFile("DATA", "-" + wrkout, "a");
                // then goes to it
                startActivity(new Intent(getApplicationContext(), DietSecondScreen.class));
            }
        });
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