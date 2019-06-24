package com.design.teaching_assistant.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Db extends SQLiteOpenHelper {
    private static final String name = "db";
    private static final int version = 1;
    public Db(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "user(user_id INTEGER primary key ,"+"user_name varchar(64),"+"user_role varchar(64),"+
                "user_password varchar(64))");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+"course(cousre_id INTEGER primary key,"+"course_teacher varchar(64))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            //修改表,暂时不需要
        }
    }

}
