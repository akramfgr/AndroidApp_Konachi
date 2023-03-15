package com.example.konachprojrct;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.konachprojrct.beans.Credit;
import com.example.konachprojrct.services.ClientService;
import com.example.konachprojrct.services.CreditService;
import com.example.konachprojrct.services.ProduitServices;

import java.util.ArrayList;

public class HistoAdapter extends RecyclerView.Adapter<HistoAdapter.MyViewHolder>{
    private ArrayList<Credit> credits;
    Context context;
    CreditService creditService;
    ProduitServices produitServices;
    ClientService clientService;
    public HistoAdapter(Context context , ArrayList<Credit> credits){
        this.context=context;
        this.credits = credits;
       creditService = new CreditService(context);
       produitServices = new ProduitServices(context);
       clientService = new ClientService(context);
    }
    public void setList(ArrayList<Credit> credits){
        this.credits=credits;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public HistoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.historique_item,parent,false);
        final HistoAdapter.MyViewHolder holder = new HistoAdapter.MyViewHolder(v);
        return holder;

    }
    @Override
    public void onBindViewHolder(@NonNull HistoAdapter.MyViewHolder holder, int position) {
        holder.cltprd.setText(credits.get(position).getQuantite()+" x "+produitServices.findById(credits.get(position).getIdProduit()).getLibelle()+" par "+clientService.findById(credits.get(position).getIdClient()).getPrenom()+" "+clientService.findById(credits.get(position).getIdClient()).getNom());
        holder.count.setText("count: "+credits.get(position).getQuantite());
        holder.totale.setText("totale: "+credits.get(position).getTotale()+" dh");
        holder.date.setText(credits.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return credits.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        // like on create methode
        TextView date ;
        TextView cltprd ;
        TextView count ;
        TextView totale ;

        public MyViewHolder(@NonNull View viewIem) {
            super(viewIem);
            cltprd = viewIem.findViewById(R.id.prd);
            count = viewIem.findViewById(R.id.totale);
            totale = viewIem.findViewById(R.id.etat);
            date = viewIem.findViewById(R.id.date);
        }
    }
}
