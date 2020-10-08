package com.rajpatel.distanceconverter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity_RP";
    private EditText userInput;
    private TextView historyOutputField;
    private TextView defaultOption;
    private TextView secondOption;
    private TextView answerOutput;
    private RadioButton mileRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null)
            Toast.makeText(this, "savedInstanceState is NULL", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "savedInstanceState is NOT NULL", Toast.LENGTH_LONG).show();

        userInput = findViewById(R.id.milesouputLabel);
        mileRadio = findViewById(R.id.milesOption);
        defaultOption = findViewById(R.id.milestextView);
        secondOption = findViewById(R.id.kilometertextView);
        historyOutputField = findViewById(R.id.conversiontextView);
        answerOutput = findViewById(R.id.answerOutputLabel);
        historyOutputField.setMovementMethod(new ScrollingMovementMethod());

    }

    public void onStart() {
        super.onStart();
        Log.d(TAG,"OnStart:");
    }

    public void clear(View v) {
        Log.d(TAG,"Clear Button Clicked");
        historyOutputField.setText("");
    }


    @SuppressLint("DefaultLocale")
    public void calculate(View v) {

        double metricInput = Double.parseDouble(userInput.getText().toString());
        userInput.setText("");
        String  prev = historyOutputField.getText().toString();
        double result;
        String curr = "";
        if(mileRadio.isChecked()) {
            result = calcKilometer(metricInput);
            curr = String.format("%.1f Mi ==> %.1f Km",metricInput,result);
            answerOutput.setText(String.format("%.1f",result));
        }
        else {
            result = calcMiles(metricInput);
            curr = String.format("%.1f Km ==> %.1f Mi",metricInput,result);
            answerOutput.setText(String.format("%.1f",result));
        }
        historyOutputField.setText(String.format("%s\n%s",curr,prev));
        Log.d(TAG,"milesSubmit " + metricInput);

    }

    public double calcKilometer(double kiloInput) {
        return kiloInput * 1.60934;
    }

    public double calcMiles(double mileInput) {
        return mileInput * 0.621371;
    }


    public void radioOnClick(View v) {
        String message = "You selected ";
        switch (v.getId()) {
            case R.id.milesOption:
                message += "miles";
                defaultOption.setText(R.string.miles_value);
                secondOption.setText(R.string.kilometers_value);
                Log.d(TAG,"miles options selected");
                break;

            case R.id.kilometersOption:
                message += "kilometers";
                defaultOption.setText(R.string.kilometers_value);
                secondOption.setText(R.string.miles_value);
                Log.d(TAG,"kilometers options selected");
                break;
        }
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }

    // Bundle object is like hashmap
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putString("HISTORY",historyOutputField.getText().toString());
        outState.putString("INPUT",userInput.getText().toString());

        // call super last
        super.onSaveInstanceState(outState);

    }



    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {

        // call super first
        super.onRestoreInstanceState(savedInstanceState);

        historyOutputField.setText(savedInstanceState.getString("HISTORY"));
        userInput.setText(savedInstanceState.getString("INPUT"));
    }
}