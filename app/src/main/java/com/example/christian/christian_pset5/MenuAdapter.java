package com.example.christian.christian_pset5;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

// setting the adapter for menu items
public class MenuAdapter extends BaseAdapter {

    private Context mContext;
    private List<Dish> mProductList;

    public MenuAdapter(Context mContext, List<Dish> mProductlist) {
        this.mContext = mContext;
        this.mProductList = mProductlist;
    }

    @Override
    public int getCount() {
        return mProductList.size();
    }

    @Override
    public Object getItem(int i) {
        return mProductList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    // set all views correctly
    @Override
    public View getView(int i, final View view, ViewGroup viewGroup) {

        View v = view.inflate(mContext, R.layout.row_menu, null);

        TextView entryText = v.findViewById(R.id.menuEntryText);
        TextView priceText = v.findViewById(R.id.menuPriceText);
        TextView descText = v.findViewById(R.id.descText);

        entryText.setText(mProductList.get(i).getName());
        descText.setText(mProductList.get(i).getDescription());
        priceText.setText("€ " + Integer.toString(mProductList.get(i).getPrice()) + ",-");

        final ImageView imageView = v.findViewById(R.id.imageView);

        RequestQueue queue = Volley.newRequestQueue(mContext);
        // bron: https://www.programcreek.com/javi-api-examples/index.php?api=com.android.volley.toolbox.ImageRequest
        ImageRequest imageRequest = new ImageRequest(mProductList.get(i).getIcon(), new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {

                imageView.setImageBitmap(bitmap);
            }

        },
                0,
                0,
                null,
                Bitmap.Config.ALPHA_8,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("out");

                    }
                }
        );

        queue.add(imageRequest);

        return v;
    }
}
