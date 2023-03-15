package com.example.konachprojrct.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.konachprojrct.beans.Client;
import com.example.konachprojrct.util.MySQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private static final String TABLE_NAME ="client";

    private static final String KEY_ID = "id";
    private static final String KEY_NOM = "nom";
    private static final String KEY_PRENOM ="prenom";
    private static final String KEY_CIN ="cin";
    private static final String KEY_TELEPHONE ="telephone";

    private static String [] COLUMNS = {KEY_ID, KEY_NOM, KEY_PRENOM, KEY_CIN, KEY_TELEPHONE};

    private MySQLiteHelper helper;
    private static ClientService clientService;



    public ClientService(Context context) {
        this.helper = new MySQLiteHelper(context);
    }

    public void create(Client e){
        SQLiteDatabase db = this.helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NOM, e.getNom());
        values.put(KEY_PRENOM, e.getPrenom());
        values.put(KEY_CIN, e.getCin());
        values.put(KEY_TELEPHONE, e.getTelephone());
        db.insert(TABLE_NAME,
                null,
                values);
        Log.d("insert", e.getNom());
        db.close();
    }

    public void update(Client e){
        SQLiteDatabase db = this.helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, e.getId());
        values.put(KEY_NOM, e.getNom());
        values.put(KEY_PRENOM, e.getPrenom());
        values.put(KEY_CIN, e.getCin());
        values.put(KEY_TELEPHONE, e.getTelephone());

        db.update(TABLE_NAME,
                values,
                "id = ?",
                new String[]{e.getId()+""});
        db.close();
    }
    public Client findById(int id){
        Client e = null;
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
            e = new Client();
            e.setId(c.getInt(0));
            e.setNom(c.getString(1));
            e.setPrenom(c.getString(2));
            e.setCin(c.getString(3));
            e.setTelephone(c.getLong(4));
        }
        db.close();
        return e;
    }

    public void delete(Client e){
        SQLiteDatabase db = this.helper.getWritableDatabase();
        db.delete(TABLE_NAME,
                "id = ?",
                new String[]{String.valueOf(e.getId())});
        db.close();
    }

    public List<Client> findAll(){
        List<Client> eds = new ArrayList<>();
        String req ="select * from "+TABLE_NAME;
        SQLiteDatabase db = this.helper.getReadableDatabase();
        Cursor c = db.rawQuery(req, null);
        Client e = null;
        if(c.moveToFirst()){
            do{
                e = new Client();
                e.setId(c.getInt(0));
                e.setNom(c.getString(1));
                e.setPrenom(c.getString(2));
                e.setCin(c.getString(3));
                e.setTelephone(c.getLong(4));
                eds.add(e);
                Log.d("id = ", e.getId()+"");
            }while(c.moveToNext());
        }
        return eds;
    }
}
