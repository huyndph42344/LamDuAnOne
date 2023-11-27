package com.example.lamduanone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lamduanone.DAO.NguoiDungDao;
import com.example.lamduanone.MOdel.NguoiDung;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;

public class DangKy extends AppCompatActivity {
    private EditText edt_name;
    private EditText edt_email;
    private EditText edt_phone;
    private EditText edt_pass;
    private Button tv_login;
    private FirebaseAuth mAuth;
    public String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        edt_name = findViewById(R.id.edt_name);
        edt_phone = findViewById(R.id.edt_phone);
        edt_email = findViewById(R.id.edt_email);
        edt_pass = findViewById(R.id.edt_pass);
        tv_login = findViewById(R.id.btn_back);
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.btn_dangky).setOnClickListener(v ->{
            signup();
        });

        tv_login.setOnClickListener(v ->{
            Intent intent = new Intent(this, DangNhap.class);
            Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
            startActivity(intent, bundle);
        });

    }

    private void signup(){
        String fullName = edt_name.getText().toString();
        String email = edt_email.getText().toString();
        String phone = edt_phone.getText().toString();
        String password = edt_pass.getText().toString();
        String image = "";
        String gender = "";
        String date_of_birth = "";
        String address = "";

        if (fullName.isEmpty()) {
            edt_name.setError("Bạn chưa nhập tên");
            edt_name.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            edt_email.setError("Bạn chưa nhập email");
            edt_email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edt_email.setError("Bạn chưa nhập đúng định dạng email ");
            edt_email.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            edt_phone.setError("Ban chưa nhập số điện thoại");
            edt_phone.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            edt_pass.setError("Bạn chưa nhập mật khẩu");
            edt_pass.requestFocus();
            return;
        }

        if (password.length() < 6) {
            edt_pass.setError("Mật khẩu phải có tối thiểu 6 kí tự");
            edt_pass.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            NguoiDung nguoiDung = new NguoiDung(fullName, email,  phone, password, image, gender, date_of_birth, address);
                            FirebaseDatabase.getInstance().getReference("NguoiDung")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .child("NguoiDung")
                                    .setValue(nguoiDung).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(DangKy.this, "Bạn đã đăng ký thành công", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(DangKy.this, DangNhap.class);
                                                Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(DangKy.this).toBundle();
                                                startActivity(intent, bundle);
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(DangKy.this, "Đặng ký thất bại ,vui lòng thử lại!!", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}