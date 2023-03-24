package com.example.bttl;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class taikhoan extends AppCompatActivity {
    ImageView avata;
    TextView taikhoan,matkhau;
    Button btnsua;
    String url= urlApi.usercansua;
    String urlsua=urlApi.capnhatuser;
    String anhava="";
    String taikhoanuser="";
    String matkhauuser="";
    Toolbar toll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taikhoan);
        Anhxa();
        getdata(url);
        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),suauser.class);
                Bundle b=new Bundle();
                b.putString("image",anhava);
                b.putString("name",taikhoanuser);
                b.putString("phone",matkhau.getText().toString());
                intent.putExtras(b);
                startActivityForResult(intent,100);
            }
        });
        Actiontoolbar();
    }
    private void Actiontoolbar(){
        setSupportActionBar(toll);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toll.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getdata(String url){
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id=0;
                if(response!=null){
                    try {
                        JSONArray jsonArray=new JSONArray(response);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            id=jsonObject.getInt("Id");
                            taikhoanuser=jsonObject.getString("Email");
                            matkhauuser=jsonObject.getString("Matkhau");
                        }
                        taikhoan.setText(taikhoanuser);
                        matkhau.setText(matkhauuser);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param=new HashMap<String,String>();
                param.put("emailuser",MainActivity.tk);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle b= data.getExtras();
        String phone= b.getString("phone");
        String image=b.getString("image");
        if(requestCode==100 && resultCode==150){
            //truong hop sua
            if(image.isEmpty()){
                avata.setImageResource(R.drawable.user);
            }
            else{
                avata.setImageURI(Uri.parse(image));
            }
            Toast.makeText(this, phone, Toast.LENGTH_SHORT).show();
            Suatt(urlsua,MainActivity.tk,phone);
            matkhau.setText(phone);
        }



    }

    private void Suatt(String url, String email, String matkhau){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success"))
                        {

                            Toast.makeText(getApplicationContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Lỗi sửa", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Xảy ra lỗi", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<>();
                param.put("emailuservt",email);
                param.put("mkmoi",matkhau);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void Anhxa(){
        avata=(ImageView) findViewById(R.id.anhavata);
        taikhoan=(TextView) findViewById(R.id.txtenuser);
        matkhau=(TextView) findViewById(R.id.txtmatkhauuser);
        btnsua=(Button) findViewById(R.id.nutsuatt);
        toll = findViewById(R.id.toolbar);

    }
}