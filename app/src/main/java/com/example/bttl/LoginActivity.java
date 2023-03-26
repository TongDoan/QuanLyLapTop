package com.example.bttl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private EditText emailUser, passUser;
    private Button loginBtn,register;
    public static ArrayList<user> userArrayList;
    String url=urlApi.user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setVariable();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getuser();
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });
        getuser();
    }

    private void checkaccount(){
        int ktr=0;
        for(user a:userArrayList){
            if(a.getEmail().trim().equals(emailUser.getText().toString().trim()) &&
               a.getMatkhat().trim().equals(passUser.getText().toString().trim())){
                ktr++;
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Name",emailUser.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            }

        }
        if(ktr == 0){
            Toast.makeText(this, "Tài khoản hoặc mật khẩu không chính xác.", Toast.LENGTH_SHORT).show();
        }


    }
    private void setVariable(){
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkaccount();
            }
        });
    }

    private void getuser(){
        userArrayList.clear();
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response !=null){
                            int Id=0;
                            String email="";
                            String matkhau="";
                            String hinhanh="";

                            for(int i=0;i<response.length();i++){
                                try {

                                    JSONObject object =response.getJSONObject(i);
                                    Id=object.getInt("Id");
                                    email=object.getString("email");
                                    matkhau=object.getString("matkhau");
                                    hinhanh=object.getString("anhdaidien");
                                    userArrayList.add(new user(Id,email,matkhau,hinhanh));
                                } catch (JSONException e) {
                                    Toast.makeText(getApplicationContext(),"fail" , Toast.LENGTH_LONG).show();
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(jsonArrayRequest);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startActivity(startMain);
        finish();
        super.onBackPressed();
    }
    private void initView(){
        emailUser = findViewById(R.id.editTextPersonEmail);
        passUser = findViewById(R.id.editTextPersonPassword);
        loginBtn = findViewById(R.id.loginBtn);
        register = findViewById(R.id.register);
        userArrayList = new ArrayList<>();
    }
}
