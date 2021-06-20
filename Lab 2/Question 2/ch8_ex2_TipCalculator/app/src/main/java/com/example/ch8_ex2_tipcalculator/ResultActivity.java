package com.example.ch8_ex2_tipcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

public class ResultActivity extends AppCompatActivity {

    private TextView totalTextView;
    private TextView billAmountTV;
    private TextView PrevtotalTextView;

    // define instance variables that should be saved
    private String billAmountString = "";
    private float tipPercent = .15f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultslayout);
        Bundle b = getIntent().getExtras();
        String value = "-1";
        if(b != null)
            value = b.getString("bAmount");
        totalTextView = (TextView) findViewById(R.id.ResTotalAmt);
        billAmountTV = (TextView) findViewById(R.id.ResBAmt);
        billAmountTV.setText(value);
        PrevtotalTextView = (TextView) findViewById(R.id.totalTextView);
        String value2 = "-1"; // or other values
        if(b != null)
            value2 = b.getString("TA");
        totalTextView.setText(value2);

        // set the listeners
    }

}
