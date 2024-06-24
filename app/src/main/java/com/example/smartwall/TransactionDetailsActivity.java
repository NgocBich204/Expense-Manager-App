package com.example.smartwall;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TransactionDetailsActivity extends AppCompatActivity {
Button buttonClose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_details);
        buttonClose = findViewById(R.id.button_close);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            // Kiểm tra và lấy giá trị id từ bundle
            int id = bundle.getInt("id", -1); // -1 là giá trị mặc định nếu không tìm thấy "id" trong bundle

            // Kiểm tra xem giá trị id đã được gán hay chưa
            if (id != -1) {
                // Sử dụng giá trị id ở đây
                Toast.makeText(TransactionDetailsActivity.this, "id: "+ id, Toast.LENGTH_SHORT).show();
//              lây dữ liệu tù Extras xong select dữ liệu từ fire base view

            } else {
                // Xử lý trường hợp không có giá trị id
                Log.e("TransactionDetailsActivity", "No id found in bundle");
            }
        } else {
            // Xử lý trường hợp không có bundle
            Log.e("TransactionDetailsActivity", "Intent extras (bundle) is null");
        }


    }
}