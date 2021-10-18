package com.swufe.stu.first;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class rate_list extends AppCompatActivity {
    private static final String TAG = "RateListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_list);
        ListView listView = findViewById(R.id.mylist1);
        ProgressBar progressBar = findViewById(R.id.progressBar);

        String[] list_data = {"one","two","three","four"};
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list_data);
        listView.setAdapter(adapter);

        Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg){
                super.handleMessage(msg);
                Log.i(TAG, "handleMessage: ");
                if(msg.what==9){
                    ArrayList<String> rlist = (ArrayList<String>)msg.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(rate_list.this, android.R.layout.simple_list_item_1,rlist);
                    listView.setAdapter(adapter);
                    //调整控件显示状态
                    progressBar.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                }
            }
        };

        RateThread rt = new RateThread(handler);
        Thread t = new Thread(rt);
        t.start();

    }
}