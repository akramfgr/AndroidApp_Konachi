package com.example.konachprojrct;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.konachprojrct.beans.Category;
import com.example.konachprojrct.beans.Produit;
import com.example.konachprojrct.services.CategoryServices;
import com.example.konachprojrct.services.ProduitServices;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ProduitAdapter extends RecyclerView.Adapter<ProduitAdapter.MyViewHolder>{
    public void setProduits(ArrayList<Produit> produits) {
        this.produits = produits;
    }

    private ArrayList<Produit> produits;
    Context context;
    CategoryServices categoryServices;
    ProduitServices produitServices;
    View sn;
    public ProduitAdapter(View sn ,Context context , ArrayList<Produit> produits){
        this.context=context;
        this.produits = produits;
        categoryServices = new CategoryServices(context);
        produitServices = new ProduitServices(context);
        this.sn = sn;
    }
    @NonNull
    @Override
    public ProduitAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.produit_item,parent,false);
        final MyViewHolder holder = new MyViewHolder(v);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ProduitAdapter.MyViewHolder holder, int position) {
        holder.libelle.setText(produits.get(position).getLibelle());
        holder.category.setText("Category : "+categoryServices.findById(produits.get(position).getIdCategory()).getName()+"");
        holder.prix.setText("Prix : "+produits.get(position).getPrix()+ "dh");
        holder.edit.setOnClickListener(new View.OnClickListener() {
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
                int i=0;
                for(Category c:cat){
                    if(produits.get(holder.getAdapterPosition()).getIdCategory() == c.getId())
                        i=cat.indexOf(c);
                    catName.add(c.getName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, catName);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                spinner.setSelection(i);
                libelle.setText(produits.get(holder.getAdapterPosition()).getLibelle());
                prix.setText(produits.get(holder.getAdapterPosition()).getPrix()+"");
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
                        Produit produit = produitServices.findById(produits.get(holder.getAdapterPosition()).getId());
                        produit.setLibelle(libelle.getText().toString());
                        produit.setPrix(Double.parseDouble(prix.getText().toString()));
                        produit.setIdCategory(cat.get(idCat[0]-1).getId());
                        produitServices.update(produit);
                        produits = (ArrayList<Produit>) produitServices.findAll();
                        notifyDataSetChanged();
                        Snackbar snackbar = Snackbar
                                .make(sn, "category bien ajouteé!", Snackbar.LENGTH_LONG);
                        View sbView = snackbar.getView();
                        sbView.setBackgroundColor(Color.BLUE);
                        //listener.onDialogPositiveClick(AlertFragment.this);
                        snackbar.show();

                    }
                }).setNegativeButton("Annuler", null).create();
                dialog.show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return produits.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        // like on create methode
        TextView libelle ;
        TextView category ;
        TextView prix ;
        ImageView edit ;


        public MyViewHolder(@NonNull View viewIem) {
            super(viewIem);
            libelle = viewIem.findViewById(R.id.libelle);
            category = viewIem.findViewById(R.id.category);
            prix = viewIem.findViewById(R.id.prix);
            edit = viewIem.findViewById(R.id.edit);


        }
    }
}


