package com.swufe.stu.first;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class huilv extends AppCompatActivity implements Runnable {
    private static final String TAG = "huilv";
    EditText edit;
    float dollarrate = 0.1548f;
    float eurorate = 0.1323f;
    float wonrate = 182.5773f;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huilv);
        TextView output = findViewById(R.id.output);
        Log.i("11111", "onCreate: fg");
        edit = findViewById(R.id.input);

        //读取保存的数据
        SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        PreferenceManager.getDefaultSharedPreferences(this);
        dollarrate = sharedPreferences.getFloat("dollar_rate",0.0f);
        eurorate = sharedPreferences.getFloat("euro_rate",0.0f);
        wonrate = sharedPreferences.getFloat("won_rate",0.0f);
        Log.i(TAG, "onCreate: "+dollarrate);
        Log.i(TAG, "onCreate: "+eurorate);
        Log.i(TAG, "onCreate: "+wonrate);



        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.i(TAG, "handleMessage: 收到消息");
                if(msg.what==6){
                    String str = (String) msg.obj;
                    Log.i(TAG, "handleMessage: 收到消息"+str);
                }
                super.handleMessage(msg);
            }
        };
        //开启线程
        Thread t = new Thread(this);
        t.start();
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

        //保存数据到sp
        SharedPreferences sp = getSharedPreferences("myrate",Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat("dollar_rate",dollarrate);
        editor.putFloat("euro_rate",eurorate);
        editor.putFloat("won_rate",wonrate);
        editor.apply();

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

    public void run(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "run: ....");
        //发送消息给主线程
        Message msg = handler.obtainMessage();
        msg.what = 6;
        msg.obj = "hello from run";
        handler.sendMessage(msg);
        Log.i(TAG, "run: 消息已发送");

        //获取网络数据
        URL url = null;
        try {
            url = new URL("https://www.usd-cny.com/bankofchina.htm");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            InputStream in = http.getInputStream();

            String html = inputStream2String(in);
            Log.i(TAG, "run: html=" + html);

        }catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private  String inputStream2String(InputStream inputStream)
            throws IOException{
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream,"gb2312");
        while (true){
            int rsz = in.read(buffer,0,buffer.length);
            if(rsz<0){
                break;
            }
            out.append(buffer,0,rsz);
        }
        return out.toString();
    }

}