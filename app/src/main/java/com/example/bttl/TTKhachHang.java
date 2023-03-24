package com.example.bttl;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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
import java.util.jar.JarException;

public class TTKhachHang extends AppCompatActivity {
    EditText tenkhachhang, sdt,sonha;
    Button btnxacnhan, btntrove;
    Spinner tp, phuong;
    String tkcu= "";
    String url = urlApi.addkhach;
    String urlchitiet = urlApi.addchitietdonhang;
    private void Evenspinner(){
        CharSequence[] sltp=new CharSequence[]{"Chọn thành phố","Hà Nội","Bắc Ninh","Bắc Giang"};
        ArrayAdapter<CharSequence> arrayAdapter=new ArrayAdapter<CharSequence>(this, android.R.layout.simple_dropdown_item_1line,sltp);
        tp.setAdapter(arrayAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ttkhach_hang);
        tenkhachhang = findViewById(R.id.editTextTenkh);
        sdt = findViewById(R.id.editTextsdt);
        tp = findViewById(R.id.spinnerTp);
        sonha = findViewById(R.id.editTextTextsonha);
        phuong = findViewById(R.id.spinnerPhuong);
        btnxacnhan = findViewById(R.id.btnxacnhantt);
        btntrove = findViewById(R.id.btntrove);
        Evenspinner();
        this.tp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(tp.getSelectedItem().toString() == "Hà Nội"){
                    CharSequence[] slp=new CharSequence[]{"Phường Cầu Diễn","Phường Xuân Phương","Phường Minh Khai","Phường Trương Định"};
                    ArrayAdapter<CharSequence> arrayAdapterP=new ArrayAdapter<CharSequence>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line,slp);
                    phuong.setAdapter(arrayAdapterP);
                }else if(tp.getSelectedItem().toString() == "Bắc Ninh"){
                    CharSequence[] slp=new CharSequence[]{"Phường Tiền An","Phường Vạn An","Phường Vệ An","Phường Vũ Ninh"};
                    ArrayAdapter<CharSequence> arrayAdapterP=new ArrayAdapter<CharSequence>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line,slp);
                    phuong.setAdapter(arrayAdapterP);
                }else if(tp.getSelectedItem().toString() == "Bắc Giang") {
                    CharSequence[] slp=new CharSequence[]{"Phường Đa Mai","Phường Dĩnh Kế","Phường Lê Lợi","Phường Ngô Quyền"};
                    ArrayAdapter<CharSequence> arrayAdapterP=new ArrayAdapter<CharSequence>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line,slp);
                    phuong.setAdapter(arrayAdapterP);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btntrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean connected = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);
        if(connected){
            btnxacnhan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String ten = tenkhachhang.getText().toString().trim();
                    String sdtt = sdt.getText().toString().trim();
                    String diachi =sonha.getText().toString().trim() + ","+phuong.getSelectedItem().toString().trim() + "," +  tp.getSelectedItem().toString().trim() ;
                    if(ten.length() > 0 && sdtt.length() > 0 && tp.getSelectedItem().toString().length() > 0 && phuong.getSelectedItem().toString().length() > 0 && sonha.length() > 0){
                        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                        StringRequest stringRequest =   new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String madonhang) {

                                if (Integer.parseInt(madonhang) > 0) {
                                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, urlchitiet, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            tkcu =  MainActivity.tk;
                                            if(response.equals("1")){
                                                MainActivity.giohangArrayList.clear();
                                                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                                                builder.setTitle("Thông báo");
                                                builder.setMessage("Xác nhận thành công!");
                                                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        MainActivity.tk = tkcu;
                                                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                                        startActivity(intent);
                                                    }
                                                });
                                                builder.show();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    }) {
                                        @Nullable
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            JSONArray jsonArray = new JSONArray();
                                            for (int i = 0; i < MainActivity.giohangArrayList.size(); i++) {
                                                JSONObject jsonObject = new JSONObject();
                                                try {
                                                    jsonObject.put("madonhang", madonhang);
                                                    jsonObject.put("masp",MainActivity.giohangArrayList.get(i).getIdsp());
                                                    jsonObject.put("tensp",MainActivity.giohangArrayList.get(i).getTensp());
                                                    jsonObject.put("giasp",String.valueOf(MainActivity.giohangArrayList.get(i).getGiasp()));
                                                    jsonObject.put("soluongsp",MainActivity.giohangArrayList.get(i).getSoluongsp());
                                                    jsonObject.put("hinhanh",MainActivity.giohangArrayList.get(i).getHinhsp());
                                                    jsonObject.put("taikhoan",MainActivity.tk);

                                                } catch (JSONException e) {
                                                    throw new RuntimeException(e);
                                                }
                                                jsonArray.put(jsonObject);
                                            }
                                            HashMap<String,String> hashMap = new HashMap<String,String>();
                                            hashMap.put("json",jsonArray.toString());
                                            return hashMap;
                                        }
                                    };
                                    queue.add(stringRequest);
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
                                HashMap<String,String> hashMap = new HashMap<String,String>();
                                hashMap.put("tenkh",ten);
                                hashMap.put("sodienthoai",sdtt);
                                hashMap.put("diachi",diachi);
                                return hashMap;
                            }
                        };
                    requestQueue.add(stringRequest);

                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                        builder.setTitle("Thông báo");
                        builder.setMessage("Vui lòng nhập kiểm tra lại thông tin");

                        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.show();
                    }
                }
            });
        }
    }

}