package com.example.konachprojrct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
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
import android.widget.EditText;
import android.widget.TextView;

import com.example.konachprojrct.beans.Client;
import com.example.konachprojrct.services.ClientService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ClientCrudActivity2 extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    ClientService clientService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_crud2);
        View view = findViewById(R.id.client_crud);
        clientService = new ClientService(this);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_200)));
        recyclerView = findViewById(R.id.recyclerView);
        ClientAdapter adapter = new ClientAdapter(view,this,(ArrayList<Client>)clientService.findAll());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        floatingActionButton=findViewById(R.id.add_fab);
        Context context = this;

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popup = LayoutInflater.from(context).inflate(R.layout.client_alert, null, false);
                final EditText nom = popup.findViewById(R.id.nom);
                final EditText prenom = popup.findViewById(R.id.prenom);
                final EditText telephone = popup.findViewById(R.id.tlt);
                final EditText cin = popup.findViewById(R.id.numero);
                AlertDialog dialog = new AlertDialog.Builder(context).setTitle("Create : ").setMessage("lorem ipsum").setView(popup).setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Client client = new Client();
                        client.setNom(nom.getText().toString());
                        client.setPrenom(prenom.getText().toString());
                        client.setCin(cin.getText().toString());
                        client.setTelephone(Long.parseLong(telephone.getText().toString()));
                        clientService.create(client);
                        adapter.setClients((ArrayList<Client>) clientService.findAll());
                        Snackbar snackbar = Snackbar
                                .make(view, "category bien ajouteé!", Snackbar.LENGTH_LONG);
                        TextView textView = view.findViewById(com.google.android.material.R.id.snackbar_text);
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
}