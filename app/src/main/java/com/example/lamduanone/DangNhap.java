package com.example.lamduanone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lamduanone.DAO.NguoiDungDao;
import com.example.lamduanone.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class DangNhap extends AppCompatActivity {
    TextInputLayout w_username, w_password;
    TextView txt_credangki;
    Button btn_login;
    EditText txt_username, txt_password;
    CheckBox luuTK;
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        initUI();//lấy dữ liệu người dùng nhập
        btnLogin();//đăng nhập
        kiemTraCheckBox();//kiểm tra check Box
        txt_credangki = findViewById(R.id.txt_credangki);
        txt_credangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DangNhap.this, DangKy.class));
            }
        });
    }

    private void initUI() {
        btn_login = findViewById(R.id.btn_login);

        w_username = findViewById(R.id.w_username);
        w_password = findViewById(R.id.w_password);

        txt_username = findViewById(R.id.txt_username);
        txt_password = findViewById(R.id.txt_password);

        luuTK = findViewById(R.id.chk_rememberAccount);

    }

    private void btnLogin() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getUsname = txt_username.getText().toString().trim();
                String getPass = txt_password.getText().toString().trim();
                if (getUsname.isEmpty() || getPass.isEmpty()) {
                    if (getUsname.isEmpty()) {
                        w_username.setError("Không được để trống tài khoản!!!");

                    } else {
                        w_username.setErrorEnabled(false);
                    }
                    if (getPass.isEmpty()) {
                        w_password.setError("Không được để trống mật khẩu!!!");

                    } else {
                        w_password.setErrorEnabled(false);
                    }
                } else {
                    NguoiDungDao nguoiDungDao = new NguoiDungDao(DangNhap.this);
                    if (nguoiDungDao.KiemtraDangNhap(getUsname, getPass) ) {
                        // cần bổ sung sau
                        Toast.makeText(DangNhap.this, "Bạn đã đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        rememberAccount(luuTK.isChecked());
                        Intent intentt = new Intent(DangNhap.this, MainActivity.class);
                        intentt.putExtra("username", getUsname);

                        startActivity(intentt);
                        finish();
                    } else {
                        Toast.makeText(DangNhap.this, "Tài khoản mật khẩu chưa chính xác!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void rememberAccount(Boolean chkRemember) {
        SharedPreferences sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", txt_username.getText().toString().trim());
        editor.putString("password", txt_password.getText().toString().trim());
        editor.putBoolean("chkRemember", chkRemember);
        editor.apply();
    }

    private void kiemTraCheckBox() {
        SharedPreferences sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        password = sharedPreferences.getString("password", "");
        Boolean chkRemember = sharedPreferences.getBoolean("chkRemember", false);
        luuTK.setChecked(chkRemember);
        if (luuTK.isChecked()) {
            txt_username.setText(username);
            txt_password.setText(password);
        }
    }

}