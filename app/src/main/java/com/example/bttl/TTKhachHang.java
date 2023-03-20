package com.example.bttl;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class TTKhachHang extends AppCompatActivity {
    EditText tenkhachhang, sdt,email;
    Button btnxacnhan, btntrove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ttkhach_hang);
        tenkhachhang = findViewById(R.id.editTextTenkh);
        sdt = findViewById(R.id.editTextTextsdt);
        email = findViewById(R.id.editTextTextemail);
        btnxacnhan = findViewById(R.id.btnxacnhantt);
        btntrove = findViewById(R.id.btntrove);
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
                    String emaill = email.getText().toString().trim();
                    if(ten.length() > 0 && sdtt.length() > 0 && emaill.length() > 0){
                        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                        StringRequest stringRequest =   new StringRequest(Request.Method.POST, "http://192.168.1.3:8080/appbanhang/ttkhachhang.php", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

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
                                hashMap.put("email",emaill);
                                return hashMap;
                            }
                        };
                    requestQueue.add(stringRequest);
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                        builder.setTitle("Thông báo");
                        builder.setMessage("Xác nhận thành công!");
                        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                    finish();
                            }
                        });
                        builder.show();
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