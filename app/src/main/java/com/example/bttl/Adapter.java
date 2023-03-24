package com.example.bttl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {
    private ArrayList<Contact> data;
    private Activity context;
    private LayoutInflater inflater;
    private ArrayList<Contact> databackup;
    public Adapter(){}

    public Adapter(ArrayList<Contact> data, Activity activity) {
        this.data = data;
        this.context = activity;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if(v==null) v = inflater.inflate(R.layout.item_user,null);

        ImageView imgprofile = v.findViewById(R.id.img_user);
        ImageView img_phone = v.findViewById(R.id.img_phone);
        ImageView img_message = v.findViewById(R.id.img_message);



        TextView tvname = v.findViewById(R.id.tv_name);
        tvname.setText(data.get(i).getName());

        TextView tvphone = v.findViewById(R.id.tv_job);
        tvphone.setText(data.get(i).getJob());

        img_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCall = new Intent();
                intentCall.setAction(Intent.ACTION_DIAL);
                intentCall.setData(Uri.parse("tel:" + data.get(i).getPhone()));
                context.startActivity(intentCall);
            }
        });
        img_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSms = new Intent();
                intentSms.setAction(Intent.ACTION_SENDTO);
                intentSms.setData(Uri.parse("smsto:" + data.get(i).getPhone()));
                context.startActivity(intentSms);
            }
        });



        TextView name = v.findViewById(R.id.tv_name);
        TextView job = v.findViewById(R.id.tv_job);
        name.setText(data.get(i).getName());
        job.setText(data.get(i).getJob());
        return v;
    }
  
    public Filter getFilter() {
        Filter f = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults fr = new FilterResults();
                //Backup dữ liệu: lưu tạm data vào databackup
                if(databackup==null)
                    databackup = new ArrayList<>(data);
                //Nếu chuỗi để filter là rỗng thì khôi phục dữ liệu
                if(charSequence==null || charSequence.length()==0)
                {
                    fr.count = databackup.size();
                    fr.values = databackup;
                }
                //Còn nếu không rỗng thì thực hiện filter
                else{
                    ArrayList<Contact> newdata = new ArrayList<>();
                    for(Contact u:databackup)
                        if(u.getName().toLowerCase().contains(
                                charSequence.toString().toLowerCase()))
                            newdata.add(u);
                    fr.count=newdata.size();
                    fr.values=newdata;
                }
                return fr;
            }
            @Override
            protected void publishResults(CharSequence charSequence,
                                          FilterResults filterResults) {
                data = new ArrayList<Contact>();
                ArrayList<Contact> tmp =(ArrayList<Contact>)filterResults.values;
                for(Contact u: tmp)
                    data.add(u);
                notifyDataSetChanged();
            }
        };
        return f;
    }
}