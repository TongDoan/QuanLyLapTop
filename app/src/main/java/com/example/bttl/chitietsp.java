package com.example.bttl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class chitietsp extends AppCompatActivity {

    Toolbar toolbar;
    ImageView anhsp;
    TextView txttensp,txtgiasp,txtmotasp;
    Spinner spinner;
    Button btndatmua;

    int IdSanPham = 0;
    int id;
    String image;
    String ten;
    String gia;
    String mota;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menugiohang, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.idgiohang:
                Intent intent = new Intent(getApplicationContext(), giohangadapter.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsp);
        Anhxa();
        Actiontoolbar();
        Intent intent= getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null){
            id =bundle.getInt("id");
            image=bundle.getString("image");
            ten=bundle.getString("ten");
            gia=bundle.getString("gia");
            mota=bundle.getString("mota");
            txttensp.setText(ten);
            DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
            txtgiasp.setText(decimalFormat.format(Double.parseDouble(gia))+" VND");
            txtmotasp.setText(mota);
            Picasso.get().load(image).into(anhsp);
        }
        Evenspinner();
        EvenDatMua();
    }
    private void EvenDatMua(){
        btndatmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.giohangArrayList.size() > 0){
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean check = false;
                    for (int i = 0 ; i <MainActivity.giohangArrayList.size(); i++){
                        if(MainActivity.giohangArrayList.get(i).getIdsp() == id){
                            MainActivity.giohangArrayList.get(i).setSoluongsp(MainActivity.giohangArrayList.get(i).getSoluongsp() + sl);
                            if(MainActivity.giohangArrayList.get(i).getSoluongsp() >= 10){
                                MainActivity.giohangArrayList.get(i).setSoluongsp(10);
                            }
                            MainActivity.giohangArrayList.get(i).setGiasp(Integer.parseInt(gia)*MainActivity.giohangArrayList.get(i).getSoluongsp());
                            check = true;
                        }
                    }
                    if(check == false){
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long tongtien = soluong * Integer.parseInt(gia);
                        MainActivity.giohangArrayList.add(new giohang(id,ten,tongtien,image,soluong));
                    }
                }else{
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long tongtien = soluong * Integer.parseInt(gia);
                    MainActivity.giohangArrayList.add(new giohang(id,ten,tongtien,image,soluong));
                }
                Intent intent = new Intent(getApplicationContext(), giohangadapter.class);
                startActivity(intent);
            }
        });
    }
    private void Evenspinner(){
        Integer[] sl=new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter=new ArrayAdapter<Integer>(this, android.R.layout.simple_dropdown_item_1line,sl);
        spinner.setAdapter(arrayAdapter);
    }
    private void Actiontoolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void Anhxa(){
        toolbar=(Toolbar) findViewById(R.id.toobarchitietsp);
        anhsp=(ImageView) findViewById(R.id.anhchitietsp);
        txttensp=(TextView) findViewById(R.id.tenchitietsp);
        txtgiasp=(TextView) findViewById(R.id.giachitietsp);
        txtmotasp=(TextView) findViewById(R.id.motasp);
        spinner=(Spinner) findViewById(R.id.spinnersl);
        btndatmua=(Button)  findViewById(R.id.buttonthemgio);
    }
}