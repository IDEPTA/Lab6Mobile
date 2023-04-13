package com.example.lab6;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.contentcapture.DataRemovalRequest;

import androidx.annotation.Nullable;

import java.util.LinkedList;

public class DBHelper extends SQLiteOpenHelper{
    private static final String MY_TABLE = "MyTable";
    private static final String ID = "id";
    private static final String NUM_KV = "numKv";
    private static final String WATER = "water";
    private static final String ENERGY = "energy";

    public DBHelper(@Nullable Context context) {
        super(context,  "appdb.db",  null,  1);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + MY_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NUM_KV + " INTEGER, " + WATER + " REAL, " + ENERGY + " REAL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void Delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MY_TABLE, ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void Add(Data data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NUM_KV, data.numKv);
        cv.put(WATER,data.water);
        cv.put(ENERGY,data.energy);
        db.insert(MY_TABLE,null,cv);
        db.close();
    }

    public LinkedList<Data> GetAll(){
        LinkedList<Data> list = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(MY_TABLE,null,null,null,null,null,null);

        if (cursor.moveToFirst()){
            do {
                int id_numkv = cursor.getColumnIndex(NUM_KV);
                int id_water = cursor.getColumnIndex(WATER);
                int id_energy = cursor.getColumnIndex(ENERGY);
                Data data = new Data(cursor.getInt(id_numkv),cursor.getFloat(id_water),cursor.getFloat(id_energy));
                list.add(data);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
    public boolean Update(int id, Data data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NUM_KV, data.getNumKv());
        contentValues.put(WATER, data.getWater());
        contentValues.put(ENERGY, data.getEnergy());
        int result = db.update(MY_TABLE, contentValues, ID + "=?", new String[]{String.valueOf(id)});
        return result > 0;
    }

}
