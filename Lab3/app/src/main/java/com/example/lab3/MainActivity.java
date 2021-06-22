package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View.OnClickListener;


import java.text.NumberFormat;


public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, TextView.OnEditorActionListener,OnClickListener {


    //region :::VIEW COMPONENTS:::
    private EditText theBill;
    private TextView amountTotal;
    private TextView perPersonTotal;
    private RadioGroup tipOptions;
    private RadioButton tenPercent;
    private RadioButton fifteenPercent;
    private RadioButton twentyPercent;
    private SeekBar personSplitter;
    private CheckBox Member;
    private CheckBox Weekday;
    private CheckBox Special;
    private Button Submit;
    //endregion


    //region :::VARs::::
    private String billAmountString = "";
    private float tipPercent;
    private float billAmount;
    private float calculatedTotalAmount;
    private float aggregateDiscount = 0f;
    private float personsTotal = 0f;
    private int totalPpl;
    private NumberFormat currency = NumberFormat.getCurrencyInstance();
    private SharedPreferences prefs;
    //endregion

    //region :::GENERAL PURPOSE FUNCTIONS:::
    public void getTip() {
        int selected = tipOptions.getCheckedRadioButtonId();
        switch (selected) {
            case R.id.radioButton:
                tipPercent = 0.10f;
                break;
            case R.id.radioButton2:
                tipPercent = 0.15f;
                break;
            case R.id.radioButton3:
                tipPercent = 0.2f;
                break;
            default:
                tipPercent = 0f;
                break;
        }
    }

    public void getBill() {
        billAmountString = theBill.getText().toString();
        if (billAmountString.equals("")) {
            billAmount = 0;
        } else {
            billAmount = Float.parseFloat(billAmountString);
        }
    }
    public void setCheckboxes(String checkboxes){
        if(checkboxes!="nil"){
            String[] ids= checkboxes.split("-");
            for(int i = 0 ;i<ids.length;i++){
                switch ( Integer.parseInt(ids[i])){
                    case R.id.checkBox:
                        aggregateDiscount+=0.05f;
                        Member.setChecked(true);
                        break;
                    case R.id.checkBox2:
                        aggregateDiscount+=0.02f;
                        Weekday.setChecked(true);
                        break;
                    case R.id.checkBox3:
                        aggregateDiscount +=0.03f;
                        Special.setChecked(true);
                        break;
                    default:
                        break;
                }
            }

        }
    }
    public void setSplitter(){
        totalPpl = personSplitter.getProgress()+1;
    }
    public void setSplitter(int p){
        totalPpl = p+1;
    }
    public void setRadioGroup(int id){
        switch (id) {
            case R.id.radioButton:
                tenPercent.setChecked(true);
                tipPercent=0.1f;
                break;
            case R.id.radioButton2:
                fifteenPercent.setChecked(true);
                tipPercent=0.15f;
                break;
            case R.id.radioButton3:
                twentyPercent.setChecked(true);
                tipPercent=0.2f;
                break;
            default:
                break;
        }
    }
    public void setSplit() {
        personsTotal = calculatedTotalAmount/totalPpl;
        perPersonTotal.setText(currency.format(personsTotal));
    }

    public void setTotal() {
        calculatedTotalAmount = (1 + tipPercent - aggregateDiscount) * billAmount;
        amountTotal.setText(currency.format(calculatedTotalAmount));
    }

    public void onSubmitHandler() {
        getBill();
        getTip();
        setTotal();
        setSplitter();
        setSplit();
    }
    public String getActiveCheckBoxes(){
        String ret="";
        if(Member.isChecked()){
            ret = String.valueOf(Member.getId());
        }
        if(Weekday.isChecked()){
            ret += "-"+Weekday.getId();
        }
        if(Special.isChecked()){
            ret+= "-"+Special.getId();
        }
        return ret;
    }
    //endregion
    //region :::HANDLERS::
    public void onClick(View v) {
        onSubmitHandler();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        setSplitter(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
            case R.id.checkBox:
                aggregateDiscount = (checked) ? aggregateDiscount + 0.05f : aggregateDiscount - 0.05f;
                break;
            case R.id.checkBox2:
                aggregateDiscount = (checked) ? aggregateDiscount + 0.02f : aggregateDiscount - 0.02f;
                break;
            case R.id.checkBox3:
                aggregateDiscount = (checked) ? aggregateDiscount + 0.03f : aggregateDiscount - 0.03f;
            default:
                Log.d("err", "didnotpick");
        }
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
            onSubmitHandler();
        }
        return false;
    }

    //endregion
    //region :::VIEW CONTROLLERS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        theBill = (EditText) findViewById(R.id.editTextNumber);
        amountTotal = (TextView) findViewById(R.id.Totalamount);
        perPersonTotal = (TextView) findViewById(R.id.Toatlamountperperson);
        tipOptions = (RadioGroup) findViewById(R.id.RadioGroup);
        tenPercent = (RadioButton) findViewById(R.id.radioButton);
        fifteenPercent = (RadioButton) findViewById(R.id.radioButton2);
        twentyPercent = (RadioButton) findViewById(R.id.radioButton3);
        Member = (CheckBox) findViewById(R.id.checkBox);
        Weekday  = (CheckBox) findViewById(R.id.checkBox2);
        Special  = (CheckBox) findViewById(R.id.checkBox3);
        personSplitter = (SeekBar) findViewById(R.id.seekBar2);
        personSplitter.setMax(9);
        personSplitter.setProgress(0);
        Submit = (Button) findViewById(R.id.button);
        personSplitter.setOnSeekBarChangeListener(this);
        theBill.setOnEditorActionListener(this);
        Submit.setOnClickListener(this);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
    }
    @Override
    public void onPause() {
        // save the instance variables
        Toast.makeText(this, "onPause...",
                Toast.LENGTH_LONG).show();
        Editor editor = prefs.edit();
        editor.putString("billAmountString", billAmountString);
        editor.putString("CheckBoxes",getActiveCheckBoxes());
        editor.putInt("SelectedRadioGroup",tipOptions.getCheckedRadioButtonId());
        editor.putInt("splitterValue",personSplitter.getProgress());
        editor.commit();

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume...",
                Toast.LENGTH_LONG).show();
        billAmountString = prefs.getString("billAmountString", "");
        theBill.setText(billAmountString);
        int selectedcheck = prefs.getInt("SelectedRadioGroup",-1);
        setRadioGroup(selectedcheck);
        setCheckboxes(prefs.getString("CheckBoxes","nil"));
        setSplitter(prefs.getInt("splitterValue",0));
        personSplitter.setProgress(prefs.getInt("splitterValue",0));
        onSubmitHandler();
    }

    //endregion


}


