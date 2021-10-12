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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class huilv extends AppCompatActivity implements Runnable {
    private static final String TAG = "huilv";
    EditText edit;
    Date start ;
    Date end ;
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
        SharedPreferences sharedPreference = getSharedPreferences("time", Activity.MODE_PRIVATE);
        dollarrate = sharedPreferences.getFloat("dollar_rate",0.0f);
        eurorate = sharedPreferences.getFloat("euro_rate",0.0f);
        wonrate = sharedPreferences.getFloat("won_rate",0.0f);
        Log.i(TAG, "onCreate: "+dollarrate);
        Log.i(TAG, "onCreate: "+eurorate);
        Log.i(TAG, "onCreate: "+wonrate);



        String dateString;
        dateString = sharedPreference.getString("dateString","");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Log.i(TAG,"dataString:"+dateString);
        if(dateString.equals("")){//第一次运行
            // HH:mm:ss
            //获取当前时间
            start = new Date(System.currentTimeMillis());//获取当前时间
            dateString = simpleDateFormat.format(start);//转为String
            SharedPreferences.Editor editor=sharedPreference.edit();
            editor.putString("dateString",dateString);
            editor.apply();
        }else{
            try {
                dateString = sharedPreference.getString("dateString", null);
                start =simpleDateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        //  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        //start =new Date(System.currentTimeMillis()) ;




        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.i(TAG, "handleMessage: 收到消息");
                if(msg.what==6){
                    /*
                    String str = (String) msg.obj;
                    Log.i(TAG, "handleMessage: 收到消息"+str);
                     */
                    Bundle bdl = (Bundle)msg.obj;
                    dollarrate = bdl.getFloat("r1");
                    eurorate = bdl.getFloat("r2");
                    wonrate = bdl.getFloat("r3");

                    Log.i(TAG, "handleMessage: dollarrate=" + dollarrate);
                    Log.i(TAG, "handleMessage: eurorate=" + eurorate);
                    Log.i(TAG, "handleMessage: wonrate=" + wonrate);

                    //提示
                    Toast.makeText(huilv.this,"数据已更新",Toast.LENGTH_SHORT).show();
                }
                super.handleMessage(msg);
            }
        };
        //开启线程
        /*
        Thread t = new Thread(this);
        t.start();

         */

        //Thread td = new MyThread();
        //td.setHandler(handler);
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

             /*           //保存文件名，模式      得到参数
        SharedPreferences sharedPreferences=getSharedPreferences("change_rate", Activity.MODE_PRIVATE);
        PreferenceManager.getDefaultSharedPreferences(this);//权限设定
        //获取数据
        D_rate= sharedPreferences.getFloat("D_rate",0.0f);
        E_rate= sharedPreferences.getFloat("E_rate",0.0f);
        W_rate= sharedPreferences.getFloat("W_rate",0.0f);*/
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
        /*
        Message msg = handler.obtainMessage();
        msg.what = 6;
        msg.obj = "hello from run";
        handler.sendMessage(msg);
         */
        Log.i(TAG, "run: 消息已发送");

        Log.i(TAG, "onEnd:fffffffffffffffffffff " );
        //发送消息
        while (true) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
            end = new Date(System.currentTimeMillis()); //获取当前时间
            long cha = end.getTime() - start.getTime();
            double result = cha * 1.0 / (1000 * 60 * 60);
            if (result > 24) {
                SharedPreferences sp = getSharedPreferences("time", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.apply();
                editor.putString("dataString", simpleDateFormat.format(end));
                Log.i(TAG, "onEnd: " + simpleDateFormat.format(end));
                /*  Thread.sleep(10000);*/

                //获取网络数据
                Bundle bundle = new Bundle();
                URL url = null;
                try {
                    /**
                     url = new URL("https://www.usd-cny.com/bankofchina.htm");
                     HttpURLConnection http = (HttpURLConnection) url.openConnection();
                     InputStream in = http.getInputStream();

                     String html = inputStream2String(in);
                     Log.i(TAG, "run: html=" + html);
                     **/
                    Document doc = Jsoup.connect("https://www.usd-cny.com/bankofchina.htm").get();
                    Log.i(TAG, "run: title= " + doc.title());

                    Elements h4s = doc.getElementsByTag("h4");
                    for (Element h4 : h4s) {
                        Log.i(TAG, "run: h4=" + h4.text());
                    }
                    Elements tables = doc.getElementsByTag("table");

                    Element table1 = tables.first();
                    //Log.i(TAG, "run: table=" + table1);
                    Elements trs = table1.getElementsByTag("tr");
                    trs.remove(0);
                    for (Element tr : trs) {
                        Elements tds = tr.getElementsByTag("td");
                        String cname = tds.get(0).text();
                        String cval = tds.get(5).text();
                        //Log.i(TAG, "run: tr=" + tr);
                        if ("美元".equals(cname)) {
                            bundle.putFloat("r1", 100f / Float.parseFloat(cval));
                            Log.i(TAG, "run: dollarrate=" + cval);
                        } else if ("欧元".equals(cname)) {
                            bundle.putFloat("r2", 100f / Float.parseFloat(cval));
                            Log.i(TAG, "run: eurorate=" + cval);
                        } else if ("韩元".equals(cname)) {
                            bundle.putFloat("r3", 100f / Float.parseFloat(cval));
                            Log.i(TAG, "run: wonrate=" + cval);
                        }
                    }
                    /**
                     for(Element t:tables){
                     Elements hrefs=t.getElementsByTag("a");
                     Elements types=t.getElementsByTag("td");
                     Element rate=types.last();
                     for(int i=0;i<27;i++){
                     Log.i(TAG, "run: hr=" + trs.get(i));
                     }
                     Log.i(TAG, "run: 下一行");
                     Log.i(TAG, "run: a=" + hrefs.text());
                     Log.i(TAG, "run: td=" + types.text());
                     Log.i(TAG, "run: rate=" + rate.text());
                     }
                     **/


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

        /*
        Message msg = handler.obtainMessage();
        msg.what = 6;
        msg.obj = "Hello from run";
         */
                Message msg = handler.obtainMessage(6, bundle);
                handler.sendMessage(msg);
                Log.i(TAG, "run: 消息已发送");
            }
        }
        }

    /*
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

     */

}