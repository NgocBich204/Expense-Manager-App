package com.example.smartwall;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.smartwall.model.Expenses;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LogExpenses extends AppCompatActivity {
    private IncomeAdapter incomeAdapter;
    private ArrayList<Expenses> incomeList;
    private ListView listViewHistory;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.fragment_activity_log_expenses);

        listViewHistory = findViewById(R.id.listViewHistory);
        incomeList = new ArrayList<>();
        incomeAdapter = new IncomeAdapter(this, incomeList);
        listViewHistory.setAdapter(incomeAdapter);


        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("income").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                incomeList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Expenses income = dataSnapshot.getValue(Expenses.class);
                    if (income != null) {
                        try {
                            int incomeTotal = Integer.parseInt(income.getTotal());
                            incomeList.add(income);
                        } catch (NumberFormatException e) {
                        }
                    }
                }
                //totalIncomeTextView.setText(String.format("+ %,d VND", totalIncome));
                incomeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


    }
}