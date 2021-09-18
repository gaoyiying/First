package com.swufe.stu.first;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class twoteam extends AppCompatActivity {

    private static final String TAG = "BallActivity";
    int score1 = 0;
    int score2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twoteam);
    }

    public void click(View btn){
        Log.i(TAG, "click: ");
        if(btn.getId()==R.id.btn3){
            score1 += 3;
        }else if(btn.getId()==R.id.btn2){
            score1 += 2;
        }else if(btn.getId()==R.id.btn1){
            score1++;
        }else{
            //reset
            score1 = 0;
            score2 = 0;
        }
        show();
    }

    public void clickb(View btn){
        Log.i(TAG, "clickb: ");
        if(btn.getId()==R.id.btnb3){
            score2 += 3;
        }else if(btn.getId()==R.id.btnb2){
            score2 += 2;
        }else if(btn.getId()==R.id.btnb1){
            score2++;
        }
        show();
    }

    private void show() {
        //显示分数到控件中score1,score2
        TextView out1 = findViewById(R.id.score1);
        out1.setText(String.valueOf(score1));

        TextView out2 = findViewById(R.id.score2);
        out2.setText(String.valueOf(score2));
    }

}