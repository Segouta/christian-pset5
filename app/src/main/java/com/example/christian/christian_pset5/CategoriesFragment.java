package com.example.christian.christian_pset5;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends ListFragment {

    List<String> menuGroups = new ArrayList<String>();
    ArrayAdapter theAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setting adapter
        theAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, menuGroups);
        this.setListAdapter(theAdapter);

        // requesting info for categories
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        String url = "https://resto.mprog.nl/categories";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest

                (JsonObjectRequest.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray group = response.getJSONArray("categories");
                            menuGroups.clear();
                            for(int i = 0; i < group.length(); i++){
                                menuGroups.add(group.getString(i));

                            }
                            theAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            System.out.println("did not work");
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        System.out.println("Error");

                    }
                });

        // Access the RequestQueue through your singleton class.
        queue.add(jsObjRequest);

    }

    // when clicked, start next fragment
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        MenuFragment menuFragment = new MenuFragment();

        Bundle args = new Bundle();
        args.putString("category", l.getItemAtPosition(position).toString());
        menuFragment.setArguments(args);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, menuFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

}
