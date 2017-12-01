package com.example.christian.christian_pset5;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class WaitingFragment extends DialogFragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_waiting, container, false);

        final TextView waitingText = v.findViewById(R.id.waitingText);

        Log.d("test", waitingText.toString());

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        String url = "https://resto.mprog.nl/order";

        JSONObject to_send = new JSONObject();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, to_send, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            waitingText.setText("Your estimated waiting time is " + response.getString("preparation_time") + " minutes...");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        System.out.println("Error");

                    }
                });

        // Access the RequestQueue
        queue.add(jsObjRequest);
        // Inflate the layout for this fragment
        return v;
    }

}
