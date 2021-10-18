package com.swufe.stu.first;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class rate_list_activity2 extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "rate_list_activity2";
    private ListView list2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_list2);
        list2 = findViewById(R.id.mylist2);
        list2.setOnItemClickListener(this);

        //准备数据
        ArrayList<HashMap<String,String>> listItems = new ArrayList<HashMap<String,String>>();
        for(int i = 0;i < 10;i++){
            HashMap<String,String> map = new HashMap<String,String>();

            map.put("ItemTitle","Rate:" + i);//标题文字
            map.put("ItemDetail","detail" + i);//详情描述
            listItems.add(map);
        }

        //生成适配器的Item和动态数组对应的元素
        SimpleAdapter listItemAdapter = new SimpleAdapter(this,
                listItems,//listItems数据源
                R.layout.list_item,//listItem的XML布局实现
                new String[]{"ItemTitle","ItemDetail"},
                new int[]{R.id.itemTitle,R.id.itemDetail}
                );
        list2.setAdapter(listItemAdapter);

        Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg){
                super.handleMessage(msg);
                Log.i(TAG, "handleMessage: ");
                if(msg.what==9){
                    ArrayList<HashMap<String,String>> rlist = (ArrayList<HashMap<String,String>>)msg.obj;
                    /*
                    SimpleAdapter listItemAdapter = new SimpleAdapter(rate_list_activity2.this,
                            rlist,
                            R.layout.list_item,
                            new String[] {"ItemTitle","ItemDetail"},
                            new int[] {R.id.itemTitle,R.id.itemDetail}
                            );
                     */
                    MyAdapter myAdapter= new MyAdapter(rate_list_activity2.this,R.layout.list_item,rlist);
                    list2.setAdapter(myAdapter);
                }
            }
        };

        RateMapThread rt = new RateMapThread(handler);
        Thread t = new Thread(rt);
        t.start();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Object itemAtPosition = list2.getItemAtPosition(position);
        HashMap<String,String> map = (HashMap<String, String>) itemAtPosition;
        String titleStr = map.get("ItemTitle");
        String detailStr = map.get("ItemDetail");
        Log.i(TAG, "onItemClick: titleStr=" + titleStr);
        Log.i(TAG, "onItemClick: detailStr=" + detailStr);

        TextView title = (TextView) view.findViewById(R.id.itemTitle);
        TextView detail = (TextView) view.findViewById(R.id.itemDetail);
        String title2 = String.valueOf(title.getText());
        String detail2 = String.valueOf(detail.getText());
        Log.i(TAG, "onItemClick: title2=" + title2);
        Log.i(TAG, "onItemClick: detail2=" + detail2);

        //打开新的页面上传参数
        Intent rateCalc = new Intent(this,RateCalcActivity.class);
        rateCalc.putExtra("title",titleStr);
        rateCalc.putExtra("rate",Float.parseFloat(detailStr));
        startActivity(rateCalc);
    }
}