package com.example.konachprojrct;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;


import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.konachprojrct.beans.Category;
import com.example.konachprojrct.beans.Client;
import com.example.konachprojrct.beans.Credit;
import com.example.konachprojrct.beans.Produit;
import com.example.konachprojrct.services.CategoryServices;
import com.example.konachprojrct.services.ClientService;
import com.example.konachprojrct.services.CreditService;
import com.example.konachprojrct.services.ProduitServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreditExpandableListAdapter extends BaseExpandableListAdapter implements AlertFragment.NoticeDialogListener{

    private Context context;
    private Map<String, List<Credit>> mobileCollection;
    private List<Client> groupList;
    private View vw;
    private ClientService clientService;
    private ProduitServices produitServices;
    private CreditService creditService;

    public CreditExpandableListAdapter(Context context, List<Client> groupList,
                                       Map<String, List<Credit>> mobileCollection, View vw){
        this.context = context;
        clientService = new ClientService(context);
        produitServices = new ProduitServices(context);
        creditService = new CreditService(context);
        this.mobileCollection = mobileCollection;
        this.groupList = groupList;
        this.vw=vw;
    }
    public List<Client> getList(){
        return groupList;
    }
    public void setList(List<Client> groupList){
        this.groupList=groupList;
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mobileCollection.get(groupList.get(i).getNom()).size();
    }

    @Override
    public Client getGroup(int i) {
        return groupList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return mobileCollection.get(groupList.get(i).getNom()).get(i1);
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
        String mobileName = getGroup(i).getPrenom()+" "+getGroup(i).getNom();
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.client_item_credit, null);
        }
        TextView name = view.findViewById(R.id.name);
        TextView numero = view.findViewById(R.id.numero);
        TextView totale = view.findViewById(R.id.tlt);
        ImageView menu = view.findViewById(R.id.menu);
        PopupMenu popup = new PopupMenu(context, menu); //you can use image button
        // as btnSettings on your GUI after
        //clicking this button pop up menu will be shown
        popup.getMenuInflater().inflate(R.menu.credit_menu, popup.getMenu());
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.show();
            }
        });


        name.setText(mobileName);
        numero.setText("Téle: "+getGroup(i).getTelephone()+"");
        totale.setText("Totale du credit: "+creditService.totalParClient(getGroup(i).getId())+" dh" );
        totale.setTextColor(Color.RED);
        return view;
    }
    @Override
    public View getChildView(final int i, final int i1, boolean b, View view, ViewGroup viewGroup) {
        Credit model = (Credit) getChild(i, i1);
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.produit_item_credit, null);
        }
        TextView prd = view.findViewById(R.id.prd);
        TextView totale = view.findViewById(R.id.totale);
        TextView etat = view.findViewById(R.id.etat);
        TextView date = view.findViewById(R.id.date);
        prd.setText("Produit: "+produitServices.findById(model.getIdProduit()).getLibelle()+" (x"+model.getQuantite()+")");
        etat.setText("Etat: "+model.getEtat());
        date.setText(model.getDate());
        totale.setText("Totale: "+model.getTotale()+"");
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
