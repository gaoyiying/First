package com.swufe.stu.first;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class huilv2 extends AppCompatActivity {
    private static final String TAG = "huilv2";
    EditText output1,output2,output3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huilv2);
        Intent intent = getIntent();
        float dollar2 = intent.getFloatExtra("dollar_rate_key",0.0f);
        float euro2 = intent.getFloatExtra("euro_rate_key",0.0f);
        float won2 = intent.getFloatExtra("won_rate_key",0.0f);

        Log.i(TAG, "onCreate: dollar2=" + dollar2);
        Log.i(TAG, "onCreate: euro2=" + euro2);
        Log.i(TAG, "onCreate: won2=" + won2);

        output1 = findViewById(R.id.input1);
        output2 = findViewById(R.id.input2);
        output3 = findViewById(R.id.input3);

        output1.setText(String.valueOf(dollar2));
        output2.setText(String.valueOf(euro2));
        output3.setText(String.valueOf(won2));
    }
    public void Save(View btn){
        Log.i(TAG, "Save: ");
        //重新获得新的汇率数据
        float dollar = Float.parseFloat(output1.getText().toString());
        float euro = Float.parseFloat(output2.getText().toString());
        float won = Float.parseFloat(output3.getText().toString());

        Intent first = new Intent();
        first.putExtra("dollar_key",dollar);
        first.putExtra("euro_key",euro);
        first.putExtra("won_key",won);

        //Bundle bdl = new Bundle();
        //bdl.putFloat("key_dollar",dollar);
        //bdl.putFloat("key_euro",euro);
        //bdl.putFloat("key_won",won);
        //getIntent().putExtra(bdl);

        //startActivity(Save);
        setResult(3,first);
        finish();

    }

}