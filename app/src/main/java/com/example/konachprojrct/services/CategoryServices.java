package com.example.konachprojrct.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.konachprojrct.beans.Category;
import com.example.konachprojrct.util.MySQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class CategoryServices {
    private static final String TABLE_NAME ="category";
    private static final String KEY_ID = "id";
    private static final String KEY_NOM = "name";
    private static String [] COLUMNS = {KEY_ID, KEY_NOM};
    private MySQLiteHelper helper;
    public CategoryServices(Context context) {
        this.helper = new MySQLiteHelper(context);

    }
    public void create(Category e){
        SQLiteDatabase db = this.helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NOM, e.getName());
        db.insert(TABLE_NAME,
                null,
                values);
        Log.d("insert", e.getName());
        db.close();
    }
    public void update(Category e){
        SQLiteDatabase db = this.helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, e.getId());
        values.put(KEY_NOM, e.getName());
        db.update(TABLE_NAME,
                values,
                "id = ?",
                new String[]{e.getId()+""});
        db.close();
        Log.d("upda", e.getName());
    }

    public Category findById(int id){
        Category e = null;
        SQLiteDatabase db = this.helper.getReadableDatabase();
        Cursor c;
        c = db.query(TABLE_NAME,
                COLUMNS,
                "id = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);
        if(c.moveToFirst()){
            e = new Category();
            e.setId(c.getInt(c.getColumnIndexOrThrow("id")));
            e.setName(c.getString(c.getColumnIndexOrThrow("name")));
        }
        db.close();
        Log.d("findById","update"+e.getName());
        return e;
    }

    public void delete(Category e){
        SQLiteDatabase db = this.helper.getWritableDatabase();
        db.delete(
                TABLE_NAME,
                "id = ?",
                new String[] {String.valueOf(e.getId())}
        );
        db.close();
    }

    public List<Category> findAll(){
        List<Category> eds = new ArrayList<>();
        String req ="select * from "+TABLE_NAME;
        SQLiteDatabase db = this.helper.getReadableDatabase();
        Cursor c = db.rawQuery(req, null);
        Category e = null;
        if(c.moveToFirst()){
            do{
                e = new Category();
                e.setId(c.getInt(c.getColumnIndexOrThrow("id")));
                e.setName(c.getString(c.getColumnIndexOrThrow("name")));
                eds.add(e);
                Log.d("id = ", e.getId()+"");
            }while(c.moveToNext());
        }
        return eds;
    }


}
