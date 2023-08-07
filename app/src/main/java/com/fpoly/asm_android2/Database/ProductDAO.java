package com.fpoly.asm_android2.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.fpoly.asm_android2.Models.SanPham;

import java.util.ArrayList;

public class ProductDAO {
    DBHelperProduct helperProduct;
    Context context;

    public ProductDAO(DBHelperProduct helperProduct, Context context) {
        this.helperProduct = helperProduct;
        this.context = context;
    }
    public void insertProduct(SanPham sanPham){
        SQLiteDatabase sql = helperProduct.getReadableDatabase();
        ContentValues pair = new ContentValues();
        pair.put("MaSP", sanPham.getMaSp());
        pair.put("TenSP", sanPham.getTenSp());
        pair.put("GiaSP", sanPham.getGiaSp());
        pair.put("SoLuong", sanPham.getSoLuong());

        long result = sql.insert("SanPham",null ,pair );
        if (result > 0){
            Toast.makeText(context, "Thành công !", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Không thành công", Toast.LENGTH_SHORT).show();
        }

    }

    public void updateProduct(SanPham sanPham){
        SQLiteDatabase sql = helperProduct.getReadableDatabase();
        ContentValues pair = new ContentValues();
        pair.put("MaSP", sanPham.getMaSp());
        pair.put("TenSP", sanPham.getTenSp());
        pair.put("GiaSP", sanPham.getGiaSp());
        pair.put("SoLuong", sanPham.getSoLuong());
// vì có thể có nhiều điều kiện lên cần truyền vào arrayString
        long result = sql.update("SanPham",pair,"MaSP = ?" ,new String[]{String.valueOf(sanPham.getMaSp())} );
        if (result > 0){
            Toast.makeText(context, "Thành công !", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Không thành công", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteProduct(SanPham sanPham){
        SQLiteDatabase sql = helperProduct.getWritableDatabase();
        long result = sql.delete("SanPham", "MaSP = ?" ,new String[]{String.valueOf(sanPham.getMaSp())} );
        if (result > 0){
            Toast.makeText(context, "Thành công !", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Không thành công", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<SanPham> getListproduct(){
        ArrayList<SanPham> list = new ArrayList<>();
        SQLiteDatabase sql = helperProduct.getReadableDatabase();
        sql.beginTransaction();
        try {
            Cursor cursor = sql.rawQuery("SELECT * FROM SanPham", null);
            if (cursor.getCount() > 0 ){
                cursor.moveToFirst();
                do {
                    int getMaSp = cursor.getInt(0);
                    String getTenSp = cursor.getString(1);
                    int getGiaSp = cursor.getInt(2);
                    int getSoLuong = cursor.getInt(3);
                    list.add(new SanPham(getMaSp, getTenSp, getGiaSp, getSoLuong));
                }while (cursor.moveToNext());
                sql.setTransactionSuccessful();
            }
        }catch (Exception e){

        }finally {
            sql.endTransaction();
        }

        return list;
    }
}
