package com.example.konachprojrct;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.konachprojrct.beans.Category;
import com.example.konachprojrct.beans.Client;
import com.example.konachprojrct.beans.Credit;
import com.example.konachprojrct.beans.Produit;
import com.example.konachprojrct.services.ClientService;
import com.example.konachprojrct.services.CreditService;
import com.example.konachprojrct.services.ProduitServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ClientService clientService = new ClientService(getContext());
        ProduitServices produitServices = new ProduitServices(getContext());
        CreditService creditService = new CreditService(getContext());


        View view= inflater.inflate(R.layout.fragment_add, container, false);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.done);
        LinearLayout layout = view.findViewById(R.id.wd);
        layout.addView(inflater.inflate(R.layout.simple_item, container, false));

        ////
        final Spinner spn = layout.getChildAt(0).findViewById(R.id.spinner);
        ArrayList<String> produitName= new ArrayList<>();
        ArrayList<Produit> produit;
        produit = (ArrayList<Produit>) produitServices.findAll();
        for(Produit c:produit){
            produitName.add(c.getLibelle());
        }
        ArrayAdapter<String> adap = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, produitName);
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(adap);
        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ImageView add = view.findViewById(R.id.add);
        ImageView cancel = view.findViewById(R.id.cancel);
        final Spinner spinner = view.findViewById(R.id.c);
        ArrayList<String> clientName= new ArrayList<>();
        ArrayList<Client> clients;
        clients = (ArrayList<Client>) clientService.findAll();
        for(Client c:clients){
            clientName.add(c.getNom()+" "+c.getPrenom());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, clientName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Select your favorite Planet!");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(layout.getChildCount()>1){
                    layout.removeViewAt(layout.getChildCount()-1);
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.addView(inflater.inflate(R.layout.simple_item, container, false));
                for(int i=0;i<layout.getChildCount();i++){
                    final Spinner spn = layout.getChildAt(i).findViewById(R.id.spinner);

                    ArrayList<String> catName= new ArrayList<>();
                    ArrayList<Produit> cat;
                    cat = (ArrayList<Produit>) produitServices.findAll();
                    for(Produit c:cat){
                        catName.add(c.getLibelle());
                    }
                    ArrayAdapter<String> adap = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, catName);
                    adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn.setAdapter(adap);
                    spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }

            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Credit> credits = new ArrayList<>();
                for(int i=0;i<layout.getChildCount();i++){
                    View ve = layout.getChildAt(i);
                    Spinner s = ve.findViewById(R.id.spinner);
                    final EditText editText = ve.findViewById(R.id.quantite);
                    Date aujourdhui = new Date();
                    DateFormat shortDateFormat = DateFormat.getDateTimeInstance(
                            DateFormat.SHORT,
                            DateFormat.SHORT);
                    Double totale = Integer.parseInt(editText.getText().toString())*produit.get(s.getSelectedItemPosition()).getPrix();
                    Credit credit = new Credit(clients.get(spinner.getSelectedItemPosition()).getId(),produit.get(s.getSelectedItemPosition()).getId(),Integer.parseInt(editText.getText().toString()),shortDateFormat.format(aujourdhui),"Non payé",totale);
                    credits.add(credit);
                    Snackbar snackbar = Snackbar.make(view, "category bien ajouteé!", Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(Color.BLUE);
                    snackbar.show();
                }
                creditService.createList(credits);
                for (int i=0 ;i<layout.getChildCount();i++){
                    if(layout.getChildCount()>1){
                        layout.removeViewAt(i);
                    }
                }
                View ve = layout.getChildAt(0);
                EditText ed = ve.findViewById(R.id.quantite);
                ed.setText("");
                Snackbar snackbar = Snackbar
                        .make(view, "category bien ajouteé!", Snackbar.LENGTH_LONG);

                View sbView = snackbar.getView();
                sbView.setBackgroundColor(Color.BLUE);
                //listener.onDialogPositiveClick(AlertFragment.this);
                snackbar.show();
                Log.d("AZ","azaza");
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}