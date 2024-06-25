package com.example.smartwall;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartwall.MainActivity;
import com.example.smartwall.R;
import com.example.smartwall.createSavingActivity;
import com.example.smartwall.model.Expenses;
import com.example.smartwall.model.Goal;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SavingsActivity extends AppCompatActivity {

    private ListView listView;
    private DatabaseReference databaseReference;
    private List<Goal> goalsList;
    private GoalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving);

        listView = findViewById(R.id.listView);
        goalsList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("goals");

        adapter = new GoalAdapter(this, goalsList);
        listView.setAdapter(adapter);

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


        databaseReference.child("goals").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                goalsList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Goal goal = postSnapshot.getValue(Goal.class);
                    goalsList.add(goal);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SavingsActivity.this, "Error fetching expense data!", Toast.LENGTH_SHORT).show();

            }
        });
    }
}