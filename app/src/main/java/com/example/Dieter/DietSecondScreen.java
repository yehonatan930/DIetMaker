package com.example.Dieter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DietSecondScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_second_screen);

        getSupportActionBar().hide(); // hides the action bar

        // getting the data from the data file
        String data = readFromFile("DATA");
        String[] vars = data.split("-"); // splitting it to a string array
        double bmi = Double.parseDouble(vars[0]);
        double height = Double.parseDouble(vars[1]);
        double weight = Double.parseDouble(vars[2]);
        double age = Double.parseDouble(vars[3]);
        String gender = vars[4];
        String state = vars[5];
        final int calories = Integer.parseInt(vars[6]);
        final double workout = Double.parseDouble(vars[7]);

        // setting the title
        String diet2Title = String.format(getResources().getString(R.string.Diet2Title), state, calories, workout);
        TextView titv = (TextView) findViewById(R.id.TitleInfoTextView);
        titv.setText(diet2Title);

        // setting title seek bar which is only for show.
        SeekBar stateSeekBar = (SeekBar) findViewById(R.id.StateSeekBar);
        stateSeekBar.setMax(40 * 100);
        stateSeekBar.setProgress((int) (bmi * 100));
        stateSeekBar.setEnabled(false);

        // setting the calories seek bar w/ values 0~calories*2
        // and the workout time seek bar w/ values 0~40
        SeekBar caloriesSeekBar = (SeekBar) findViewById(R.id.CaloriesSeekBar);
        SeekBar workoutSeekBar = (SeekBar) findViewById(R.id.WorkoutSeekBar);
        caloriesSeekBar.setMax(calories * 2);
        workoutSeekBar.setMax(40);

        // when the calories seek bar is changed
        caloriesSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                // views the current value of seek bar below it.
                TextView caloriesTextView = (TextView) findViewById(R.id.CaloriesTextView);
                if(progress == seekBar.getMax())
                    caloriesTextView.setText(progress + "+");
                else
                    caloriesTextView.setText("" + progress);
                // location:
                //int val = (progress * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
                //textView.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2);

                // tells the user if he ate too little~too much by the seek bar progress.
                TextView caloriesResponseTextView = (TextView) findViewById(R.id.CaloriesResponseTextView);
                if (progress < 0.75*calories)
                    // too little
                    caloriesResponseTextView.setText(getResources().getStringArray(R.array.ResponseMessage)[0]);
                else if (progress > 1.25*calories)
                    // too much
                    caloriesResponseTextView.setText(getResources().getStringArray(R.array.ResponseMessage)[2]);
                else
                    // fine
                    caloriesResponseTextView.setText(getResources().getStringArray(R.array.ResponseMessage)[1]);

                // calculating the difference between progress and the required calories
                double caloriesDiff = Math.abs(calories-progress);
                Quals.cqual = assesDiffQuality(caloriesDiff, calories); // put it in Quals.cqual

                // show in summary text view of screen the overall assessments of today's work.
                TextView summaryMessageTextView = findViewById(R.id.Diet2SummaryMessageTextView);
                String summaryMessage = getResources().getString(R.string.Diet2SummaryMessage);
                summaryMessage = String.format(summaryMessage, assesAllQuality());
                summaryMessageTextView.setText(summaryMessage);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        // when the workout seek bar is changed
        workoutSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                // views the current value of seek bar below it.
                // the workout time is required to be a double, so it will be divide by 10: 0~40 => 0~4 hours.
                TextView workoutTextView = (TextView) findViewById(R.id.WorkoutTextView);
                if(progress == seekBar.getMax())
                    workoutTextView.setText(((double)(progress)/10) + "+");
                else
                    workoutTextView.setText("" + (double)(progress)/10);
                // location:
                //int val = (progress * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
                //textView.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2);

                // tells the user if he ate too little~too much by the seek bar progress.
                TextView workaoutResponseTextView = (TextView) findViewById(R.id.WorkoutResponseTextView);
                if ((double)(progress)/10 < 0.75*workout)
                    // too little
                    workaoutResponseTextView.setText(getResources().getStringArray(R.array.ResponseMessage)[0]);
                else if ((double)(progress)/10 > 1.25*workout)
                    // too much
                    workaoutResponseTextView.setText(getResources().getStringArray(R.array.ResponseMessage)[2]);
                else
                    // fine
                    workaoutResponseTextView.setText(getResources().getStringArray(R.array.ResponseMessage)[1]);

                // calculating the difference between progress and the required workout hours
                double workoutDiff = Math.abs(workout-(double)(progress)/10);
                Quals.wqual = assesDiffQuality(workoutDiff, workout);// put it in Quals.cqual

                // show in summary text view of screen the overall assessments of today's work.
                TextView summaryMessageTextView = findViewById(R.id.Diet2SummaryMessageTextView);
                String summaryMessage = getResources().getString(R.string.Diet2SummaryMessage);
                summaryMessage = String.format(summaryMessage, assesAllQuality());
                summaryMessageTextView.setText(summaryMessage);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public String readFromFile(String filename) {

        String ret = "";

        try {
            InputStream inputStream = openFileInput(filename+".txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Intent intent = new Intent(getBaseContext(), ErrorScreen.class);
            intent.putExtra("ErrorMessage2", "File not found: " + e.toString());
            startActivity(intent);
        } catch (IOException e) {
            Intent intent = new Intent(getBaseContext(), ErrorScreen.class);
            intent.putExtra("ErrorMessage2", "Can not read file: " + e.toString());
            startActivity(intent);
        }

        return ret;
    }

    public int assesDiffQuality(double diff, double goodVal){
        // receives the difference between a value and goodVal, and goodVal
        // assessing the quality of the difference relatively to goodVal
        // return the quality as int 0~2
        if(diff > 0.625*goodVal)
            // very bad
            return 0;
        else if(diff > 0.25*goodVal)
            // not so good
            return 1;
        else
            // great
            return 2;
    }

    public String assesAllQuality(){
        // returns the average quality of Quals' vars as string words.
        double final_qual = (Quals.cqual+Quals.wqual)/2; // average
        if (final_qual == 0.0)
            return "very bad";
        if (final_qual == 0.5)
            return "bad";
        if (final_qual == 1.0)
            return "not so good";
        if (final_qual == 1.5)
            return "good";
        if (final_qual == 2.0)
            return "great";
        return "";
    }
}
class Quals {
    // a class for the global integers cqual and wqual, representing calories' consumption quality
    // and workout time quality, respectively.
    public static int cqual = 0;
    public static int wqual = 0;
}
