package com.example.bttl;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ContactList extends AppCompatActivity {
    private ArrayList<Contact> ContactList;

    private Adapter ListAdapter;

    private EditText etSearch;

    private ListView lstContact;

    private Toolbar lienhe;

    private  int dem =0;

    private int SelectedItemId = -1;



    private MyDB db;

    //An giu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.contextmenu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.actionmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //Loc theo ten
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.SortNganh:
                Collections.sort(ContactList, new Comparator<Contact>() {
                    @Override
                    public int compare(Contact o1, Contact o2) {
                        return o1.getJob().compareTo(o2.getJob());
                    }
                });
                lstContact.setAdapter(ListAdapter);
                break;
            case R.id.SortName:
                Collections.sort(ContactList, new Comparator<Contact>() {
                    @Override
                    public int compare(Contact o1, Contact o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });
                lstContact.setAdapter(ListAdapter);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.mnuCount:
                String[] selectName =  ContactList.get(SelectedItemId).getName().split("\\s");
                for (Contact contact: ContactList)
                {
                    String[] partsName = contact.getName().split("\\s");
                    if(selectName[selectName.length-1].equalsIgnoreCase(partsName[partsName.length-1])){
                        dem++;
                    }
                }
                String result = "Có " + dem + " người tên " + selectName[selectName.length-1];
                Toast.makeText(getApplicationContext(),
                        result, Toast.LENGTH_LONG).show();
                dem=0;
                break;

            case R.id.mnuSendEmail:

                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("message/rfc822");
                emailIntent.putExtra(Intent.EXTRA_EMAIL  , new String[]{ContactList.get(SelectedItemId).getEmail()});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Phản hồi từ khách hàng");
                emailIntent.putExtra(Intent.EXTRA_TEXT   , "Nội dung:");
                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }

                break;

        }
//      ContactList = db.getAllContact();
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //lấy dữ liệu từ NewContact gửi về
        Bundle bundle = data.getExtras();
        int id = bundle.getInt("Id");
        String job = bundle.getString("Job");
        String name = bundle.getString("Name");
        String phone = bundle.getString("Phone");
        String email = bundle.getString("Email");


        if(requestCode==100 && resultCode==200 )
        {
            //đặt vào listData
            db.addContact(new Contact(id,"img1",job,name, phone,email));
        }


        //cập nhật adapter
        ListAdapter.notifyDataSetChanged();
        resetData();
    }
    private void resetData(){
        db = new MyDB(getApplicationContext(), "Contac3",null,1);
        ContactList  = db.getAllContact();
        ListAdapter = new Adapter(ContactList, this);
        lstContact.setAdapter(ListAdapter);
    }

    private void Actiontoolbar(){
        setSupportActionBar(lienhe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lienhe.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);


        FloatingActionButton img_floatingbutton = findViewById(R.id.btn_floating);
        img_floatingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("message/rfc822");
                emailIntent.putExtra(Intent.EXTRA_EMAIL  , new String[]{"ShopLaptopGroup6@gmail.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Phản hồi từ khách hàng");
                emailIntent.putExtra(Intent.EXTRA_TEXT   , "Nội dung:");
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));

            }
        });


        ContactList = new ArrayList<>();
        ArrayList<Contact> ContactList1 = new ArrayList<Contact>();
        db = new MyDB(this,"Contact",null,1);

        //Them du lieu lan dau(0 dung 2 lan se bi trung id)
//        db.addContact(new Contact(1,"","Contact Manager","Lê Thiên Khang","0000","LeThienKhang@gmail.com"));
//        db.addContact(new Contact(2,"","Gio Hang Manager","Tống Trung Đoàn ","1111","TongTrungDoan@gmail.com"));
//        db.addContact(new Contact(3,"","Location Manager","Nguyễn Đình An Huy","2222","NguyenDinhAnHuy@gmail.com"));
//        db.addContact(new Contact(4,"","Login Manager","Nguyễn Tuấn Hưng","3333","NguyenTuanHung@gmail.com"));
//        db.addContact(new Contact(5,"","Product Manager","Vũ Trung Tuấn","4444","VuTrungTuan@gmail.com"));

        ContactList = db.getAllContact();

        ListAdapter = new Adapter(ContactList,this);
        etSearch = findViewById(R.id.etSearch);
        lstContact = findViewById(R.id.lstContact);
        lienhe = findViewById(R.id.toolbarlienhe);


        Actiontoolbar();

        lstContact.setAdapter(ListAdapter);
        registerForContextMenu(lstContact);
        lstContact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                SelectedItemId = position;
                return false;
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                ListAdapter.getFilter().filter(s.toString());
                ListAdapter.notifyDataSetChanged();
                lstContact.setAdapter(ListAdapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }
}