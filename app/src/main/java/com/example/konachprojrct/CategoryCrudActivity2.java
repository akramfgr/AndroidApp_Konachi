package com.example.konachprojrct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.konachprojrct.beans.Category;
import com.example.konachprojrct.beans.Produit;
import com.example.konachprojrct.services.CategoryServices;
import com.example.konachprojrct.services.ProduitServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryCrudActivity2 extends AppCompatActivity implements AlertFragment.NoticeDialogListener {
    CategoryServices categoryServices;
    ProduitServices produitServices;
    List<Category> groupList;
    List<String> childList;
    Map<String, List<Produit>> mobileCollection;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_200)));
        setContentView(R.layout.activity_category_crud2);
        categoryServices = new CategoryServices(this);
        produitServices = new ProduitServices(this);
        groupList = categoryServices.findAll();
        for (Category c:groupList){
            Log.d("cat",c.toString());
        }
        createCollection();
        floatingActionButton = findViewById(R.id.add_fab);
        FragmentManager fm = getSupportFragmentManager();
        View view = findViewById(R.id.activity_crud);
        expandableListView = findViewById(R.id.elvMobiles);
        expandableListAdapter = new MyExpandableListAdapter(this, groupList, mobileCollection,fm,view);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertFragment alertFragment = new AlertFragment(view,CategoryCrudActivity2.this, (MyExpandableListAdapter) expandableListAdapter,"create");
                alertFragment.show(fm, "fragment_edit_name");
            }
        });
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int lastExpandedPosition = -1;
            @Override
            public void onGroupExpand(int i) {
                if(lastExpandedPosition != -1 && i != lastExpandedPosition){
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = i;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                String selected = expandableListAdapter.getChild(i,i1).toString();
                return true;
            }
        });
    }
    private void createCollection() {
        mobileCollection = new HashMap<String, List<Produit>>();
        for(Category group : groupList){
            mobileCollection.put(group.getName(), produitServices.produitsParCategory(group.getId()));
        }
    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Log.d("ah","noooooooo");


    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}