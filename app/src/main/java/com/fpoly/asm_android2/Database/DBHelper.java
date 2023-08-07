package com.fpoly.asm_android2.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    Context context;

    public DBHelper(@Nullable Context context) {
        super(context, "Account.database", null, 1);
        this.context = context;
    }

    String createAccount = "CREATE TABLE NguoiDung(TenDangNhap TEXT PRIMARY KEY, MatKhau TEXT, HoVaTen TEXT)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createAccount);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
