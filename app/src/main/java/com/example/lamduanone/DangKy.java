package com.example.lamduanone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lamduanone.DAO.NguoiDungDao;

public class DangKy extends AppCompatActivity {
    private NguoiDungDao nguoiDungDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        //ánh xạ
        EditText txttk = findViewById(R.id.txt_login);
        EditText txtmk = findViewById(R.id.txt_password);
        EditText txtRemk = findViewById(R.id.txt_Re_password);
        EditText txtsdt = findViewById(R.id.txt_sdt);
        Button btn_dangky = findViewById(R.id.btn_dangky);
        Button btn_back = findViewById(R.id.btn_back);


        nguoiDungDao = new NguoiDungDao(this);
//
        btn_dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = txttk.getText().toString().trim();
                String pass = txtmk.getText().toString().trim();
                String Re_pass = txtRemk.getText().toString().trim();
                String sdtString = txtsdt.getText().toString().trim();

                if (sdtString.isEmpty()) {
                    Toast.makeText(DangKy.this, "Hãy nhập số điện thoại", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Integer sdt = Integer.valueOf(sdtString);

                        if (!pass.equals(Re_pass)) {
                            Toast.makeText(DangKy.this, "Mật khẩu không trùng nhau!!", Toast.LENGTH_SHORT).show();
                        } else {
                            boolean check = nguoiDungDao.DangKi(user, pass, sdt);
                            if (check) {
                                Toast.makeText(DangKy.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(DangKy.this, "Hãy nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(DangKy.this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DangKy.this, DangNhap.class);
                startActivity(intent);
            }
        });

    }
}