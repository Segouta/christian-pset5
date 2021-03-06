package com.example.christian.christian_pset5;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends DialogFragment implements View.OnClickListener {

    RestoDatabase restoDatabase;
    private RestoAdapter restoAdapter;
    private ListView orderList;
    private TextView totalText;
    Button cancelButton, orderButton;
    private OnFragmentInteractionListener mListener;

    // set textviews
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_order, container, false);

        totalText = v.findViewById(R.id.totalText);
        orderList = v.findViewById(R.id.orderList);
        cancelButton = v.findViewById(R.id.cancelButton);
        orderButton = v.findViewById(R.id.orderButton);

        orderList.setOnItemLongClickListener(new ClickSomeLong());
        orderList.setOnItemClickListener(new ClickSome());
        cancelButton.setOnClickListener(this);
        orderButton.setOnClickListener(this);

        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        updateList();
    }

    // show alert when clicking
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancelButton:
                getDialog().dismiss();
                break;
            case R.id.orderButton:
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(getContext());
                }
                builder.setTitle("Are you sure?")
                        .setMessage("Are you sure you want to order this?")
                        .setPositiveButton("Yes, order!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                restoDatabase.clear();
                                getDialog().dismiss();
                                mListener.onFragmentInteraction();
                            }
                        })
                        .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_popup_reminder)
                        .show();

                break;
        }
    }

    // implements longclick
    private class ClickSomeLong implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            restoDatabase.deleteItem((int) id);
            updateList();
            return true;
        }
    }

    // clicking on items
    private class ClickSome implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(final AdapterView<?> parent, View view, int position, long id) {
            restoDatabase.deleteItem((int) id);
            updateList();
        }
    }

    // updating the list when something was added or deleted
    public void updateList() {
        restoDatabase = RestoDatabase.getInstance(getActivity().getApplicationContext());

        totalText.setText(restoDatabase.totalPrice());

        Cursor dish = restoDatabase.getOrder();
        ArrayList<String> orderArray = new ArrayList<String>();
        while (dish.moveToNext()) {
            orderArray.add(dish.getString(1));
        }

        restoAdapter = new RestoAdapter(getActivity(), dish);

        orderList.setAdapter(restoAdapter);

    }

    // setting the fragments
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
        else {
            System.out.println("Error");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }
}
