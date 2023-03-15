package com.example.konachprojrct.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.konachprojrct.beans.Category;
import com.example.konachprojrct.beans.Produit;
import com.example.konachprojrct.util.MySQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class ProduitServices {
    private static final String TABLE_NAME ="produit";
    private static final String KEY_ID = "id";
    private static final String KEY_NOM = "libelle";
    private static final String KEY_PRIX = "prix";
    private static final String KEY_IDCATEGORY = "idCategory";
    private static String [] COLUMNS = {KEY_ID, KEY_NOM,KEY_PRIX,KEY_IDCATEGORY};
    private MySQLiteHelper helper;
    public ProduitServices(Context context) {
        this.helper = new MySQLiteHelper(context);

    }
    public void create(Produit e){
        SQLiteDatabase db = this.helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NOM, e.getLibelle());
        values.put(KEY_PRIX, e.getPrix());
        values.put(KEY_IDCATEGORY, e.getIdCategory());
        db.insert(TABLE_NAME,
                null,
                values);
        Log.d("insert", e.getLibelle());
        db.close();
    }
    public void update(Produit e){
        SQLiteDatabase db = this.helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NOM, e.getLibelle());
        values.put(KEY_PRIX, e.getPrix());
        values.put(KEY_IDCATEGORY, e.getIdCategory());
        db.update(TABLE_NAME,
                values,
                "id = ?",
                new String[]{e.getId()+""});
        db.close();
        Log.d("upda", e.getLibelle());
    }

    public Produit findById(int id){
        Produit e = null;
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
            e = new Produit();
            e.setId(c.getInt(c.getColumnIndexOrThrow("id")));
            e.setLibelle(c.getString(c.getColumnIndexOrThrow("libelle")));
            e.setPrix(c.getDouble(c.getColumnIndexOrThrow("prix")));
            e.setIdCategory(c.getInt(c.getColumnIndexOrThrow("idCategory")));
        }
        db.close();
        Log.d("findById","update"+e.getLibelle());
        return e;
    }
    public void delete(Produit e){
        SQLiteDatabase db = this.helper.getWritableDatabase();
        db.delete(
                TABLE_NAME,
                "id = ?",
                new String[] {String.valueOf(e.getId())}
        );
        db.close();
    }

    public List<Produit> findAll(){
        List<Produit> eds = new ArrayList<>();
        String req ="select * from "+TABLE_NAME;
        SQLiteDatabase db = this.helper.getReadableDatabase();
        Cursor c = db.rawQuery(req, null);
        Produit e = null;
        if(c.moveToFirst()){
            do{
                e = new Produit();
                e.setId(c.getInt(c.getColumnIndexOrThrow("id")));
                e.setLibelle(c.getString(c.getColumnIndexOrThrow("libelle")));
                e.setPrix(c.getDouble(c.getColumnIndexOrThrow("prix")));
                e.setIdCategory(c.getInt(c.getColumnIndexOrThrow("idCategory")));
                eds.add(e);
                Log.d("id = ", e.getLibelle()+"");
            }while(c.moveToNext());
        }
        return eds;
    }

    public List<Produit> produitsParCategory(int id){
        List<Produit> eds = new ArrayList<>();
        String req ="SELECT * FROM produit produit where idCategory = "+id;
        SQLiteDatabase db = this.helper.getReadableDatabase();
        Cursor c = db.rawQuery(req, null);
        Produit e = null;
        if(c.moveToFirst()){
            do{
                e = new Produit();
                e.setId(c.getInt(c.getColumnIndexOrThrow("id")));
                e.setLibelle(c.getString(c.getColumnIndexOrThrow("libelle")));
                e.setPrix(c.getDouble(c.getColumnIndexOrThrow("prix")));
                e.setIdCategory(c.getInt(c.getColumnIndexOrThrow("idCategory")));
                eds.add(e);
                Log.d("id = ", e.getLibelle()+"");
            }while(c.moveToNext());
        }
        return eds;
    }


}
