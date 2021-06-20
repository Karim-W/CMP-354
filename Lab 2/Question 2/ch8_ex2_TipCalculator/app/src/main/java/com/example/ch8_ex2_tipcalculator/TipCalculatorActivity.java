package com.example.ch8_ex2_tipcalculator;

import java.text.NumberFormat;

import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TipCalculatorActivity extends AppCompatActivity
        implements OnEditorActionListener, OnClickListener {

    // define variables for the widgets
    private EditText billAmountEditText;
    private TextView percentTextView;
    private Button   percentUpButton;
    private Button   percentDownButton;
    private TextView tipTextView;
    private TextView totalTextView;

    // define instance variables that should be saved
    private String billAmountString = "";
    private float tipPercent = .15f;

    // set up preferences
    private SharedPreferences prefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calculator);
        Toast.makeText(this, "The Help Feature has not been implemented",
                Toast.LENGTH_LONG).show();
        // get references to the widgets
        billAmountEditText = (EditText) findViewById(R.id.billAmountEditText);
        percentTextView = (TextView) findViewById(R.id.percentTextView);
        percentUpButton = (Button) findViewById(R.id.percentUpButton);
        percentDownButton = (Button) findViewById(R.id.percentDownButton);
        tipTextView = (TextView) findViewById(R.id.tipTextView);
        totalTextView = (TextView) findViewById(R.id.totalTextView);

        // set the listeners
        billAmountEditText.setOnEditorActionListener(this);
        percentUpButton.setOnClickListener(this);
        percentDownButton.setOnClickListener(this);

        // get default SharedPreferences object
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_tip_calculator, menu);

        return true;
    }

    @Override
    public void onPause() {
        // save the instance variables
        Editor editor = prefs.edit();
        editor.putString("billAmountString", billAmountString);
        editor.putFloat("tipPercent", tipPercent);
        editor.commit();

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();



        // get the instance variables
        billAmountString = prefs.getString("billAmountString", "");
        // if (rememberTipPercent) {
        tipPercent = prefs.getFloat("tipPercent", 0.15f);


        // set the bill amount on its widget
        billAmountEditText.setText(billAmountString);

        // calculate and display
        calculateAndDisplay();
    }

    public void calculateAndDisplay() {
        // get the bill amount
        billAmountString = billAmountEditText.getText().toString();
        float billAmount;
        if (billAmountString.equals("")) {
            billAmount = 0;
        }
        else {
            billAmount = Float.parseFloat(billAmountString);
        }

        // calculate tip and total
        float tipAmount = 0;
        float totalAmount = 0;
        float tipPercentToDisplay = 0;
        tipAmount = billAmount * tipPercent;
        totalAmount = billAmount + tipAmount;
        tipPercentToDisplay = tipPercent;
        // display the other results with formatting
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        tipTextView.setText(currency.format(tipAmount));
        totalTextView.setText(currency.format(totalAmount));

        NumberFormat percent = NumberFormat.getPercentInstance();
        percentTextView.setText(percent.format(tipPercentToDisplay));
    }
    public void onclickHandler(){
        // calculate tip and total
        float tipPercentToDisplay = 0;

        tipPercentToDisplay = tipPercent;

        NumberFormat percent = NumberFormat.getPercentInstance();
        percentTextView.setText(percent.format(tipPercentToDisplay));
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        int keyCode = -1;
        if (event != null) {
            keyCode = event.getKeyCode();
        }
        if (actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_UNSPECIFIED ||
                keyCode == KeyEvent.KEYCODE_DPAD_CENTER ||
                keyCode == KeyEvent.KEYCODE_ENTER) {
            calculateAndDisplay();
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.percentDownButton:
                tipPercent = tipPercent - .01f;
//                calculateAndDisplay();
                onclickHandler();
                break;
            case R.id.percentUpButton:
                tipPercent = tipPercent + .01f;
//                calculateAndDisplay();
                onclickHandler();
                break;
        }
    }

    public void refResh(MenuItem i){
        Toast.makeText(this, "Refreshing calc...",
                Toast.LENGTH_LONG).show();
        calculateAndDisplay();
    }

    public void launchBrowser(MenuItem m){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.onlineconversion.com/tip_calculator.htm"));
        startActivity(browserIntent);
    }
    public void ResultsHandler(MenuItem M){
        Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
        Bundle b = new Bundle();
        b.putString("bAmount",billAmountString);
        b.putString("TA", (String) totalTextView.getText());
        intent.putExtras(b);
        startActivity(intent);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_about:
                // Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}