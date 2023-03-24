package com.example.bttl;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class suauser extends AppCompatActivity {
    TextView tkbd;
    EditText mksau,xacnhanmksua;
    ImageView anhsua;
    Button xacnhan,quaylai;
    String picturePath="";
    String mk="";
    String pic="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suathongtinkhach);
        tkbd=(TextView) findViewById(R.id.tenusersua);
        mksau=(EditText) findViewById(R.id.matkhausua);
        xacnhanmksua=(EditText) findViewById(R.id.xacnhanmatkhausua);
        anhsua=(ImageView) findViewById(R.id.anhavatasua);
        xacnhan=(Button) findViewById(R.id.nutxacnhansua);
        quaylai=(Button) findViewById(R.id.nutquaylaitrangtruoc);

        Intent intent= getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null){
            String image=bundle.getString("image");
            String name=bundle.getString("name");
            String phone=bundle.getString("phone");
            mk=phone;
            tkbd.setText(name);
            mksau.setText(phone);
            xacnhanmksua.setText(phone);
            if(picturePath.isEmpty()){
                anhsua.setImageResource(R.drawable.user);
            }
            else{
                anhsua.setImageURI(Uri.parse(picturePath));
            }
        }
        anhsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,1);
            }
        });
        xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mksau.getText().toString().isEmpty() || xacnhanmksua.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Bạn phải điền đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else if(!mksau.getText().toString().equals(xacnhanmksua.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                }
                else if(mksau.getText().toString().equals(xacnhanmksua.getText().toString())){
                    String phone=mksau.getText().toString();
                    Intent intent =new Intent();
                    Bundle b=new Bundle();
                    b.putString("phone",phone);
                    b.putString("image",picturePath);
                    intent.putExtras(b);
                    setResult(150,intent);
                    finish();
                }
            }
        });
        quaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                Bundle b=new Bundle();
                b.putString("phone",mk);
                b.putString("image",pic);
                intent.putExtras(b);
                setResult(150,intent);
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri selectImage= data.getData();
        picturePath=selectImage.toString();
        anhsua.setImageURI(selectImage);
    }
}