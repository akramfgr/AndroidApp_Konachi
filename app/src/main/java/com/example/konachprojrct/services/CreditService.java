package com.example.konachprojrct.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.konachprojrct.beans.Credit;
import com.example.konachprojrct.util.MySQLiteHelper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;




public class CreditService{


    private static final String TABLE_NAME ="credit";

    private static final String KEY_ID = "id";
    private static final String KEY_CLIENT = "idClient";
    private static final String KEY_PRODUIT ="idProduit";
    private static final String KEY_QUANTITE ="quantite";
    private static final String KEY_DATE ="date";
    private static final String KEY_ETAT ="etat";
    private static final String KEY_TOTALE ="totale";




    private static String [] COLUMNS = {KEY_ID, KEY_CLIENT, KEY_PRODUIT,KEY_QUANTITE , KEY_DATE, KEY_ETAT,KEY_TOTALE};

    private MySQLiteHelper helper;

    public CreditService(Context context) {
        this.helper = new MySQLiteHelper(context);
    }

    public boolean create(Credit e){
        SQLiteDatabase db = this.helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CLIENT, e.getIdClient());
        values.put(KEY_PRODUIT, e.getIdProduit());
        values.put(KEY_QUANTITE, e.getQuantite()+"");
        values.put(KEY_DATE, e.getDate());
        values.put(KEY_ETAT, e.getEtat());
        values.put(KEY_TOTALE, e.getTotale());
        db.insert(TABLE_NAME,
                null,
                values);
        Log.d("insert", e.toString());
        db.close();
        return false;
    }

    public boolean update(Credit e){
        SQLiteDatabase db = this.helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CLIENT, e.getIdClient());
        values.put(KEY_PRODUIT, e.getIdProduit());
        values.put(KEY_QUANTITE, e.getQuantite()+"");
        values.put(KEY_DATE, e.getDate());
        values.put(KEY_ETAT, e.getEtat());
        values.put(KEY_TOTALE, e.getTotale());
        db.update(TABLE_NAME,
                values,
                "id = ?",
                new String[]{e.getId()+""});
        db.close();
        return true;
    }


    public Credit findById(int id){
        Credit e = null;
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
            e = new Credit();
            e.setId(c.getInt(0));
            e.setIdClient(c.getInt(2));
            e.setIdProduit(c.getInt(1));
            e.setQuantite(c.getInt(3));
            e.setDate(c.getString(4));
            e.setEtat(c.getString(5));
            e.setTotale(c.getDouble(6));
        }
        db.close();
        return e;
    }

    public boolean delete(Credit e){
        SQLiteDatabase db = this.helper.getWritableDatabase();
        db.delete(TABLE_NAME,
                "id = ?",
                new String[]{String.valueOf(e.getId())});
        db.close();
        return true;
    }

    public List<Credit> findAll(){
        List<Credit> eds = new ArrayList<>();
        String req ="select * from "+TABLE_NAME;
        SQLiteDatabase db = this.helper.getReadableDatabase();
        Cursor c = db.rawQuery(req, null);
        Credit e = null;
        if(c.moveToFirst()){
            do{
                e = new Credit();
                e.setId(c.getInt(0));
                e.setIdProduit(c.getInt(1));
                e.setIdClient(c.getInt(2));
                e.setQuantite(c.getInt(3));
                e.setDate(c.getString(4));
                e.setEtat(c.getString(5));
                e.setTotale(c.getDouble(6));
                eds.add(e);
                Log.d("id = ", e.getId()+"");
            }while(c.moveToNext());
        }
        return eds;
    }
    public List<Credit> findNonPayé(){
        List<Credit> eds = new ArrayList<>();
        String req ="select * from "+TABLE_NAME+" where etat == 'Non Payé'";
        SQLiteDatabase db = this.helper.getReadableDatabase();
        Cursor c = db.rawQuery(req, null);
        Credit e = null;
        if(c.moveToFirst()){
            do{
                e = new Credit();
                e.setId(c.getInt(0));
                e.setIdProduit(c.getInt(1));
                e.setIdClient(c.getInt(2));
                e.setQuantite(c.getInt(3));
                e.setDate(c.getString(4));
                e.setEtat(c.getString(5));
                e.setTotale(c.getDouble(6));
                eds.add(e);
                Log.d("id = ", e.getId()+"");
            }while(c.moveToNext());
        }
        return eds;
    }
    public List<Credit> searchByClient(String text){
        List<Credit> eds = new ArrayList<>();
        String req ="select credit.id,credit.idproduit,credit.idClient,credit.quantite,credit.date,credit.etat,credit.totale from "+TABLE_NAME+" inner join client on client.id=credit.idClient where client.nom like '%"+text+"%'";
        SQLiteDatabase db = this.helper.getReadableDatabase();
        Cursor c = db.rawQuery(req, null);
        Credit e = null;
        if(c.moveToFirst()){
            do{
                e = new Credit();
                e.setId(c.getInt(0));
                e.setIdProduit(c.getInt(1));
                e.setIdClient(c.getInt(2));
                e.setQuantite(c.getInt(3));
                e.setDate(c.getString(4));
                e.setEtat(c.getString(5));
                e.setTotale(c.getDouble(6));
                eds.add(e);
                Log.d("id = ", e.getId()+"");
            }while(c.moveToNext());
        }

        return eds;

    }
    public List<Credit> searchByProdyuit(String text){
        List<Credit> eds = new ArrayList<>();
        String req ="select credit.id,credit.idproduit,credit.idClient,credit.quantite,credit.date,credit.etat,credit.totale from "+TABLE_NAME+" inner join produit on produit.id=credit.idProduit where produit.libelle like '%"+text+"%'";
        SQLiteDatabase db = this.helper.getReadableDatabase();
        Cursor c = db.rawQuery(req, null);
        Credit e = null;
        if(c.moveToFirst()){
            do{
                e = new Credit();
                e.setId(c.getInt(0));
                e.setIdProduit(c.getInt(1));
                e.setIdClient(c.getInt(2));
                e.setQuantite(c.getInt(3));
                e.setDate(c.getString(4));
                e.setEtat(c.getString(5));
                e.setTotale(c.getDouble(6));
                eds.add(e);
                Log.d("id = ", e.getId()+"");
            }while(c.moveToNext());
        }

        return eds;

    }
    public List<Credit> searchByDate(String text){
        List<Credit> eds = new ArrayList<>();
        String req ="select * from "+TABLE_NAME+" where date like '%"+text+"%'";
        SQLiteDatabase db = this.helper.getReadableDatabase();
        Cursor c = db.rawQuery(req, null);
        Credit e = null;
        if(c.moveToFirst()){
            do{
                e = new Credit();
                e.setId(c.getInt(0));
                e.setIdProduit(c.getInt(1));
                e.setIdClient(c.getInt(2));
                e.setQuantite(c.getInt(3));
                e.setDate(c.getString(4));
                e.setEtat(c.getString(5));
                e.setTotale(c.getDouble(6));
                eds.add(e);
                Log.d("id = ", e.getId()+"");
            }while(c.moveToNext());
        }

        return eds;

    }

    public boolean createList(ArrayList<Credit> creditList){
        SQLiteDatabase db = this.helper.getWritableDatabase();
        for(Credit e:creditList){
            ContentValues values = new ContentValues();
            values.put(KEY_CLIENT, e.getIdClient());
            values.put(KEY_PRODUIT, e.getIdProduit());
            values.put(KEY_QUANTITE, e.getQuantite()+"");
            values.put(KEY_DATE, e.getDate()+"");
            values.put(KEY_ETAT, e.getEtat());
            values.put(KEY_TOTALE, e.getTotale());
            db.insert(TABLE_NAME,
                    null,
                    values);
            Log.d("insert", e.toString());
        }

        db.close();
        return false;
    }

    public List<Credit> creditParClient(int id) {
        List<Credit> eds = new ArrayList<>();
        String req ="select * from "+TABLE_NAME+" where idClient ="+id;
        SQLiteDatabase db = this.helper.getReadableDatabase();
        Cursor c = db.rawQuery(req, null);
        Credit e = null;
        if(c.moveToFirst()){
            do{
                e = new Credit();
                e.setId(c.getInt(0));
                e.setIdProduit(c.getInt(1));
                e.setIdClient(c.getInt(2));
                e.setQuantite(c.getInt(3));
                e.setDate(c.getString(4));
                e.setEtat(c.getString(5));
                e.setTotale(c.getDouble(6));
                eds.add(e);
                Log.d("id = ", e.getId()+"");
            }while(c.moveToNext());
        }
        return eds;
    }
    public double totalParClient(int id) {
        List<Credit> eds = new ArrayList<>();
        String req ="select SUM(totale) as totale from "+TABLE_NAME+" where idClient ="+id +" and etat like 'Non payé'";
        SQLiteDatabase db = this.helper.getReadableDatabase();
        Cursor c = db.rawQuery(req, null);
        double n =0;
        if(c.moveToFirst()){
            do{
                n = c.getDouble(0);
            }while(c.moveToNext());
        }
        Log.d("AAA",n+"");
        return n;
    }
}