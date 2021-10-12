package com.swufe.stu.first;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.os.Handler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;


public class MyThread implements Runnable{
    private static final String TAG = "MyThread";
    private Handler handler;

    public void setHandler(Handler handler){
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        Log.i(TAG, "run: AAAA....");
        Bundle bundle = new Bundle();

        //获取网络数据
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
        /*
        Message msg = handler.obtainMessage(6, bundle);
        handler.sendMessage(msg);
        Log.i(TAG, "run: 消息已发送");
        
         */
        Message msg = handler.obtainMessage(6,bundle);
        handler.sendMessage(msg);
        Log.i(TAG, "run: 消息已发送");
    }
}
