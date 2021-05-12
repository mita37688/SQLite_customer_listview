package com.example.listtravel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Travel";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Travels";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_students_table = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT)", TABLE_NAME, KEY_ID, KEY_NAME);
        db.execSQL(create_students_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_students_table = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(drop_students_table);
        onCreate(db);
    }

    public void addTravel(Travel travel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, travel.getName());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<Travel> getAllTravels() {
        List<Travel> travels = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false) {
            Travel travel = new Travel(cursor.getInt(0), cursor.getString(1));
            travels.add(travel);
            cursor.moveToNext();
        }
        return travels;
    }

    public void updateTravel(String name1, List<Travel> travels, int position) {
        String name = travels.get(position).getName();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name1);
        db.update(TABLE_NAME, values, KEY_NAME + " = ?", new String[] { name });
        db.close();
    }

    public void deleteTravel(List<Travel> travels, int position) {
        String name = travels.get(position).getName();
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_NAME + " = ?", new String[] { name });
        db.close();
    }
}
