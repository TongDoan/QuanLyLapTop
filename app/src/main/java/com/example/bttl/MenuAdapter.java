package com.example.bttl;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MenuAdapter extends BaseAdapter {
    private Activity context;
    private ArrayList<menuitem> arrayList;
    private LayoutInflater inflater;

    public MenuAdapter(Activity context, ArrayList<menuitem> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View v=  view;
        if(v==null) {
            v = inflater.inflate(R.layout.dong_menu, null);
        }
        ImageView image=(ImageView) v.findViewById(R.id.imageView);
        image.setImageResource(arrayList.get(position).getImage());
        TextView text=(TextView) v.findViewById(R.id.textView);
        text.setText(arrayList.get(position).getTenitem());
        return v;

    }
}
