package com.example.konachprojrct.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "konnache";
    private static final String CREATE_TABLE_CATEGORY="create table category(" + "id INTEGER primary key autoincrement," + "name TEXT)";
    private static final String CREATE_TABLE_PRODUIT="create table produit(id INTEGER primary key autoincrement,libelle TEXT,prix DOUBLE,idCategory INTEGER,FOREIGN KEY (idCategory) REFERENCES category(id))";
    private static final String CREATE_TABLE_CLIENT = "create table client(" +
            "id INTEGER primary key autoincrement," +
            "nom TEXT," +
            "prenom TEXT," +
            "cin TEXT," +
            "telephone INTEGER)";

    private static final String CREATE_TABLE_CREDIT = "create table credit(" +
            "id INTEGER primary key autoincrement," +
            "idproduit INTEGER NOT NULL," +
            "idClient INTEGER NOT NULL," +
            "quantite INTEGER," +
            "date TEXT,"+
            "etat TEXT," +
            "totale DOUBLE,"+
            "FOREIGN KEY (idClient) REFERENCES client(id)," +
            "FOREIGN KEY (idproduit) REFERENCES produit(id))";

    public MySQLiteHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }





    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_CATEGORY);
        sqLiteDatabase.execSQL(CREATE_TABLE_PRODUIT);
        sqLiteDatabase.execSQL(CREATE_TABLE_CLIENT);
        sqLiteDatabase.execSQL(CREATE_TABLE_CREDIT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP table if exists category");
        sqLiteDatabase.execSQL("DROP table if exists client");
        sqLiteDatabase.execSQL("DROP table if exists produit");
        sqLiteDatabase.execSQL("DROP table if exists credit");
        this.onCreate(sqLiteDatabase);

    }
}
