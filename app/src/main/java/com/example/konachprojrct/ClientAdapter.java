package com.example.konachprojrct;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.konachprojrct.beans.Client;
import com.example.konachprojrct.services.CategoryServices;
import com.example.konachprojrct.services.ClientService;
import com.example.konachprojrct.services.CreditService;
import com.example.konachprojrct.services.ProduitServices;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.MyViewHolder>{
    private ArrayList<Client> clients;
    Context context;
    ClientService clientService;
    CategoryServices categoryServices;
    ProduitServices produitServices;
    CreditService creditService;
    View sn;
    public ClientAdapter(View sn ,Context context , ArrayList<Client> clients){
        this.context=context;
        this.clients = clients;
        this.sn=sn;
        categoryServices = new CategoryServices(context);
        produitServices = new ProduitServices(context);
        clientService = new ClientService(context);
    }
    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ClientAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.client_item,parent,false);
        final ClientAdapter.MyViewHolder holder = new ClientAdapter.MyViewHolder(v);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View popup = LayoutInflater.from(context).inflate(R.layout.client_alert, null, false);
                final EditText nom = popup.findViewById(R.id.nom);
                final EditText prenom = popup.findViewById(R.id.prenom);
                final EditText telephone = popup.findViewById(R.id.tlt);
                final EditText cin = popup.findViewById(R.id.numero);


                nom.setText(clients.get(holder.getAdapterPosition()).getNom());
                prenom.setText(clients.get(holder.getAdapterPosition()).getPrenom());
                telephone.setText(clients.get(holder.getAdapterPosition()).getTelephone()+"");
                cin.setText(clients.get(holder.getAdapterPosition()).getCin());

                AlertDialog dialog = new AlertDialog.Builder(context).setTitle("Edit : ").setMessage("lorem ipsum").setView(popup).setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Client client = clientService.findById(clients.get(holder.getAdapterPosition()).getId());
                        client.setNom(nom.getText().toString());
                        client.setPrenom(prenom.getText().toString());
                        client.setCin(cin.getText().toString());
                        client.setTelephone(Long.parseLong(telephone.getText().toString()));
                        clientService.update(client);
                        clients = (ArrayList<Client>) clientService.findAll();
                        notifyDataSetChanged();
                        Snackbar snackbar = Snackbar.make(sn, "category bien ajouteé!", Snackbar.LENGTH_LONG);
                        TextView textView = sn.findViewById(com.google.android.material.R.id.snackbar_text);
                        View sbView = snackbar.getView();
                        sbView.setBackgroundColor(Color.BLUE);
                        //listener.onDialogPositiveClick(AlertFragment.this);
                        snackbar.show();
                    }
                }).setNegativeButton("Annuler", null).create();
                dialog.show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(clients.get(position).getPrenom()+" "+clients.get(position).getNom());
        holder.cin.setText("CIN: "+clients.get(position).getCin());
        holder.numero.setText("TELE: "+clients.get(position).getTelephone()+"");
        holder.appel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + clients.get(holder.getAdapterPosition()).getTelephone()));
                    context.startActivity(intent);
                } catch (Exception e) {
                    //TODO smth
                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return clients.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        // like on create methode
        TextView name ;
        TextView cin ;
        TextView numero ;
        ImageView appel ;


        public MyViewHolder(@NonNull View viewIem) {
            super(viewIem);
            name = viewIem.findViewById(R.id.name);
            cin = viewIem.findViewById(R.id.numero);
            numero = viewIem.findViewById(R.id.tlt);
            appel = viewIem.findViewById(R.id.appel);


        }
    }
}
