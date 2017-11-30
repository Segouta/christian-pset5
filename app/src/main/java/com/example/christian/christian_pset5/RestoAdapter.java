package com.example.christian.christian_pset5;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;


public class RestoAdapter extends ResourceCursorAdapter {

    public RestoAdapter(Context context, Cursor cursor) {
        super(context, R.layout.row_order, cursor, 0);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView entryText = view.findViewById(R.id.entryText);
        TextView priceText = view.findViewById(R.id.priceText);
        TextView amountText = view.findViewById(R.id.amountText);
        TextView totalPriceText = view.findViewById(R.id.totalPriceText);

        Integer title_id = cursor.getColumnIndex("name");
        Integer price_id = cursor.getColumnIndex("price");
        Integer amount_id = cursor.getColumnIndex("amount");

        String title_val = cursor.getString(title_id);
        String price_val = "€ " + cursor.getFloat(price_id);
        String amount_val = cursor.getString(amount_id) + " x";

        Float subtotal = Float.valueOf(cursor.getInt(amount_id)) * cursor.getFloat(price_id);

        totalPriceText.setText("€ " + subtotal.toString());
        entryText.setText(title_val);
        priceText.setText(price_val);
        amountText.setText(amount_val);

    }
}
