package com.example.bttl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;

    DrawerLayout drawerLayout;
    ArrayList<sanpham> sp;
    sanphamadapter spadapter;

    MenuAdapter menuAdapter;
    ImageView imageButton;
    EditText txttimkiem;
    ImageView btntimkiem;
    ImageView btnhuy;
    ArrayList<sanpham> sptimkiem;
    ArrayList<sanpham> spbandau;
    ImageView btntang;
    ImageView btngiam;
   TextView trang;
   int page =1;
   public static ArrayList<giohang> giohangArrayList;
   ArrayList<sanpham> spshow;

    private int slitem=0;
    String url="https://appbanlaptop.000webhostapp.com/api/getsanpham.php";
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
        setContentView(R.layout.activity_main);
        Anhxa();
        ActionBar();
        ActionViewfipper();
        getsp(url);

        registerForContextMenu(imageButton);
        btntimkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txttimkiem.getText().toString().trim().isEmpty()){
                    Toast.makeText(MainActivity.this, "Bạn chưa nhập tên laptop", Toast.LENGTH_SHORT).show();
                }
                else {
                    sptimkiem.clear();
                    btnhuy.setVisibility(View.VISIBLE);
                    btnhuy.setClickable(true);
                    for(sanpham x :spbandau){
                        if(x.getTensp().toLowerCase().contains(txttimkiem.getText().toString().toLowerCase().trim())){
                            sptimkiem.add(x);
                        }
                    }
                    sp.clear();
                    sp.addAll(sptimkiem);
                    if(sp.size()%4==0){
                        slitem=sp.size()/4;
                    }
                    else{
                        slitem=sp.size()/4+1;
                    }
                    if(slitem==1){
                        btntang.setVisibility(View.INVISIBLE);
                    }else{
                        btntang.setVisibility(View.VISIBLE);
                    }
                    trang.setText("1");
                    btngiam.setVisibility(View.INVISIBLE);
                    show(1);
                    Toast.makeText(MainActivity.this, String.valueOf(slitem), Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txttimkiem.setText("");
                btnhuy.setVisibility(View.INVISIBLE);
                btnhuy.setClickable(false);
                sptimkiem.clear();
                sp.clear();
                sp.addAll(spbandau);
                if(sp.size()%4==0){
                    slitem=sp.size()/4;
                }
                else{
                    slitem=sp.size()/4+1;
                }
                if(slitem==1){
                    btntang.setVisibility(View.INVISIBLE);
                }else{
                    btntang.setVisibility(View.VISIBLE);
                }
                trang.setText("1");
                btngiam.setVisibility(View.INVISIBLE);
                show(1);
                Toast.makeText(MainActivity.this, String.valueOf(slitem), Toast.LENGTH_SHORT).show();
            }
        });
        btngiam.setVisibility(View.INVISIBLE);
        btntang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int slm=Integer.parseInt(trang.getText().toString())+1;
                if(slm>=slitem){
                    btntang.setVisibility(View.INVISIBLE);
                    btngiam.setVisibility(View.VISIBLE);
                }
                else{
                    btntang.setVisibility(View.VISIBLE);
                    btngiam.setVisibility(View.VISIBLE);
                }
                trang.setText(String.valueOf(slm));
                show(slm);
            }
        });
        btngiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slm=Integer.parseInt(trang.getText().toString())-1;
                if(slm<=1){
                    btngiam.setVisibility(View.INVISIBLE);
                    btntang.setVisibility(View.VISIBLE);
                }
                else{
                    btntang.setVisibility(View.VISIBLE);
                    btngiam.setVisibility(View.VISIBLE);
                }
                trang.setText(String.valueOf(slm));
                show(slm);
            }
        });
    }

    private void show(int page){
        spshow.clear();
        int dau=(page-1)*4;
        int cuoi=page*4;
        if(cuoi>=sp.size()){
            cuoi=sp.size();
        }
        for(int i=dau;i<cuoi;i++){
            spshow.add(sp.get(i));
            spadapter.notifyDataSetChanged();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
               break;
            case R.id.nav_location:
                Toast.makeText(this, "Location", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_contact:
                Toast.makeText(this, "Contatc", Toast.LENGTH_SHORT).show();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context,menu);
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.sorttenaz:
                Collections.sort(sp, new Comparator<sanpham>() {
                            @Override
                            public int compare(sanpham sv1, sanpham sv2) {
                                return (sv1.getTensp().compareTo(sv2.getTensp()));
                            }
                        }
                );
                show(Integer.parseInt(trang.getText().toString().trim()));
                break;
            case R.id.sortten:
                Collections.sort(sp, new Comparator<sanpham>() {
                            @Override
                            public int compare(sanpham sv1, sanpham sv2) {
                                return (sv2.getTensp().compareTo(sv1.getTensp()));
                            }
                        }
                );
                show(Integer.parseInt(trang.getText().toString().trim()));
                break;
            case R.id.sortgiatang:
                Collections.sort(sp, new Comparator<sanpham>() {
                            @Override
                            public int compare(sanpham sv1, sanpham sv2) {
                                return (Double.parseDouble(sv1.getGiasp())<(Double.parseDouble(sv2.getGiasp()))?-1:1);
                            }
                        }
                );
                show(Integer.parseInt(trang.getText().toString().trim()));
                break;
            case R.id.sortgia:
                Collections.sort(sp, new Comparator<sanpham>() {
                            @Override
                            public int compare(sanpham sv1, sanpham sv2) {
                                return (Double.parseDouble(sv1.getGiasp())>(Double.parseDouble(sv2.getGiasp()))?-1:1);
                            }
                        }
                );
                show(Integer.parseInt(trang.getText().toString().trim()));
                break;
        }
        return super.onContextItemSelected(item);
    }
    private void ActionViewfipper(){
        List<String> mangquangcao=new ArrayList<>();
        mangquangcao.add("https://laptops.vn/uploads/1920_x_659_1614062618.jpg");
        mangquangcao.add("https://kimlongcenter.com/upload/image/TOP%20laptop%20dell.png");
        mangquangcao.add("https://truonggiang.vn/wp-content/uploads/2021/07/banner-laptop-sinh-vien-scaled.jpg");
        for(int i=0;i<mangquangcao.size();i++){
            ImageView imageView=new ImageView(getApplicationContext());
            Picasso.get().load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_right);
        Animation slide_out= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
    }
    private void ActionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    private void getsp(String url){

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response !=null){
                            int Id=0;
                            String tensp="";
                            String giasp="";
                            String hinhanh="";
                            String mota="";
                            for(int i=0;i<response.length();i++){
                                try {

                                    JSONObject object =response.getJSONObject(i);
                                    Id=object.getInt("Id");
                                    tensp=object.getString("Tensp");
                                    giasp=object.getString("Giasp");
                                    hinhanh=object.getString("Hinhanh");
                                    mota=object.getString("Mota");
                                    sp.add(new sanpham(Id,tensp,giasp,hinhanh,mota));
                                } catch (JSONException e) {
                                    Toast.makeText(MainActivity.this,"fail" , Toast.LENGTH_LONG).show();
                                    e.printStackTrace();
                                }
                            }
                            if(sp.size()%4==0){
                                slitem=sp.size()/4;
                            }
                            else{
                                slitem=sp.size()/4+1;
                            }
                            if(slitem==1){
                                btntang.setVisibility(View.INVISIBLE);
                            }else{
                                btntang.setVisibility(View.VISIBLE);
                            }
                            show(page);
                            spbandau.addAll(sp);

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
    private void Anhxa(){
        toolbar=(Toolbar) findViewById(R.id.toobarmanhinhchinh);
        viewFlipper=(ViewFlipper) findViewById(R.id.viewflipper);
        recyclerView=(RecyclerView) findViewById(R.id.recycleview);
        imageButton=(ImageView) findViewById(R.id.imagebutton);
        txttimkiem=(EditText) findViewById(R.id.txttimkiem);
        btntimkiem=(ImageView) findViewById(R.id.nuttimkiem);
        btnhuy=(ImageView) findViewById(R.id.nuthuy);
        trang=(TextView) findViewById(R.id.textrang);
        sptimkiem=new ArrayList<>();
        spbandau=new ArrayList<>();
        drawerLayout=(DrawerLayout) findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationview);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        spshow=new ArrayList<>();
        sp=new ArrayList<>();
        spadapter=new sanphamadapter(this,spshow);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(spadapter);
        btntang=(ImageView) findViewById(R.id.buttontang);
        btngiam=(ImageView) findViewById(R.id.buttongiam);
        if(giohangArrayList != null){

        }else{
            giohangArrayList = new ArrayList<>();
        }

    }
}