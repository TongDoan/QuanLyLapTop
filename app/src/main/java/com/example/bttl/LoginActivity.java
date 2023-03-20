package com.example.bttl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText emailUser, passUser;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        setVariable();
    }

    private void setVariable(){
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailUser.getText().toString().isEmpty() && passUser.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please fill the form of login" , Toast.LENGTH_SHORT).show();
                }else if(emailUser.getText().toString().equals("root") && passUser.getText().toString().equals("123")){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("Name",emailUser.getText().toString());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                startActivity(new Intent(LoginActivity.this, MainActivity.class));

            }
        });
    }

    private void initView(){
        emailUser = findViewById(R.id.editTextPersonEmail);
        passUser = findViewById(R.id.editTextPersonPassword);
        loginBtn = findViewById(R.id.loginBtn);
    }
}