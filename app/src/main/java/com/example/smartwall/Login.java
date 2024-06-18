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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private EditText edtEmail, edtPass;
    private Button btnLogin;
    private TextView txtForget, txtSignUp;
    private ProgressBar progressBar;
    private FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fbAuth = FirebaseAuth.getInstance(); // Xác thực người dùng Firebase
        setContentView(R.layout.login);
        login();
    }

    private void login() {
        // Ánh xạ id
        edtEmail = findViewById(R.id.edtEmailLogin);
        edtPass = findViewById(R.id.edtPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        txtForget = findViewById(R.id.txtForgetPass);
        txtSignUp = findViewById(R.id.txtSignUp);
        progressBar = findViewById(R.id.progressBar);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(); // Ẩn bàn phím khi nhấn nút đăng nhập
                String email = edtEmail.getText().toString().trim();
                String pass = edtPass.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    edtEmail.setError("Email không được để trống!");
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    edtEmail.setError("Email không hợp lệ!");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    edtPass.setError("Mật khẩu không được để trống!");
                    return;
                }
                if (pass.length() < 6) {
                    edtPass.setError("Mật khẩu phải có ít nhất 6 ký tự!");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE); // Hiển thị thanh tiến trình

                fbAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE); // Ẩn thanh tiến trình
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Đăng nhập không thành công. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });

        txtForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ResetPWActivity.class));
            }
        });
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
