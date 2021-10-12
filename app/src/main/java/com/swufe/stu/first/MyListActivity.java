package com.swufe.stu.first;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyListActivity extends ListActivity{

    private static final String TAG = "mylistactivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        List<String> list1 = new ArrayList<String>();
        for (int i = 1;i<100;i++){
            list1.add("item" + i);
        }
        String[] list_data = {"one","two","three","four"};
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list_data);
        setListAdapter(adapter);


        Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg){
                super.handleMessage(msg);
                Log.i(TAG, "handleMessage: ");
                if(msg.what==9){
                    ArrayList<String> rlist = (ArrayList<String>)msg.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(MyListActivity.this, android.R.layout.simple_list_item_1,rlist);
                    setListAdapter(adapter);
                }
            }
        };

        RateThread rt = new RateThread(handler);
        Thread t = new Thread(rt);
        t.start();

    }


}
