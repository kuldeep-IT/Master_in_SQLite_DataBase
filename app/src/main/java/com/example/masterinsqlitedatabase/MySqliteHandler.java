package com.example.masterinsqlitedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.renderscript.ScriptGroup;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MySqliteHandler extends SQLiteOpenHelper {

    //Database version
    private static final int DATABASE_VERSION = 1;


    //Database name
    private static final String DATABASE_NAME = "computer.db";


    //Computer Table Name
    private static final String TABLE_COMPUTER = "computer";

    //computers table columns
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_COMPUTER_NAME = "computerName";
    private static final String COLUMN_COMPUTER_TYPE  = "computerType";


    String CREATE_COMPUTER_TABLE = "CREATE TABLE " + TABLE_COMPUTER + " (" + COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_COMPUTER_NAME +" TEXT, " + COLUMN_COMPUTER_TYPE + " TEXT" +");";

    public MySqliteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_COMPUTER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPUTER);
        onCreate(db);

    }

    //all database operations : CREATE,READ,UPDATE,DELETE

    //create
    public void addComputer(Computer computer)
    {
        SQLiteDatabase sqLiteDatabase = MySqliteHandler.this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COMPUTER_NAME,computer.getComputerName());
        values.put(COLUMN_COMPUTER_TYPE,computer.getComputerType());

        sqLiteDatabase.insert(TABLE_COMPUTER,null,values);
        sqLiteDatabase.close();
    }

    //getting single computer = READ

    public Computer getComputer(int id)
    {
        SQLiteDatabase database = MySqliteHandler.this.getReadableDatabase();

        //Cursor is a interface

        Cursor cursor = database.query(TABLE_COMPUTER, new String[]{COLUMN_ID,COLUMN_COMPUTER_NAME,COLUMN_COMPUTER_TYPE},
                                            COLUMN_ID + "=?", new String[] {String.valueOf(id)}, null,null,null);

        if (cursor != null)
        {
             cursor.moveToFirst();
        }

        Computer  computer = new Computer(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2));
        return computer;
    }

    public List<Computer> getAllComputerObjects()
    {

        List<Computer> computerList = new ArrayList<>();

        SQLiteDatabase database = MySqliteHandler.this.getReadableDatabase();

        String cursorQuery = "SELECT * FROM " + TABLE_COMPUTER;

        Cursor cursor = database.rawQuery(cursorQuery,null);

        if (cursor.moveToFirst())
        {
            do {


                Computer computer=new Computer();

                computer.setId(Integer.parseInt(cursor.getString(0)));
                computer.setComputerName(cursor.getString(1));
                computer.setComputerType(cursor.getString(2));

                computerList.add(computer);

            } while (cursor.moveToNext());
        }

        return computerList;
    }

    public int updateData(Computer computer)
    {

        SQLiteDatabase database = MySqliteHandler.this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_COMPUTER_NAME,computer.getComputerName());
        values.put(COLUMN_COMPUTER_TYPE,computer.getComputerType());

        return database.update(TABLE_COMPUTER, values , COLUMN_ID +" =? ",new String[]{String.valueOf(computer.getId())});

    }

    //deleting single computer
    public void deleteData(Computer computer)
    {
        SQLiteDatabase database = this.getWritableDatabase();

        database.delete(TABLE_COMPUTER, COLUMN_ID + " =? ",new String[]{String.valueOf(computer.getId())});

    }

    //count total computer
    public int countData()
    {
        SQLiteDatabase database =this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_COMPUTER;

        Cursor cursor = database.rawQuery(query,null);
        database.close();

        return cursor.getCount();
    }

}
