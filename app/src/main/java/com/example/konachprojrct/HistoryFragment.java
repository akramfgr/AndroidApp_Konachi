package com.example.konachprojrct;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.konachprojrct.beans.Credit;
import com.example.konachprojrct.beans.Produit;
import com.example.konachprojrct.services.CreditService;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {
    RecyclerView recyclerView;
    CreditService creditService;
    MaterialButtonToggleGroup materialButtonToggleGroup;
    EditText editText;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        creditService = new CreditService(getContext());
        recyclerView = view.findViewById(R.id.recyclerView);
        HistoAdapter adapter1 = new HistoAdapter(getContext(),(ArrayList<Credit>) creditService.findAll());
        recyclerView.setAdapter(adapter1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setVerticalFadingEdgeEnabled(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        editText = view.findViewById(R.id.input);
        materialButtonToggleGroup = view.findViewById(R.id.toogle);
        materialButtonToggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(isChecked && !s.equals("")){
                            switch (checkedId) {
                                case R.id.client:
                                    adapter1.setList((ArrayList<Credit>) creditService.searchByClient(s.toString()));
                                    break;
                                case R.id.produit:
                                    adapter1.setList((ArrayList<Credit>) creditService.searchByProdyuit(s.toString()));
                                    break;
                                case R.id.date:
                                    adapter1.setList((ArrayList<Credit>) creditService.searchByDate(s.toString()));
                            }
                        }else {
                            adapter1.setList((ArrayList<Credit>) creditService.findAll());
                        }
                    }
                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

            }
        });


        return view;
    }
}