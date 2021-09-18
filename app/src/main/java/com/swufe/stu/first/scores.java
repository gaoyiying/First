package com.swufe.stu.first;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class scores extends AppCompatActivity {

    private static final String TAG = "wwww";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score);


    }

    public void click(View btn) {
        Log.i(TAG, "onClick: aaa");
        int score = 0;
        if(btn.getId()==R.id.btn3){
            score += 3;
        }else if(btn.getId()==R.id.btn2){
            score += 2;
        }else if(btn.getId()==R.id.btn1){
            score += 1;
        }else if(btn.getId()==R.id.btn4){
            score = 0;
        }
        TextView output = findViewById(R.id.output);
        output.setText(String.valueOf(score));
    }
}