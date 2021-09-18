package com.swufe.stu.first;

import android.icu.util.Output;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "wwww";
    TextView output;
    EditText edit1;
    EditText edit2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temperature);
        TextView output = findViewById(R.id.output);
        output.setText("your BMI is");
        Log.i("11111", "onCreate: fg");
        Log.i(TAG, "onCreate: ");

        edit1 = findViewById(R.id.input1);
        edit2 = findViewById(R.id.input2);

        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(this);
        //this.onClick
    }


    @Override
    public void onClick(View view) {
        Log.i(TAG, "onClick: AAAAA");
        //用户输入数据
        EditText edit1 = findViewById(R.id.input1);
        EditText edit2 = findViewById(R.id.input2);
        String h = edit1.getText().toString();
        String w = edit2.getText().toString();
        double height = Double.parseDouble(h);
        double weight = Double.parseDouble(w);
        double B = weight / (height * height);
        BigDecimal b = new BigDecimal(B);
        double BIM = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        //显示输出到控件
        TextView output = findViewById(R.id.output);
        if((h!=null)&&(w!=null)){
            output.setText("your BMI is "+BIM);
        }
    }
}