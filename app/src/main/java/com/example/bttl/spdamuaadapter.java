package com.example.bttl;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class spdamuaadapter extends BaseAdapter {
    private ArrayList<chitietspdamua> sp;
    private Activity context;
    private LayoutInflater inflater;
    private ArrayList<chitietspdamua> databackup;

    public spdamuaadapter(ArrayList<chitietspdamua> sp, Activity context) {
        this.sp = sp;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return sp.size();
    }

    @Override
    public Object getItem(int position) {
        return sp.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        View v = view;
        if (v == null) {
            v = inflater.inflate(R.layout.item_spdamua, null);

            ImageView img = v.findViewById(R.id.anhspdamua);
            Picasso.get().load(sp.get(i).getHinhanh()).into(img);
            TextView name = v.findViewById(R.id.txtenspdamua);
            TextView gia = v.findViewById(R.id.txtgiaspdamua);
            TextView sl = v.findViewById(R.id.txtslspdamua);
            name.setText(sp.get(i).getTensp());
            DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
            gia.setText(decimalFormat.format(Double.parseDouble(sp.get(i).getGiasp()))+" VND");
            sl.setText(String.valueOf("Số lượng: "+sp.get(i).getSoluong()));
        }
        return v;
    }

    public Filter getFilter() {
        Filter f = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults fr = new FilterResults();
                //Backup dữ liệu: lưu tạm data vào databackup
                if (databackup == null)
                    databackup = new ArrayList<>(sp);
                //Nếu chuỗi để filter là rỗng thì khôi phục dữ liệu
                if (charSequence == null || charSequence.length() == 0) {
                    fr.count = databackup.size();
                    fr.values = databackup;
                }
                //Còn nếu không rỗng thì thực hiện filter
                else {
                    ArrayList<chitietspdamua> newdata = new ArrayList<>();
                    for (chitietspdamua u : databackup)
                        if (u.getTensp().toLowerCase().contains(
                                charSequence.toString().toLowerCase()))
                            newdata.add(u);
                    fr.count = newdata.size();
                    fr.values = newdata;
                }
                return fr;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults filterResults) {
                sp = new ArrayList<chitietspdamua>();
                ArrayList<chitietspdamua> tmp = (ArrayList<chitietspdamua>) filterResults.values;
                for (chitietspdamua u : tmp)
                    sp.add(u);
                notifyDataSetChanged();
            }
        };
        return f;
    }
}
