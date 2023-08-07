package com.fpoly.asm_android2.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelperProduct extends SQLiteOpenHelper {
    Context context;

    public DBHelperProduct(@Nullable Context context) {
        super(context, "sanpham.database", null, 1);
        this.context = context;
    }

    String createSanPham = "CREATE TABLE SanPham(MaSP INTEGER PRIMARY KEY, TenSP TEXT, GiaSP INTEGER, SoLuong INTEGER)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createSanPham);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
