package com.example.smartwall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartwall.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateAccountActivity extends AppCompatActivity {

    private EditText etFullName, etEmail, etNewPassword;
    private Button btnSave, btnCancel;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updateacount);

        // Khởi tạo Firebase Authentication và Firebase Database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        // Ánh xạ các view từ layout
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etNewPassword = findViewById(R.id.etNewPassword);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        // Load dữ liệu người dùng hiện tại nếu có
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String fullName = currentUser.getDisplayName();
            String email = currentUser.getEmail();

            etFullName.setText(fullName);
            etEmail.setText(email);
        }

        // Xử lý sự kiện khi nhấn nút "Lưu thông tin"
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserData();
            }
        });

        // Xử lý sự kiện khi nhấn nút "Hủy"
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Quay trở lại activity trước đó
            }
        });
    }

    // Phương thức lưu thông tin người dùng
    private void saveUserData() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // Lấy thông tin từ các EditText
            String fullName = etFullName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String newPassword = etNewPassword.getText().toString().trim();

            // Cập nhật thông tin người dùng trong Firebase Authentication
            updateFirebaseAuth(currentUser, email, newPassword);

            // Cập nhật thông tin người dùng trong Firebase Realtime Database
            updateFirebaseDatabase(currentUser.getUid(), fullName, email);

            // Hiển thị thông báo và chuyển hướng về AccountActivity sau khi cập nhật thành công
            Toast.makeText(UpdateAccountActivity.this, "Thông tin đã được cập nhật", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UpdateAccountActivity.this, AccountActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }

    // Phương thức cập nhật thông tin trong Firebase Authentication
    private void updateFirebaseAuth(FirebaseUser user, String email, String newPassword) {
        if (!user.getEmail().equals(email)) {
            user.updateEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Email updated successfully
                            } else {
                                // Failed to update email
                                Toast.makeText(UpdateAccountActivity.this, "Cập nhật email thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        if (!newPassword.isEmpty()) {
            user.updatePassword(newPassword)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Password updated successfully
                            } else {
                                // Failed to update password
                                Toast.makeText(UpdateAccountActivity.this, "Cập nhật mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    // Phương thức cập nhật thông tin trong Firebase Realtime Database
    private void updateFirebaseDatabase(String userId, String fullName, String email) {
        User user = new User(fullName, email);
        mDatabase.child(userId).setValue(user);
    }
}
