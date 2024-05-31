package com.example.smartwall;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    private EditText edtName, edtEmail ,edtPass ,edtRePass;
    private Button btnRegister;
    private TextView txtSignin;

    private ProgressDialog Dialog; //Khai báo thanh tiến trình
    //Firebase
    private FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        fbAuth = FirebaseAuth.getInstance(); //xác thực người dùng
        Dialog = new ProgressDialog(this);

        registration();
    }

    private void registration() {
    //Ánh xạ id
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmailRegister);
        edtPass = findViewById(R.id.edtPasswordRegister);
        edtRePass = findViewById(R.id.edtPasswordConfirm);
        btnRegister = findViewById(R.id.btnRegister);
        txtSignin = findViewById(R.id.txtSignIn);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString().trim();//loại bỏ tất cả khoảng trắng ở đầu và cuối của chuỗi văn bản.
                String email = edtEmail.getText().toString().trim();
                String pass = edtPass.getText().toString().trim();
                String repass = edtRePass.getText().toString().trim();
                //Kiểm tra xử lý đối tượng
                if (TextUtils.isEmpty(name)){
                    edtName.setError("Không được để tên trống !");
                    return;
                }
                if (TextUtils.isEmpty(email)){
                    edtEmail.setError("Không được để email trống !");
                    return;
                }
                if (TextUtils.isEmpty(pass)){
                    edtPass.setError("Không được để mật khấu trống !");
                    return;
                }
                if (TextUtils.isEmpty(repass)){
                    edtPass.setError("Bạn phải nhập lại mât khẩu !");
                    return;
                }
                if (!pass.equals(repass)) {
                    edtRePass.setError("Mật khẩu nhập lại không khớp!");
                    return;
                }
                Dialog.setMessage("Đang tải ...");
                Dialog.show();
                //đăng ký một người dùng mới
                fbAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Dialog.dismiss();//Hủy hộp thoại khi không còn cần thiết
                            Toast.makeText(getApplicationContext(), "Bạn đăng ký thành công ! ",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Homepage.class));
                        }
                        else {
                            Dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Bạn đăng ký không thành công .Vui lòng nhập lại ! ",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        //Phương thức Intent Nhấn vào Đăng nhập sẽ hiện lên bảng Main
        txtSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , MainActivity.class));

            }
        });
    }
}