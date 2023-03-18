package com.example.bttl;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class giohangadapterBase extends BaseAdapter {
    Context context;
    ArrayList<giohang> arrayListgiohang;
    private LayoutInflater inflater;

    public giohangadapterBase(Context context, ArrayList<giohang> arrayListgiohang) {
        this.context = context;
        this.arrayListgiohang = arrayListgiohang;
        this.inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayListgiohang.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListgiohang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v==null) {
            v = inflater.inflate(R.layout.dong_hang, null);
        }
        TextView textView = v.findViewById(R.id.tenspgh);
        TextView giahang = v.findViewById(R.id.giagh);
        ImageView imgage = v.findViewById((R.id.imagegiohang));
        Button btngiam = v.findViewById(R.id.btgiamsp);
        Button btntang = v.findViewById(R.id.btntangsp);
        Button btntvale = v.findViewById(R.id.btnvalue);
        Button btnxoa = v.findViewById(R.id.btndelete);
        textView.setText(arrayListgiohang.get(position).getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        giahang.setText(decimalFormat.format(arrayListgiohang.get(position).getGiasp()) + "Đ");
        Picasso.get().load(arrayListgiohang.get(position).getHinhsp()).into(imgage);
        btntvale.setText(arrayListgiohang.get(position).getSoluongsp() + "");
        int sl = Integer.parseInt(btntvale.getText().toString());
        if(sl >= 10){
            btntang.setVisibility(View.INVISIBLE);
            btngiam.setVisibility(View.VISIBLE);
        }else if(sl <=1){
            btngiam.setVisibility(View.INVISIBLE);
        }else{
            btntang.setVisibility(View.VISIBLE);
            btngiam.setVisibility(View.VISIBLE);
        }
        btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có muốn xóa sản phẩm nảy");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(MainActivity.giohangArrayList.size() <= 0){
                            giohangadapter.txtthongbao.setVisibility(View.VISIBLE);
                        }else{
                            MainActivity.giohangArrayList.remove(position);
                            giohangadapter.giohangadapterB.notifyDataSetChanged();
                            giohangadapter.EvenUltil();
                            if(MainActivity.giohangArrayList.size() <= 0){
                                giohangadapter.txtthongbao.setVisibility(View.VISIBLE);
                            }else{
                                giohangadapter.txtthongbao.setVisibility(View.INVISIBLE);
                                giohangadapter.giohangadapterB.notifyDataSetChanged();
                                giohangadapter.EvenUltil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        giohangadapter.giohangadapterB.notifyDataSetChanged();
                        giohangadapter.EvenUltil();
                    }
                });
                builder.show();
            }
        });
        btntang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sl = Integer.parseInt(btntvale.getText().toString()) + 1;
                long slht = MainActivity.giohangArrayList.get(position).getSoluongsp();
                long giaht = MainActivity.giohangArrayList.get(position).getGiasp();
                MainActivity.giohangArrayList.get(position).setSoluongsp(sl);
                long giamoi = (giaht * sl) / slht;
                MainActivity.giohangArrayList.get(position).setGiasp(giamoi);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                giahang.setText(decimalFormat.format(giamoi) + "Đ");
                giohangadapter.EvenUltil();
                if(sl > 9){
                    btntang.setVisibility(View.INVISIBLE);
                    btngiam.setVisibility(View.VISIBLE);
                    btntvale.setText(String.valueOf(sl));
                }else{
                    btntang.setVisibility(View.VISIBLE);
                    btngiam.setVisibility(View.VISIBLE);
                    btntvale.setText(String.valueOf(sl));
                }
            }
        });

        btngiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sl = Integer.parseInt(btntvale.getText().toString()) - 1;
                long slht = MainActivity.giohangArrayList.get(position).getSoluongsp();
                long giaht = MainActivity.giohangArrayList.get(position).getGiasp();
                MainActivity.giohangArrayList.get(position).setSoluongsp(sl);
                long giamoi = (giaht * sl) / slht;
                MainActivity.giohangArrayList.get(position).setGiasp(giamoi);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                giahang.setText(decimalFormat.format(giamoi) + "Đ");
                giohangadapter.EvenUltil();
                if(sl < 2){
                    btngiam.setVisibility(View.INVISIBLE);
                    btntang.setVisibility(View.VISIBLE);
                    btntvale.setText(String.valueOf(sl));
                }else{
                    btntang.setVisibility(View.VISIBLE);
                    btngiam.setVisibility(View.VISIBLE);
                    btntvale.setText(String.valueOf(sl));
                }
            }
        });
        return v;
    }
}
