package com.example.smartwall;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LogRevenue extends AppCompatActivity {
    private ExpenseAdapter expenseAdapter;
    private ArrayList<Expenses> expenseList;
    private ListView listViewExpense;
    private DatabaseReference databaseReference;
    private EditText search;
    private ArrayList<Expenses> filteredExpenseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.fragment_activity_log_revenue);
        search = findViewById(R.id.search);
        listViewExpense = findViewById(R.id.listViewHistory);
        expenseList = new ArrayList<>();
        expenseAdapter = new ExpenseAdapter(this, expenseList);
        listViewExpense.setAdapter(expenseAdapter);
        filteredExpenseList = new ArrayList<>();


        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("expenses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                expenseList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Expenses expense = dataSnapshot.getValue(Expenses.class);
                    if (expense != null) {
                        try {
                            int expenseTotal = Integer.parseInt(expense.getTotal());
                            expenseList.add(expense);
                        } catch (NumberFormatException e) {
                        }
                    }
                }
                //totalExpenseTextView.setText(String.format("- %,d VND", totalExpense));
                expenseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LogRevenue.this, "Error fetching expense data!", Toast.LENGTH_SHORT).show();
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // N/A
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Thực hiện truy vấn dữ liệu từ Firebase dựa trên văn bản tìm kiếm
                String searchText = s.toString().trim();
                if (searchText.isEmpty()) {
                    databaseReference.child("expenses").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            expenseList.clear();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Expenses expense = dataSnapshot.getValue(Expenses.class);
                                if (expense != null) {
                                    try {
                                        int expenseTotal = Integer.parseInt(expense.getTotal());
                                        expenseList.add(expense);
                                    } catch (NumberFormatException e) {
                                    }
                                }
                            }
                            //totalExpenseTextView.setText(String.format("- %,d VND", totalExpense));
                            expenseAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(LogRevenue.this, "Error fetching expense data!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    queryExpenses(searchText); // Nếu có văn bản tìm kiếm, thực hiện truy vấn Firebase
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // N/A
            }
        });



    }private void queryExpenses(String searchText) {
        Query query = databaseReference.child("expenses").orderByChild("category").startAt(searchText).endAt(searchText + "\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                expenseList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Expenses expense = dataSnapshot.getValue(Expenses.class);
                    if (expense != null) {
                        try {
                            int expenseTotal = Integer.parseInt(expense.getTotal());
                            expenseList.add(expense);
                        } catch (NumberFormatException e) {
                        }
                    }
                }
                expenseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LogRevenue.this, "Error fetching expense data!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}