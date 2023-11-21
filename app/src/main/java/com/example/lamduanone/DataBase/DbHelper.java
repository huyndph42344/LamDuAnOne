package com.example.lamduanone.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(@Nullable Context context) {
        super(context, "RUNFOOD", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String db_admin ="Create table Admin(maTT text primary key,taikhoan text,matkhau text)";
        sqLiteDatabase.execSQL(db_admin);
        String db_nhanvien ="Create table NhanVien(maTT text primary key,taikhoan1 text,matkhau1 text)";
        sqLiteDatabase.execSQL(db_nhanvien);
        String db_nguoidung ="Create table NguoiDung(maTT text primary key,taikhoan2 text,matkhau2 text, sdt integer)";
        sqLiteDatabase.execSQL(db_nguoidung);


        //thêm tt
        sqLiteDatabase.execSQL("INSERT INTO Admin VALUES(1,'admin','admin')");
        sqLiteDatabase.execSQL("INSERT INTO NhanVien VALUES(1,'huy','huy'),(2,'hung','123ab'),(3,'ab','cd')");
        sqLiteDatabase.execSQL("INSERT INTO NguoiDung VALUES(1,'huy','huy',0945249682),(2,'hung','123ab',0345313588),(3,'ab','cd',0364770278)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
