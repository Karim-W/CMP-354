package com.example.invoicetotal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    Button submitButton;
    EditText subtotal;
    private SharedPreferences prefs;
    private String billAmountString;
    private  TextView discount;
    private Float tipPercent = Float.parseFloat("0.2") ;;

    public void calculateAndDisplay(View view) {
        billAmountString = subtotal.getText().toString();
        float billAmount;
        if (billAmountString.equals("")) {
            billAmount = 0;
        }
        else {
            billAmount = Float.parseFloat(billAmountString);
        }
        subtotal = (EditText) findViewById(R.id.editTextNumberDecimal);
        Float fsubTotal = Float.parseFloat(subtotal.getText().toString());
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        NumberFormat Percentage = NumberFormat.getPercentInstance();
        discount = (TextView) findViewById(R.id.textView8);
        discount.setText(Percentage.format(0.2));
        TextView total = (TextView) findViewById(R.id.textView6);
        TextView dAmount = (TextView) findViewById(R.id.textView7);
        double amount = fsubTotal * 0.2;
        dAmount.setText(currency.format(amount));
        total.setText(currency.format(fsubTotal + amount));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        submitButton = (Button) findViewById(R.id.button);
        subtotal = (EditText) findViewById(R.id.editTextNumberDecimal);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

    }
    public void resumeCalc(){
        billAmountString = subtotal.getText().toString();
        float billAmount;
        if (billAmountString.equals("")) {
            billAmount = 0;
        }
        else {
            billAmount = Float.parseFloat(billAmountString);
        }
        subtotal = (EditText) findViewById(R.id.editTextNumberDecimal);
        Float fsubTotal = Float.parseFloat(subtotal.getText().toString());
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        NumberFormat Percentage = NumberFormat.getPercentInstance();
        TextView discount = (TextView) findViewById(R.id.textView8);
        discount.setText(Percentage.format(0.2));
        TextView total = (TextView) findViewById(R.id.textView6);
        TextView dAmount = (TextView) findViewById(R.id.textView7);
        double amount = fsubTotal * 0.2;
        dAmount.setText(currency.format(amount));
        total.setText(currency.format(fsubTotal + amount));
    }

    @Override
    public void onPause() {
        super.onPause();
        // save the instance variables
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("billAmountString", billAmountString);
        editor.commit();
        Toast.makeText(this, "pausing...",
                Toast.LENGTH_LONG).show();


    }
    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(this, "resuming...",
                Toast.LENGTH_LONG).show();


        // get the instance variables
        billAmountString = prefs.getString("billAmountString", "");
        // if (rememberTipPercent) {
        //tipPercent = prefs.getFloat("tipPercent", 0.15f);


        // set the bill amount on its widget
        subtotal.setText(billAmountString);
        NumberFormat Percentage = NumberFormat.getPercentInstance();
        //discount.setText(Percentage.format(0.2));


        // calculate and display
        resumeCalc();
    }

}
