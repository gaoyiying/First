package com.swufe.stu.first;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RateMapThread implements Runnable{
    private static final String TAG = "RateThread";
    private Handler handler;

    public RateMapThread(Handler handler) {
        this.handler = handler;
    }


    public void setHandler(Handler handler){
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(300);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        Log.i(TAG, "run: AAAA....");
        Bundle bundle = new Bundle();

        List<HashMap<String, String>> retlist = new ArrayList<>();

        //获取网络数据
        try {
            /**
             url = new URL("https://www.usd-cny.com/bankofchina.htm");
             HttpURLConnection http = (HttpURLConnection) url.openConnection();
             InputStream in = http.getInputStream();

             String html = inputStream2String(in);
             Log.i(TAG, "run: html=" + html);
             **/
            Document doc = Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get();
            Log.i(TAG, "run: title= " + doc.title());

            Elements h4s = doc.getElementsByTag("h4");
            for (Element h4 : h4s) {
                Log.i(TAG, "run: h4=" + h4.text());
            }
            Elements tables = doc.getElementsByTag("table");
            tables.remove(0);
            Element table1 = tables.first();
            Log.i(TAG, "run: table=" + table1);
            Elements trs = table1.getElementsByTag("tr");
            trs.remove(0);
            for (Element tr : trs) {
                Elements tds = tr.getElementsByTag("td");
                String cname = tds.get(0).text();
                String cval = tds.get(5).text();
                //Log.i(TAG, "run: tr=" + tr);
                Log.i(TAG, "run: cname=" + cname + "-->" + cval);
                HashMap<String,String> map = new HashMap<String,String>();
                map.put("ItemTitle",cname);
                map.put("ItemDetail",cval);
                retlist.add(map);
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
        Message msg = handler.obtainMessage(9,retlist);
        handler.sendMessage(msg);
        Log.i(TAG, "run: 消息已发送");
    }
}
