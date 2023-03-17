package com.example.bttl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsp);
        Anhxa();
        Actiontoolbar();
        Intent intent= getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null){
            int id =bundle.getInt("id");
            String image=bundle.getString("image");
            String ten=bundle.getString("ten");
            String gia=bundle.getString("gia");
            String mota=bundle.getString("mota");
            txttensp.setText(ten);
            DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
            txtgiasp.setText(decimalFormat.format(Double.parseDouble(gia))+" VND");
            txtmotasp.setText(mota);
            Picasso.get().load(image).into(anhsp);
        }
        Evenspinner();
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