package com.example.smartwall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartwall.MainActivity;
import com.example.smartwall.R;
import com.example.smartwall.createSavingActivity;

public class SavingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving);

        Button buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang CreateSavingActivity khi nút "Thêm vào" được nhấn
                Intent intent = new Intent(SavingsActivity.this, createSavingActivity.class);
                startActivity(intent);
            }
        });

        // Xử lý khi nhấn vào ImageView để quay về trang chủ
        ImageView imageViewBackToHome = findViewById(R.id.imageViewBackToHome);
        imageViewBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để quay về MainActivity (hoặc trang chủ của bạn)
                Intent intent = new Intent(SavingsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Xóa các Activity trên đỉnh stack
                startActivity(intent);
                finish(); // Đóng Activity hiện tại
            }
        });
    }
}
