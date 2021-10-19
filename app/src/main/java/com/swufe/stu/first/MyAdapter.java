package com.swufe.stu.first;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAdapter extends ArrayAdapter {
    private static final String TAG = "MyAdapter";

    public MyAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Item> data) {
        super(context, resource, data);
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent){
        View itemView = convertView;
        if(itemView == null){
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        //Map<String,String> map = (Map<String, String>) getItem(position);
        Item item = (Item) getItem(position);
        TextView title = (TextView) itemView.findViewById(R.id.itemTitle);
        TextView detail = (TextView) itemView.findViewById(R.id.itemDetail);

        /*
        title.setText(map.get("ItemTitle"));
        detail.setText(map.get("ItemDetail"));

         */
        title.setText(item.getCname());
        detail.setText(item.getCval());
        return itemView;
    }
}
