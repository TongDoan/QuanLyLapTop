package com.example.bttl;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class RegisterActivity extends AppCompatActivity {
    Button btndk,btnql;
    EditText edtemail,edtpass,edtrepass;

    String url= urlApi.adduser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btndk = findViewById(R.id.btndangky);
        btnql = findViewById(R.id.btnquaylai);
        edtemail = findViewById(R.id.editTextPersonEmail);
        edtpass = findViewById(R.id.editTextPersonPassword);
        edtrepass = findViewById(R.id.editTextPersonRePassword);
        btnql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btndk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = 0;
                for(user a:LoginActivity.userArrayList){
                    if(a.getEmail().trim().equals(edtemail.getText().toString().trim())){
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                        builder.setTitle("Thông báo");
                        builder.setMessage("Email đã được đăng ký! Vui lòng nhập email khác.");

                        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        builder.show();
                        check++;
                    }
                }
                if(edtemail.getText().toString().isEmpty() || edtpass.getText().toString().isEmpty() || edtrepass.getText().toString().isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Vui lòng kiểm tra lại thông tin đăng ký.", Toast.LENGTH_SHORT).show();
                }
                if(edtpass.getText().toString().trim().equals(edtrepass.getText().toString().trim()) == false){
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                    builder.setTitle("Thông báo");
                    builder.setMessage("Mật khẩu không trùng nhau.");

                    builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                }else {
                    if(check == 0){
                        String email = edtemail.getText().toString().trim();
                        String matkhau = edtpass.getText().toString().trim();
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("1")) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                                    builder.setTitle("Thông báo");
                                    builder.setMessage("Đăng ký thành công! Vui lòng đăng nhập.");
                                    builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
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
                                HashMap<String,String> hashMap = new HashMap<String,String>();
                                hashMap.put("email",email);
                                hashMap.put("matkhau",matkhau);
                                hashMap.put("anhdaidien","");
                                return hashMap;
                            }
                        };
                        queue.add(stringRequest);


                    }
                }

            }
        });
    }
}