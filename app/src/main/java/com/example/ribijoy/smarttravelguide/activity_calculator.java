package com.example.ribijoy.smarttravelguide;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class activity_calculator extends AppCompatActivity {

    private double num1=0, num2=0, result = 0;

    private TextView showResult;
    private String rslt = "";
    private String op = "";
    private boolean dot = false;

    private Button btnResult, btnDot,btnAc;
    private ArrayList<String> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        showResult = (TextView)findViewById(R.id.show_res);
        showResult.setText(rslt);



        btnResult = (Button) findViewById(R.id.btn_equal);
        btnDot = (Button) findViewById(R.id.btn_dot);
        //btnAc = (Button) findViewById(R.id.btnAc);







    }

    //backpress--------------------------------------------------------------
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(activity_calculator.this, activity_home.class);
        startActivity(intent);

    }

    public void operation(View view) {
        Button btn = (Button) view;
        if(rslt.length()>0){
            num1 = Double.parseDouble(rslt);

            showResult.setText("");
            //num2 = 0;
            //result = 0;
            rslt = "";
            op = btn.getText().toString();
        }
        else{
            Context context = getApplicationContext();

            Toast toast = Toast.makeText(this, R.string.enter_number,Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void equal(View view) {
        if(op.equals("+")){
            num2 = Double.parseDouble(rslt);

            result = num1+num2;

            rslt = String.valueOf(result);
            showResult.setText(rslt);



        }
        else if(op.equals("-")){
            num2 = Double.parseDouble(rslt);

            result = num1-num2;

            rslt = String.valueOf(result);
            showResult.setText(rslt);



        }
        else if(op.equals("X")){
            num2 = Double.parseDouble(rslt);

            result = num1*num2;

            rslt = String.valueOf(result);
            showResult.setText(rslt);


        }
        else if(op.equals("/")){
            num2 = Double.parseDouble(rslt);

            result = num1/num2;

            rslt = String.valueOf(result);
            showResult.setText(rslt);


        }
        //else {
          //  result = num1;
            //rslt = String.valueOf(result);
            //showResult.setText(rslt);
        //}






    }

    public void number(View view) {
        Button btn = (Button) view;


        if (showResult != null){
            rslt = rslt + btn.getText();

            showResult.setText(rslt);

        }

    }

    public void numberDot(View view) {


        //if (showResult != null){
        if (!rslt.contains(".")){
            rslt = rslt + btnDot.getText();

            showResult.setText(rslt);

        }
        // }
    }

    public void percent(View view) {
        if (rslt.length()>0) {
            num1 = Double.parseDouble(rslt);

            result = num1/100;

            rslt = String.valueOf(result);
            showResult.setText(rslt);
        }
        else{
            Context context = getApplicationContext();

            Toast toast = Toast.makeText(this, R.string.enter_number,Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void pi(View view) {
        if (showResult != null) {
            rslt = "3.1416";

            showResult.setText(rslt);

        }
    }

    public void xinverse(View view) {
        if (rslt.length()>0) {
            num1 = Double.parseDouble(rslt);

            result = 1/num1;

            rslt = String.valueOf(result);
            showResult.setText(rslt);

        }
        else{
            Context context = getApplicationContext();

            Toast toast = Toast.makeText(this, R.string.enter_number,Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    public void fact(View view) {
        if (rslt.length()>0) {
            num1 = Double.parseDouble(rslt);
            result = 1;


            for (int i=1; i<=num1; i++){
                result = result*i;
            }

            rslt = String.valueOf(result);
            showResult.setText(rslt);

        }
        else{
            Context context = getApplicationContext();

            Toast toast = Toast.makeText(this, R.string.enter_number,Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void clear(View view) {
        Button btn = (Button) view;

        showResult.setText("");
        num1 = 0;
        num2 = 0;
        result = 0;
        rslt = "";



        Context context = getApplicationContext();

        Toast toast = Toast.makeText(this, R.string.screen_clear,Toast.LENGTH_SHORT);
        toast.show();



    }

    public void delete(View view) {
        if (rslt.length()>0) {

            rslt = rslt.substring(0,rslt.length()-1);
            showResult.setText(rslt);
        }
        else{
            Context context = getApplicationContext();

            Toast toast = Toast.makeText(this, R.string.screen_clear,Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void sq(View view) {
        if (rslt.length()>0) {
            num1 = Double.parseDouble(rslt);

            result = num1*num1;

            rslt = String.valueOf(result);
            showResult.setText(rslt);

        }
        else{
            Context context = getApplicationContext();

            Toast toast = Toast.makeText(this, R.string.enter_number,Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void sqroot(View view) {
        if (rslt.length()>0) {
            num1 = Double.parseDouble(rslt);

            result = Math.sqrt(num1);

            rslt = String.valueOf(result);
            showResult.setText(rslt);


        }
        else{
            Context context = getApplicationContext();

            Toast toast = Toast.makeText(this, R.string.enter_number,Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
