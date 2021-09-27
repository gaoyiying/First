package com.swufe.stu.first;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

public class huilv extends AppCompatActivity  {
    private static final String TAG = "huilv";
    EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huilv);
        TextView output = findViewById(R.id.output);
        Log.i("11111", "onCreate: fg");
        edit = findViewById(R.id.input);
    }
    public void Click(View btn) {
        Log.i(TAG, "onClick: AAAAA");
        //用户输入数据
        String number = edit.getText().toString();
        if(number != null && number.length()>0){
            double n = Double.parseDouble(number);
            double nn = 0;
            if(btn.getId()==R.id.DOLLAR){
                nn = n * 0.1548;
            }else if(btn.getId()==R.id.btn2){
                nn = n * 0.1323;
            }else if(btn.getId()==R.id.btn1){
                nn = n * 182.5773;
            }
            TextView output = findViewById(R.id.output);
            output.setText(String.valueOf(nn));
        }else{
            Toast.makeText(this, "请输入数据", Toast.LENGTH_SHORT).show();
        }

    }
}