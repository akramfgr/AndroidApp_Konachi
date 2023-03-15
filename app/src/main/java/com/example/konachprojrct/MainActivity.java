package com.example.konachprojrct;



import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.konachprojrct.beans.Category;
import com.example.konachprojrct.beans.Client;
import com.example.konachprojrct.beans.Credit;
import com.example.konachprojrct.beans.Produit;
import com.example.konachprojrct.databinding.ActivityMainBinding;
import com.example.konachprojrct.services.CategoryServices;
import com.example.konachprojrct.services.ClientService;
import com.example.konachprojrct.services.CreditService;
import com.example.konachprojrct.services.ProduitServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_200)));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Menu");
        setContentView(R.layout.activity_main);
        replaceFragment(new ChartFragment(MainActivity.this));
        menu = findViewById(R.id.bottom_navigation);

        ClientService clientService = new ClientService(this);
        CreditService creditService = new CreditService(this);

        ProduitServices produitServices = new ProduitServices(this);

        for(Credit c: creditService.findAll()){
            Log.d("AA",c.toString());
        }
        for(Client c: clientService.findAll()){
            Log.d("AA",c.toString());
        }

        menu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.settings:
                        actionBar.setTitle("Chart");
                        replaceFragment(new CrudFragment());

                        break;
                    case R.id.history:
                        actionBar.setTitle("Historique");
                        replaceFragment(new HistoryFragment());
                        break;
                    case R.id.add:
                        actionBar.setTitle("Nouveau Credit");
                        replaceFragment(new AddFragment());
                        break;

                    case R.id.delete:
                        actionBar.setTitle("Credits");
                        replaceFragment(new DeleteFragment());
                        break;
                    case R.id.chart:
                        actionBar.setTitle("Menu");
                        Log.d("context",MainActivity.this.toString());
                        replaceFragment(new ChartFragment(MainActivity.this));
                        break;
                }
                return true;
            }
        });



    }
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmeLayout,fragment);
        fragmentTransaction.commit();
    }



}
