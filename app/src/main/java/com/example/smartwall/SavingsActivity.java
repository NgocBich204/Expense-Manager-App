package com.example.smartwall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

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
        adapter = new GoalAdapter(this, goalsList);
        listView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("goals");
        databaseReference.addValueEventListener(new ValueEventListener() {
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
                Toast.makeText(SavingsActivity.this, "Failed to retrieve goals", Toast.LENGTH_SHORT).show();
            }
        });

        Button buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SavingsActivity.this, createSavingActivity.class));
            }
        });

        ImageView imageViewBackToHome = findViewById(R.id.imageViewBackToHome);
        imageViewBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SavingsActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
