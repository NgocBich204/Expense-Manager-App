package com.example.smartwall;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    private EditText edtEmail, edtPass, edtConfirmPass, edtFullName;
    private Button btnRegister;
    private ProgressBar progressBar;
    private FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        fbAuth = FirebaseAuth.getInstance();

        // Ánh xạ các view từ layout
        edtFullName = findViewById(R.id.edtFullName);
        edtEmail = findViewById(R.id.edtEmailRegister);
        edtPass = findViewById(R.id.edtPasswordRegister);
        edtConfirmPass = findViewById(R.id.edtConfirmPassword);
        btnRegister = findViewById(R.id.BtnRegister);
        progressBar = findViewById(R.id.progressBar);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(); // Ẩn bàn phím khi nhấn nút Đăng ký

                // Lấy thông tin từ các EditText
                String fullName = edtFullName.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String pass = edtPass.getText().toString().trim();
                String confirmPass = edtConfirmPass.getText().toString().trim();

                // Kiểm tra các trường nhập liệu
                if (TextUtils.isEmpty(fullName)) {
                    edtFullName.setError("Vui lòng nhập tên đầy đủ của bạn!");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    edtEmail.setError("Vui lòng nhập email!");
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    edtEmail.setError("Email không hợp lệ!");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    edtPass.setError("Vui lòng nhập mật khẩu!");
                    return;
                }
                if (pass.length() < 6) {
                    edtPass.setError("Mật khẩu phải có ít nhất 6 ký tự!");
                    return;
                }
                if (TextUtils.isEmpty(confirmPass)) {
                    edtConfirmPass.setError("Vui lòng nhập lại mật khẩu!");
                    return;
                }
                if (!pass.equals(confirmPass)) {
                    edtConfirmPass.setError("Mật khẩu xác nhận không khớp!");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE); // Hiển thị ProgressBar

                // Đăng ký người dùng vào Firebase Authentication
                fbAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE); // Ẩn ProgressBar khi xử lý xong

                                if (task.isSuccessful()) {
                                    Toast.makeText(Register.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Register.this, Login.class));
                                    finish();
                                } else {
                                    Toast.makeText(Register.this, "Đăng ký không thành công. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    // Phương thức để ẩn bàn phím khi không cần thiết
    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
