package com.example.bttl;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.DecimalFormat;


public class giohangadapter extends AppCompatActivity {
    ListView lvgiohang;
    static TextView txtthongbao;
    static TextView txttongtien;
    Button btnthanhtoan, btnttmua;
    Toolbar toolbargiohang;
    static giohangadapterBase giohangadapterB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohangadapter);
        lvgiohang = findViewById(R.id.listviewgiohang);
        txtthongbao = findViewById(R.id.thongbaogiohang);
        txttongtien = findViewById(R.id.txttongtien);
        btnthanhtoan = findViewById(R.id.btnttgiohang);
        btnttmua = findViewById(R.id.btnttmuahang);
        toolbargiohang = findViewById(R.id.toolbargiohang);
        giohangadapterB = new giohangadapterBase(giohangadapter.this,MainActivity.giohangArrayList);
        lvgiohang.setAdapter(giohangadapterB);
        Actiontoolbar();
        CheckData();
        EvenUltil();
    }

    public static void EvenUltil(){
        long tongtien = 0;
        for (int i = 0; i< MainActivity.giohangArrayList.size(); i++){
            tongtien += MainActivity.giohangArrayList.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txttongtien.setText(decimalFormat.format(tongtien) + "Đ");
    }
    private void CheckData(){
        if(MainActivity.giohangArrayList.size() <=0){
            giohangadapterB.notifyDataSetChanged();
            txtthongbao.setVisibility(View.VISIBLE);
            lvgiohang.setVisibility(View.INVISIBLE);
        }else{
            giohangadapterB.notifyDataSetChanged();
            txtthongbao.setVisibility(View.INVISIBLE);
            lvgiohang.setVisibility(View.VISIBLE);
        }
    }
    private void Actiontoolbar(){
        setSupportActionBar(toolbargiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbargiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void ActoinToolbar(){
        Toolbar toolbargiohag = null;
        setSupportActionBar(toolbargiohag);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
    }
    
}