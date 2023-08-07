package com.fpoly.asm_android2.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.fpoly.asm_android2.Models.NguoiDung;

import java.util.ArrayList;

public class AccountDAO {
    DBHelper dbHelper;
    Context context;

    public AccountDAO(DBHelper dbHelper, Context context) {
        this.dbHelper = dbHelper;
        this.context = context;
    }

    public void insertNguoiDung(NguoiDung nguoiDung) {
        SQLiteDatabase sql = dbHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TenDangNhap", nguoiDung.getTenDangNhap());
        contentValues.put("MatKhau", nguoiDung.getMatKhau());
        contentValues.put("HoVaTen", nguoiDung.getHoVaTen());
        long kq = sql.insert("NguoiDung", null, contentValues);
        if (kq > 0)
            Toast.makeText(context, "Thành công!",
                    Toast.LENGTH_SHORT).show();
        else Toast.makeText(context, "Thất bại!",
                Toast.LENGTH_SHORT).show();
    }

    public void deleteNguoiDung(NguoiDung nguoiDung) {
        SQLiteDatabase sql = dbHelper.getReadableDatabase();
        long kq = sql.delete("NguoiDung", "TenDangNhap = ?", new String[]{nguoiDung.getTenDangNhap()});
        if (kq > 0)
            Toast.makeText(context, "Thành công!",
                    Toast.LENGTH_SHORT).show();
        else Toast.makeText(context, "Thất bại!",
                Toast.LENGTH_SHORT).show();
    }
    public void deleteAccount(String user) {
        SQLiteDatabase sql = dbHelper.getReadableDatabase();
        long kq = sql.delete("NguoiDung", "TenDangNhap = ?", new String[]{user});
        if (kq > 0)
            Toast.makeText(context, "Thành công!",
                    Toast.LENGTH_SHORT).show();
        else Toast.makeText(context, "Thất bại!",
                Toast.LENGTH_SHORT).show();
    }
    public void updateNguoiDung(NguoiDung nguoiDung) {
        SQLiteDatabase sql = dbHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TenDangNhap", nguoiDung.getTenDangNhap());
        contentValues.put("MatKhau", nguoiDung.getMatKhau());
        contentValues.put("HoVaTen", nguoiDung.getHoVaTen());
        long kq = sql.update("NguoiDung", contentValues, "TenDangNhap = ? ", new String[]{nguoiDung.getTenDangNhap()});
        if (kq > 0)
            Toast.makeText(context, "Thành công!",
                    Toast.LENGTH_SHORT).show();
        else Toast.makeText(context, "Thất bại!",
                Toast.LENGTH_SHORT).show();
    }

    public void updateForgotPasss(String userName, String passNew) {
        SQLiteDatabase sql = dbHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("MatKhau", passNew);

        long kq = sql.update("NguoiDung", contentValues, "TenDangNhap = ? ", new String[]{userName});
        if (kq > 0)
            Toast.makeText(context, "Thành công!",
                    Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Thất bại!",
                    Toast.LENGTH_SHORT).show();
    }

    public NguoiDung checkAccount() {
        NguoiDung nguoiDung = null;
        SQLiteDatabase sql = dbHelper.getReadableDatabase();
        sql.beginTransaction();
        try {
            Cursor cursor = sql.rawQuery("SELECT * FROM NguoiDung", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    String getTenDangNhap = cursor.getString(0);
                    String getMatKhau = cursor.getString(1);
                    String getHoVaTen = cursor.getString(2);
                 nguoiDung = new NguoiDung(getTenDangNhap, getMatKhau, getHoVaTen);
                } while (cursor.moveToNext());
                sql.setTransactionSuccessful();
            }
        } catch (Exception e) {

        } finally {
            sql.endTransaction();
        }
        return nguoiDung;
    }

    public ArrayList<NguoiDung> getList() {
        ArrayList<NguoiDung> list = new ArrayList<>();
        SQLiteDatabase sql = dbHelper.getReadableDatabase();
        sql.beginTransaction();
        try {
            Cursor cursor = sql.rawQuery("SELECT * FROM NguoiDung", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    String getTenDangNhap = cursor.getString(0);
                    String getMatKhau = cursor.getString(1);
                    String getHoVaTen = cursor.getString(2);
                    list.add(new NguoiDung(getTenDangNhap, getMatKhau, getHoVaTen));
                } while (cursor.moveToNext());
                sql.setTransactionSuccessful();
            }
        } catch (Exception e) {

        } finally {
            sql.endTransaction();
        }
        return list;
    }
}
