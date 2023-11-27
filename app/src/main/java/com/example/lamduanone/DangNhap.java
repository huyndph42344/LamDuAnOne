package com.example.lamduanone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lamduanone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DangNhap extends AppCompatActivity {

    TextView txt_credangki;
    Button btn_login;
    EditText txt_Email, txt_password;
    CheckBox luuTK;
    String username, password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        initUI();
        btnLogin();
        kiemTraCheckBox();
        txtDangKy();
        mAuth = FirebaseAuth.getInstance();
    }

    private void txtDangKy() {
        txt_credangki = findViewById(R.id.txt_credangki);
        txt_credangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DangNhap.this, DangKy.class);
                startActivity(intent);
            }
        });
    }

    private void initUI() {
        btn_login = findViewById(R.id.btn_login);
        txt_Email = findViewById(R.id.txt_email);
        txt_password = findViewById(R.id.txt_password);
        luuTK = findViewById(R.id.chk_rememberAccount);
    }

    private void btnLogin() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txt_Email.getText().toString();
                String password = txt_password.getText().toString();

                if (email.isEmpty()) {
                    txt_Email.setError("Bạn cần nhập vào Email!!");
                    txt_Email.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    txt_Email.setError("Vui lòng nhập Email hợp lệ");
                    txt_Email.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    txt_password.setError("Password is required!");
                    txt_password.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    txt_password.setError("Min password length should be 6 characters!");
                    txt_password.requestFocus();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(DangNhap.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    handleSignInSuccess();
                                } else {
                                    handleSignInFailure();
                                }
                            }
                        });
            }
        });
    }

    private void handleSignInSuccess() {
        if (txt_Email.getText().toString().equalsIgnoreCase("admin@gmail.com") && txt_password.getText().toString().equalsIgnoreCase("123456")) {
            Intent intent = new Intent(DangNhap.this, TrangChuAdmin.class);
            Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(DangNhap.this).toBundle();
            startActivity(intent, bundle);
        } else if (txt_Email.getText().toString().equalsIgnoreCase("NhanVien@gmail.com") && txt_password.getText().toString().equalsIgnoreCase("123456")) {
            Intent intent = new Intent(DangNhap.this, TrangChuNhanVien.class);
            Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(DangNhap.this).toBundle();
            startActivity(intent, bundle);
        } else {
            Toast.makeText(DangNhap.this, "Bạn đã đăng nhập thành công!!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(DangNhap.this, TrangChuUser.class);
            Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(DangNhap.this).toBundle();
            startActivity(intent, bundle);
        }
    }

    private void handleSignInFailure() {
        Toast.makeText(DangNhap.this, "Bạn đã đăng nhập thất bại, vui lòng kiểm tra lại!!!", Toast.LENGTH_LONG).show();
    }

    private void rememberAccount(Boolean chkRemember) {
        SharedPreferences sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", txt_Email.getText().toString().trim());
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
            txt_Email.setText(username);
            txt_password.setText(password);
        }
    }
}
