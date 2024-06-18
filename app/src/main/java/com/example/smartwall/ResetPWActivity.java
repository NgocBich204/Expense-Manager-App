package com.example.smartwall;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPWActivity extends AppCompatActivity {
    private EditText edtEmail;  // Khai báo biến cho EditText nhập email
    private Button btnSubmit;  // Khai báo biến cho nút Submit
    private TextView txtSignUp;  // Khai báo biến cho TextView đăng ký
    private FirebaseAuth fbAuth;  // Khai báo biến cho FirebaseAuth
    private ProgressBar progressBar;  // Khai báo biến cho ProgressBar

    private String strEmail;  // Khai báo biến lưu trữ email nhập vào

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_pwactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initializeUI();
    }

    private void initializeUI() {
        edtEmail = findViewById(R.id.edtEmailResetPW);
        btnSubmit = findViewById(R.id.btnSubmit);
        txtSignUp = findViewById(R.id.txtSignUp);
        progressBar = findViewById(R.id.progressBar);

        fbAuth = FirebaseAuth.getInstance();  // Khởi tạo FirebaseAuth

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strEmail = edtEmail.getText().toString().trim();  // Lấy email từ EditText
                if (TextUtils.isEmpty(strEmail)) {  // Kiểm tra nếu email trống
                    edtEmail.setError("Không được để email trống !");
                    return;
                }
                resetPassword();
            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Register.class));  // Chuyển sang màn hình đăng ký
            }
        });
    }

    private void resetPassword() {
        progressBar.setVisibility(View.VISIBLE);  // Hiển thị ProgressBar
        btnSubmit.setVisibility(View.GONE);  // Ẩn nút Submit trong quá trình gửi email

        fbAuth.sendPasswordResetEmail(strEmail)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ResetPWActivity.this, "Link thay đổi mật khẩu của bạn đã được gửi ! Vui lòng kiểm tra Email", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ResetPWActivity.this, Login.class);
                        startActivity(intent);
                        finish();  // Kết thúc Activity hiện tại
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ResetPWActivity.this, "Đã xảy ra lỗi !", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);  // Ẩn ProgressBar khi gặp lỗi
                        btnSubmit.setVisibility(View.VISIBLE);  // Hiển thị lại nút Submit khi gặp lỗi
                    }
                });
    }
}
