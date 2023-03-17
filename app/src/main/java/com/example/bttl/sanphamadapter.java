package com.example.bttl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class sanphamadapter extends RecyclerView.Adapter<sanphamadapter.ItemHolder>{
    Context context;
    ArrayList<sanpham> lstsanpham;

    public sanphamadapter(Context context, ArrayList<sanpham> lstsanpham) {
        this.context = context;
        this.lstsanpham = lstsanpham;
    }

    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanpham,null);
        ItemHolder itemHolder= new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
               sanpham sp= lstsanpham.get(position);
               holder.txttensp.setText(sp.tensp);
               DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
               holder.txtgiasp.setText(decimalFormat.format(Double.parseDouble(sp.getGiasp()))+" VND");
        Picasso.get().load(sp.getHinhanh()).into(holder.anhsp);
           holder.setItemClickListener(new ItemClickListener() {
               @Override
               public void onClick(View view, int position, boolean isLongClick) {
                   if(!isLongClick){
                       Intent intent=new Intent(context,chitietsp.class);
                       Bundle b=new Bundle();
                       b.putInt("id",sp.getId());
                       b.putString("image",sp.getHinhanh());
                       b.putString("gia",sp.getGiasp());
                       b.putString("ten",sp.getTensp());
                       b.putString("mota",sp.getMota());
                       intent.putExtras(b);
                       context.startActivity(intent);
                   }
               }
           });
    }

    @Override
    public int getItemCount() {
        return lstsanpham.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView anhsp;
        public TextView txttensp,txtgiasp;
        private ItemClickListener itemClickListener;
        public ItemHolder(View view){
            super(view);
            anhsp= (ImageView) view.findViewById(R.id.anhsp);
            txttensp=(TextView) view.findViewById(R.id.tensp);
            txtgiasp=(TextView) view.findViewById(R.id.giasp);
            view.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);
        }

    }
}
