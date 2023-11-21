package com.example.lamduanone.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lamduanone.DataBase.DbHelper;

public class NguoiDungDao {
    private DbHelper dbHelper;

    public NguoiDungDao(Context context) {
        dbHelper = new DbHelper(context);
    }

    public boolean KiemtraDangNhap(String taikhoan2, String matkhau2) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT *FROM NguoiDung WHERE taikhoan2=? AND taikhoan2=?", new String[]{taikhoan2, matkhau2});
        if (cursor.getCount() > 0) {
            return true;
        }
        return false;
    }
}
