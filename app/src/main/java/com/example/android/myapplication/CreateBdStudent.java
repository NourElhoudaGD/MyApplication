package com.example.android.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CreateBdStudent extends SQLiteOpenHelper {

    public static final String NOM_BDD = "Student.db";
    public static final String TABLE_Student = "table_students";
    public static final String COL_ID = "id";
    public static final String COL_name = "name";
    public static final String COL_surname = "surname";
    public static final String COL_marks = "marks";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_Student +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, surname TEXT NOT NULL, marks TEXT NOT NULL)";

    public CreateBdStudent(Context context) {
        super(context, NOM_BDD, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Student + ";");
        onCreate(db);
    }

    public boolean insertData(String name, String surname, String marks){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_name, name);
        contentValues.put(COL_surname, surname);
        contentValues.put(COL_marks, marks);
        float result = db.insert(TABLE_Student, null, contentValues);
        if (result == -1){
            return false;
        }else {
            return true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from "+TABLE_Student, null);
    }

    public boolean updateData(String id, String name, String surname, String marks){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ID, id);
        contentValues.put(COL_name, name);
        contentValues.put(COL_surname, surname);
        contentValues.put(COL_marks, marks);
        db.update(TABLE_Student, contentValues, "id = ?", new String[] { id });
        return true;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_Student,"id = ?", new String[] { id });
    }
}
