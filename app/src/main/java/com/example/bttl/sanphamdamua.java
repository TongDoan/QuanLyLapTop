package com.example.bttl;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class sanphamdamua extends AppCompatActivity {
    Toolbar toolbar;
    ListView lst;
    ArrayList<chitietspdamua> sp;
    spdamuaadapter adapter;
    EditText etsearch;
    String url= urlApi.spdamua;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanphamdamua);
        Anhxa();
        Actiontoolbar();
        getsp(url);
    }
    private void getsp(String url){

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest jsonArrayRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int Id=0;
                        String tensp="";
                        String giasp="";
                        String hinhanh="";
                        int sl=0;
                        if(response !=null){
                            try {
                                JSONArray jsonArray=new JSONArray(response);
                                for(int i=0;i<jsonArray.length();i++) {
                                    JSONObject object =jsonArray.getJSONObject(i);
                                    Id=object.getInt("Id");
                                    tensp=object.getString("Tensp");
                                    giasp=object.getString("Giasp");
                                    sl=object.getInt("Soluongsp");
                                    hinhanh=object.getString("Hinhanh");
                                    sp.add(new chitietspdamua(hinhanh,tensp,giasp,sl));
                                    adapter.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
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
                param.put("taikhoan",MainActivity.tk);
                return param;
            }
        };
        requestQueue.add(jsonArrayRequest);
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
        toolbar=(Toolbar) findViewById(R.id.toolspdamua);
        etsearch=(EditText) findViewById(R.id.txtspdamua);
        lst= (ListView) findViewById(R.id.lstspdamua);
        sp=new ArrayList<>();
        adapter=new spdamuaadapter(sp,this);
        lst.setAdapter(adapter);
        etsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
                adapter.notifyDataSetChanged();
                lst.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}