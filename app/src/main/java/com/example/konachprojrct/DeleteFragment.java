package com.example.konachprojrct;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.konachprojrct.beans.Category;
import com.example.konachprojrct.beans.Client;
import com.example.konachprojrct.beans.Credit;
import com.example.konachprojrct.beans.Produit;
import com.example.konachprojrct.services.ClientService;
import com.example.konachprojrct.services.CreditService;
import com.example.konachprojrct.services.ProduitServices;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeleteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeleteFragment extends Fragment {
    ProduitServices produitServices;
    ClientService clientService;
    CreditService creditService;
    List<Client> groupList;
    List<String> childList;
    Map<String, List<Credit>> mobileCollection;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DeleteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment deleteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DeleteFragment newInstance(String param1, String param2) {
        DeleteFragment fragment = new DeleteFragment();
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
        View view = inflater.inflate(R.layout.fragment_delete, container, false);

        produitServices = new ProduitServices(getContext());
        creditService = new CreditService(getContext());
        clientService = new ClientService(getContext());
        groupList = clientService.findAll();
        createCollection();
        expandableListView = view.findViewById(R.id.expCredit);
        expandableListAdapter = new CreditExpandableListAdapter(getContext(), groupList, mobileCollection,view);
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


        // Inflate the layout for this fragment
        return view;
    }
    private void createCollection() {
        mobileCollection = new HashMap<String, List<Credit>>();
        for(Client group : groupList){
            mobileCollection.put(group.getNom(), creditService.creditParClient(group.getId()));
        }
    }
}