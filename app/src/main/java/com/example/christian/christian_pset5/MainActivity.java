package com.example.christian.christian_pset5;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends AppCompatActivity implements OrderFragment.OnFragmentInteractionListener {

    // start first fragment
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        CategoriesFragment fragment = new CategoriesFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment, "categories");
        ft.commit();
    }

    // inflate the options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // check if the action bar icon is pressed and show order
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.yourOrder:
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                OrderFragment fragment = new OrderFragment();
                fragment.show(ft, "dialog");
        }
        return super.onOptionsItemSelected(item);
    }

    // code for opening dialog fragments correctly
    @Override
    public void onFragmentInteraction() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        WaitingFragment fragment = new WaitingFragment();
        fragment.show(ft, "dialog");
    }
}
