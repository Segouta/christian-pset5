package com.example.christian.christian_pset5;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends ListFragment {

    List<String> menuGroups = new ArrayList<String>();
    ArrayAdapter theAdapter;
    RestoDatabase restoDatabase;

    JSONArray group;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        String url = "https://resto.mprog.nl/menu";
        System.out.println("IN ON CREATE");
        theAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, menuGroups);
        this.setListAdapter(theAdapter);

        restoDatabase = RestoDatabase.getInstance(getActivity().getApplicationContext());

        Bundle arguments = this.getArguments();
        final String category = arguments.getString("category");

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            group = response.getJSONArray("items");
                            menuGroups.clear();
                            for (int i = 0; i < group.length(); i++){
                                if (group.getJSONObject(i).getString("category").equals(category)) {

                                    menuGroups.add(group.getJSONObject(i).getString("name"));
                                }

                            }
                            System.out.println(menuGroups);
                            theAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            System.out.println("did not work");
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        System.out.println("Error");

                    }
                });

        // Access the RequestQueue through your singleton class.
        queue.add(jsObjRequest);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        float price = 0;
        String name = l.getItemAtPosition(position).toString();
        try {
            for (int i = 0; i < group.length(); i++) {
                JSONObject item = group.getJSONObject(i);
                String sub = item.getString("name");
                if (sub.equals(name)) {
                    price = Float.valueOf(item.getString("price"));
                }

            }

        } catch (JSONException e) {
            System.out.println("That did not work...");
        }

        restoDatabase.addItem(name, price);
    }



}
