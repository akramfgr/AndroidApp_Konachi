package com.example.konachprojrct;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.konachprojrct.beans.Category;
import com.example.konachprojrct.beans.Produit;
import com.example.konachprojrct.services.CategoryServices;
import com.example.konachprojrct.services.ProduitServices;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyExpandableListAdapter extends BaseExpandableListAdapter implements AlertFragment.NoticeDialogListener{

    private Context context;
    private Map<String, List<Produit>> mobileCollection;
    private List<Category> groupList;
    private FragmentManager fm;
    private View vw;
    private CategoryServices categoryServices;
    private ProduitServices produitServices;

    public MyExpandableListAdapter(Context context,List<Category> groupList,
                                   Map<String, List<Produit>> mobileCollection,FragmentManager fm,View vw){
        this.context = context;
        categoryServices = new CategoryServices(context);
        produitServices = new ProduitServices(context);
        this.mobileCollection = mobileCollection;
        this.groupList = groupList;
        this.fm=fm;
        this.vw=vw;
    }
    public List<Category> getList(){
        return groupList;
    }
    public void setList(List<Category> groupList){
        this.groupList=groupList;
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mobileCollection.get(groupList.get(i).getName()).size();
    }

    @Override
    public Category getGroup(int i) {
        return groupList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return mobileCollection.get(groupList.get(i).getName()).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String mobileName = getGroup(i).getName();
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.group_item, null);
        }
        TextView item = view.findViewById(R.id.mobile);
        ImageView edit = view.findViewById(R.id.edit);
        ImageView add = view.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
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
                    if(c.getId()== getGroup(i).getId()){
                        idCat[0]=cat.indexOf(c);
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, catName);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                spinner.setSelection(idCat[0]);
                spinner.setEnabled(false);
                AlertDialog dialog = new AlertDialog.Builder(context).setTitle("Edit : ").setMessage("lorem ipsum").setView(popup).setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Produit produit = new Produit(libelle.getText().toString(),Double.parseDouble(prix.getText().toString()),getGroup(i).getId());
                        produitServices.create(produit);
                        mobileCollection.replace(getGroup(i).getName(),produitServices.produitsParCategory(getGroup(i).getId()));
                        notifyDataSetChanged();
                    }
                }).setNegativeButton("Annuler", null).create();
                dialog.show();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, getGroup(i).getId()+"", Toast.LENGTH_SHORT).show();

                AlertFragment alertFragment = new AlertFragment(vw,context,MyExpandableListAdapter.this,"edit",categoryServices.findById(getGroup(i).getId()));
                alertFragment.show(fm, "fragment_edit_name");

            }
        });
        item.setTypeface(null, Typeface.BOLD);
        item.setText(mobileName);
        item.setTextColor(Color.BLACK);
        return view;
    }
    @Override
    public View getChildView(final int i, final int i1, boolean b, View view, ViewGroup viewGroup) {
        Produit model = (Produit) getChild(i, i1);
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.child_item, null);
        }
        TextView item = view.findViewById(R.id.model);

        item.setText(model.getLibelle());
        item.setTextColor(Color.BLACK);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Log.d("ah","yeeeeeeees");
        notifyDataSetChanged();

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
    }

}
