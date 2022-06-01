package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etWeight, etType, etValue;
    Button btnCalculate, btnReset;
    TextView tvTotal, tvTotal2, tvTotal3;

    double weight;
    double value;
    String type;

    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = (EditText) findViewById(R.id.editTextNumberDecimal);
        etType = (EditText) findViewById(R.id.editTextTextPersonName);
        etValue = (EditText) findViewById(R.id.editTextNumberDecimal2);

        tvTotal = (TextView) findViewById(R.id.tvTotal);
        tvTotal2 = (TextView) findViewById(R.id.tvTotal2);
        tvTotal3 = (TextView) findViewById(R.id.tvTotal3);

        btnCalculate = (Button) findViewById(R.id.btnCalculate);
        btnReset = (Button) findViewById(R.id.btnReset);

        btnReset.setOnClickListener(this);
        btnCalculate.setOnClickListener(this);


        sharedPref = this.getSharedPreferences("total", Context.MODE_PRIVATE);

        //load data
        weight = sharedPref.getInt("weight", 0);
        type = sharedPref.getString("type", String.valueOf(0));
        value = sharedPref.getInt("value", 0);

        etWeight.setText("" + weight);
        etType.setText("" + type);
        etValue.setText("" + value);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnCalculate:

                try {
                    String variable = etWeight.getText().toString();
                    String variable1 = etType.getText().toString();
                    String variable2 = etValue.getText().toString();

                    double weight = Double.parseDouble(etWeight.getText().toString());
                    double value = Double.parseDouble(etValue.getText().toString());
                    String type = etType.getText().toString();

                    double totalValueGold = weight * value;

                    double totalValueType = 0;

                    if (type.equals("keep")) {
                        totalValueType = (weight - 85) * value;
                    } else {
                        if (type.equals("wear")) {
                            totalValueType = (weight - 200) * value;
                        }
                    }

                    double totalZakat = 0.025 * totalValueType;

                    tvTotal.setText(String.format("RM%s", totalValueGold));
                    tvTotal2.setText(String.format("RM%s", totalValueType));
                    tvTotal3.setText(String.format("RM%s", totalZakat));

                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("weight", (int) weight);
                    editor.putString("type", type);
                    editor.putInt("value", (int) value);
                    editor.apply();

                } catch (NumberFormatException nfe){
                    Toast.makeText(this, "Please enter valid number", Toast.LENGTH_LONG).show();

                }catch (Exception exp){
                    Toast.makeText(this, "Unknown Exception" + exp.getMessage(), Toast.LENGTH_LONG).show();
                    Log.d("Exception", exp.getMessage());
                }

                break;

            case R.id.btnReset:

                weight = 0;
                type = "";
                value = 0;

                etWeight.setText("" + weight);
                etType.setText("" + type);
                etValue.setText("" + value);

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("weight", (int) weight);
                editor.putString("type", type);
                editor.putInt("Value", (int) value);
                editor.apply();

                break;

        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate (R.menu.menu, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.profile:
                Toast.makeText(this, "This is profile", Toast.LENGTH_LONG).show();
                break;

            case R.id.settings:
                Toast.makeText(this, "This is setting", Toast.LENGTH_LONG).show();
                break;

            case R.id.about:
                //Toast.makeText(this, "This is about", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }



}