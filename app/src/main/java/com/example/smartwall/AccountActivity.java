package com.example.smartwall;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountActivity extends AppCompatActivity {

    private EditText etFullName, etEmail;
    private Button btnEditProfile, btnLogout;
    private FirebaseAuth mAuth;

    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "MyPrefs";
    private static final String IS_LOGGED_IN = "isLoggedIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Khởi tạo FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        // Khởi tạo SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Ánh xạ các view từ layout
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnLogout = findViewById(R.id.btnLogout);

        // Lấy thông tin người dùng hiện tại từ Firebase Authentication
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String fullName = currentUser.getDisplayName();
            String email = currentUser.getEmail();

            // Hiển thị thông tin người dùng lên giao diện
            etFullName.setText(fullName);
            etEmail.setText(email);
        }

        // Xử lý sự kiện khi nhấn nút "Chỉnh sửa thông tin"
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this, UpdateAccountActivity.class));
            }
        });

        // Xử lý sự kiện khi nhấn nút "Đăng xuất"
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Đăng xuất khỏi Firebase
                mAuth.signOut();

                // Cập nhật trạng thái đăng xuất trong SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(IS_LOGGED_IN, false);
                editor.apply();

                // Chuyển hướng về màn hình đăng nhập
                Intent intent = new Intent(AccountActivity.this, Login.class);
                startActivity(intent);
                finish(); // Đóng AccountActivity
            }
        });
    }
}