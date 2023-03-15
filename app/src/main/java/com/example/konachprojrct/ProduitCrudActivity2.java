package com.example.konachprojrct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.konachprojrct.beans.Category;
import com.example.konachprojrct.beans.Produit;
import com.example.konachprojrct.services.CategoryServices;
import com.example.konachprojrct.services.ProduitServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ProduitCrudActivity2 extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    CategoryServices categoryServices;
    ProduitServices produitServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produit_crud2);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_200)));
        View view = findViewById(R.id.produit_crud);
        produitServices = new ProduitServices(this);
        categoryServices = new CategoryServices(this);
        recyclerView = findViewById(R.id.recyclerView);
        ProduitAdapter adapter1 = new ProduitAdapter(view,this,(ArrayList<Produit>)produitServices.findAll());
        recyclerView.setAdapter(adapter1);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        floatingActionButton=findViewById(R.id.add_fab);
        Context context = this;
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View popup = LayoutInflater.from(context).inflate(R.layout.product_edit_alert, null, false);

                final EditText libelle = popup.findViewById(R.id.produitName);
                final EditText prix = popup.findViewById(R.id.produitPrix);
                final Spinner spinner = popup.findViewById(R.id.category);
                ArrayList<String> catName= new ArrayList<>();
                ArrayList<Category> cat;
                final int[] idCat = new int[1];
                cat = (ArrayList<Category>) categoryServices.findAll();
                for(Category c:cat){
                    catName.add(c.getName());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, catName);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        idCat[0] = cat.get(i).getId();
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
                AlertDialog dialog = new AlertDialog.Builder(context).setTitle("Edit : ").setMessage("lorem ipsum").setView(popup).setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Produit produit = new Produit(libelle.getText().toString(),Double.parseDouble(prix.getText().toString()),cat.get(idCat[0]-1).getId());
                        produitServices.create(produit);
                        adapter1.setProduits((ArrayList<Produit>) produitServices.findAll());
                        adapter1.notifyDataSetChanged();
                        Snackbar snackbar = Snackbar.make(view, "category bien ajouteé!", Snackbar.LENGTH_LONG);
                        View sbView = snackbar.getView();
                        sbView.setBackgroundColor(Color.BLUE);
                        snackbar.show();
                    }
                }).setNegativeButton("Annuler", null).create();
                dialog.show();
            }
        });

    }
}