package com.example.konachprojrct;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.konachprojrct.beans.Category;
import com.example.konachprojrct.services.CategoryServices;
import com.google.android.material.snackbar.Snackbar;


public class AlertFragment extends DialogFragment {
    private View sn;
    private Context context;
    private CategoryServices categoryServices;
    private EditText editText;
    private MyExpandableListAdapter ah;
    private String method;
    private Category category;
    public AlertFragment(View view,Context context,MyExpandableListAdapter ah,String method){
        this.sn = view;
        this.context=context;
        categoryServices = new CategoryServices(context);
        this.ah=ah;
        this.method=method;
    }
    public AlertFragment(View view,Context context,MyExpandableListAdapter ah,String method,Category category){
        this.sn = view;
        this.context=context;
        categoryServices = new CategoryServices(context);
        this.ah=ah;
        this.method=method;
        this.category=category;
    }
    public AlertFragment(){
    }
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    NoticeDialogListener listener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(e.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_alert, null);
        editText = view.findViewById(R.id.category_name);
        if(category != null)
            editText.setText(category.getName());



        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        if(method.equals("create")){
                            Category category = new Category(editText.getText().toString());
                            categoryServices.create(category);
                        }else if(method.equals("edit")){


                            category.setName(editText.getText().toString());
                            categoryServices.update(category);


                        }
                        Snackbar snackbar = Snackbar
                                .make(sn, "category bien ajouteé!", Snackbar.LENGTH_LONG);
                        TextView textView = sn.findViewById(com.google.android.material.R.id.snackbar_text);
                        View sbView = snackbar.getView();
                        sbView.setBackgroundColor(Color.BLUE);
                        //listener.onDialogPositiveClick(AlertFragment.this);
                        snackbar.show();
                        ah.setList(categoryServices.findAll());
                        ah.notifyDataSetChanged();

                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AlertFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }



}