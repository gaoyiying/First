package com.swufe.stu.first;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

public class huilv extends AppCompatActivity  {
    private static final String TAG = "huilv";
    EditText edit;
    float dollarrate = 0.1548f;
    float eurorate = 0.1323f;
    float wonrate = 182.5773f;

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
        if(number.length()>0){
            float n = Float.parseFloat(number);
            float nn = 0;
            if(btn.getId()==R.id.DOLLAR){
                nn = n * 0.1548f;
            }else if(btn.getId()==R.id.EURO){
                nn = n * 0.1323f;
            }else if(btn.getId()==R.id.WON){
                nn = n * 182.5773f;
            }
            TextView output = findViewById(R.id.output);
            output.setText(String.valueOf(nn));
        }else{
            Toast.makeText(this, "请输入数据", Toast.LENGTH_SHORT).show();
        }
    }
    public void openOne(View btn){
        config();
    }

    private void config() {
        Intent config = new Intent(this, huilv2.class);
        config.putExtra("dollar_rate_key", dollarrate);
        config.putExtra("euro_rate_key", eurorate);
        config.putExtra("won_rate_key", wonrate);

        Log.i(TAG, "onActivityResult: dollarrate=" + dollarrate);
        Log.i(TAG, "onActivityResult: eurorate=" + eurorate);
        Log.i(TAG, "onActivityResult: wonrate=" + wonrate);

        //startActivity(config);
        startActivityForResult(config, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 && resultCode==3){
            dollarrate = data.getFloatExtra("dollar_key",0.1f);
            eurorate = data.getFloatExtra("euro_key",0.1f);
            wonrate = data.getFloatExtra("won_key",0.1f);

            /*
            Bundle bundle = data.getExtras();
            dollarrate = bundle.getFloat("key_dollar",0.1f);
            eurorate = bundle.getFloat("key_eura",0.1f);
            wonrate = bundle.getFloat("key_won",0.1f);
            Log.i(TAG, "onActivityResult: dollarrate=" + dollarrate);
            Log.i(TAG, "onActivityResult: eurorate=" + eurorate);
            Log.i(TAG, "onActivityResult: wonrate=" + wonrate);
             */
        }

        Log.i(TAG, "onActivityResult: dollarrate=" + dollarrate);
        Log.i(TAG, "onActivityResult: eurorate=" + eurorate);
        Log.i(TAG, "onActivityResult: wonrate=" + wonrate);

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rate,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_set){
            config();
        }
        return super.onOptionsItemSelected(item);
    }
}