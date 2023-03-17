package com.example.bttl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
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

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView listviewmenu;
    DrawerLayout drawerLayout;
    ArrayList<sanpham> sp;
    sanphamadapter spadapter;
    ArrayList<menuitem> arrayList;
    MenuAdapter menuAdapter;
    ImageView imageButton;
    EditText txttimkiem;
    Button btntimkiem;
    Button btnhuy;
    ArrayList<sanpham> sptimkiem;
    ArrayList<sanpham> spbandau;
    String url="https://appbanlaptop.000webhostapp.com/api/getsanpham.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        ActionBar();
        ActionViewfipper();
        getsp(url);
        Menuevent();
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
                    spadapter.notifyDataSetChanged();
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
                spadapter.notifyDataSetChanged();
            }
        });
    }
    private void Menuevent(){
        listviewmenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                switch (i){
                    case 0:
                        Toast.makeText(MainActivity.this, "Trang chủ", Toast.LENGTH_SHORT).show();
                    break;
                    case 1:
                        Toast.makeText(MainActivity.this, "Địa chỉ", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this, "Liên lạc", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
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
                spadapter.notifyDataSetChanged();
                break;
            case R.id.sortten:
                Collections.sort(sp, new Comparator<sanpham>() {
                            @Override
                            public int compare(sanpham sv1, sanpham sv2) {
                                return (sv2.getTensp().compareTo(sv1.getTensp()));
                            }
                        }
                );
                spadapter.notifyDataSetChanged();
                break;
            case R.id.sortgiatang:
                Collections.sort(sp, new Comparator<sanpham>() {
                            @Override
                            public int compare(sanpham sv1, sanpham sv2) {
                                return (sv1.getGiasp().compareTo(sv2.getGiasp()));
                            }
                        }
                );
                spadapter.notifyDataSetChanged();
                break;
            case R.id.sortgia:
                Collections.sort(sp, new Comparator<sanpham>() {
                            @Override
                            public int compare(sanpham sv1, sanpham sv2) {
                                return (sv2.getGiasp().compareTo(sv1.getGiasp()));
                            }
                        }
                );
                spadapter.notifyDataSetChanged();
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
                                    spadapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    Toast.makeText(MainActivity.this,"fail" , Toast.LENGTH_LONG).show();
                                    e.printStackTrace();
                                }
                            }
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
        navigationView=(NavigationView) findViewById(R.id.navigationview);
        listviewmenu=(ListView) findViewById(R.id.manhinhmenu);
        imageButton=(ImageView) findViewById(R.id.imagebutton);
        txttimkiem=(EditText) findViewById(R.id.txttimkiem);
        btntimkiem=(Button) findViewById(R.id.nuttimkiem);
        btnhuy=(Button) findViewById(R.id.nuthuy);
        sptimkiem=new ArrayList<>();
        spbandau=new ArrayList<>();
        drawerLayout=(DrawerLayout) findViewById(R.id.drawerlayout);
        sp=new ArrayList<>();
        spadapter=new sanphamadapter(this,sp);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(spadapter);
        arrayList=new ArrayList<>();
        arrayList.add(new menuitem(R.drawable.house,"Trang chủ"));
        arrayList.add(new menuitem(R.drawable.placeholder,"Địa chỉ"));
        arrayList.add(new menuitem(R.drawable.communicate,"Liên lạc"));
        menuAdapter=new MenuAdapter(this,arrayList);
        listviewmenu.setAdapter(menuAdapter);

    }
}