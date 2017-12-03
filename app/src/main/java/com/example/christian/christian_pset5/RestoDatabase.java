package com.example.christian.christian_pset5;

import android.app.ActionBar;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// this is the database organiser
public class RestoDatabase extends SQLiteOpenHelper {

    private static RestoDatabase instance = null;

    private static final String TABLE_NAME = "orderDatabase";
    private static final String COL1 = "_id";
    private static final String COL2 = "name";
    private static final String COL3 = "price";
    private static final String COL4 = "amount";

    private RestoDatabase(Context context) {
        super(context, TABLE_NAME, null, 1);

    }

    // create the table
    @Override
    public void onCreate (SQLiteDatabase db){
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 + " TEXT, " + COL3 + " FLOAT," + COL4 + " INTEGER);";
        db.execSQL(createTable);

    }

    // make it singleton
    public static RestoDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new RestoDatabase(context);
        }
        return instance;
    }

    // check if exists
    @Override
    public void onUpgrade (SQLiteDatabase db,int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    // return order
    public Cursor getOrder() {
        SQLiteDatabase db = this.getWritableDatabase();
        String all = "SELECT * FROM " + TABLE_NAME;
        Cursor entries = db.rawQuery(all, null);
        return entries;
    }

    // clear all
    public void clear() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    // add item that is inputted
    public void addItem(String name, float price) {
        System.out.println("name: " + name);
        System.out.println(price);
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT 1 FROM " + TABLE_NAME + " WHERE " + COL2 + " = '" + name + "';", null);

        String command = "";
        if (cursor.getCount() == 0) {
            command = "INSERT INTO " + TABLE_NAME + "(" + COL2 + ", " + COL3 + ", " + COL4 + ") VALUES( '" + name + "', " + price + ", " + 1 + ");";
            System.out.println("Added new");
        }
        else {
            command = "UPDATE " + TABLE_NAME + " SET " + COL4 +  " = " + COL4 + " + 1 WHERE " + COL2 + " = '" + name + "';";
            System.out.println("Updated that shit");
        }
        db.execSQL(command);
    }

    // return total price for in order overview
    public String totalPrice() {
        int priceCol = 2;
        int amountCol = 3;
        SQLiteDatabase db = this.getWritableDatabase();
        String all = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(all, null);
        float total = 0;
        while (cursor.moveToNext()) {
            total = total + cursor.getFloat(priceCol) * cursor.getFloat(amountCol);
        }
        return "Total price: € " + total;
    }

    // delete certain item
    public void deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String command = "DELETE FROM " + TABLE_NAME + " WHERE " + COL1 + " = '" + id + "';";
        db.execSQL(command);
    }

}