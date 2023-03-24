package com.example.bttl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDB extends SQLiteOpenHelper {
    public static final String TableName= "ContactTable";
    public static final String Id = "Id";
    public static final String Image = "Image";
    public static final String Job = "Job";
    public static final String Name = "FullName";
    public static final String Phone = "Phone";
    public static final String Email = "Email";


    

    public MyDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlCreate = "Create table if not exists "+ TableName +"("
                + Id + " Integer Primary key, "
                + Image + " Text, "
                + Job+ " Text, "
                + Name+ " Text, "
                + Phone+ " Text, "
                + Email+" Text)";
        sqLiteDatabase.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //xoa bang table contact da co
        sqLiteDatabase.execSQL("Drop table if exists "+TableName);
        //Tao lai
        onCreate(sqLiteDatabase);
    }

    public ArrayList<Contact> getAllContact()
    {
        ArrayList<Contact> list = new ArrayList<>();
        //Cau truy van
        String sql = "Select * from "+TableName;
        //Lay doi tuong csdl sqlite
        SQLiteDatabase db = this.getReadableDatabase();
        //chay cau truy van tra ve dang cursor
        Cursor cursor = db.rawQuery(sql,null);
        //tao ArrayList<Contact> de tra ve;
        if(cursor!=null)
            while (cursor.moveToNext())
            {
                Contact contact = new Contact(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5));
                list.add(contact);
            }
        return list;
    }
    //ham them mot contact vao bang TableContact
    public void addContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Id,contact.getId());
        value.put(Image,contact.getImages());
        value.put(Job,contact.getJob());
        value.put(Name,contact.getName());
        value.put(Phone,contact.getPhone());
        value.put(Email,contact.getEmail());
        db.insert(TableName,null,value);
        db.close();
    }

}
