package com.example.smartwall.model;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smartwall.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExpensiveLog extends ComponentActivity {
    private DatabaseReference databaseExpenses;

    private ListView listViewExpenses;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> expenseList;


    private EditText editTextSearch;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_expenses);


        databaseExpenses = FirebaseDatabase.getInstance().getReference().child("expenses");

        expenseList = new ArrayList<>();
        listViewExpenses = findViewById(R.id.listViewHistory);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, expenseList);
        listViewExpenses.setAdapter(adapter);

        editTextSearch = findViewById(R.id.search);

        /*databaseExpenses.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                expenseList.clear(); // Xóa dữ liệu cũ

                // Lặp qua từng child node của "expenses"
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Expenses expense = snapshot.getValue(Expenses.class); // Chuyển đổi thành đối tượng Expense
                    String expenseInfo = expense.getTitle() + ": " + expense.getTotal(); // Tạo thông tin để hiển thị
                    expenseList.add(expenseInfo); // Thêm vào danh sách để hiển thị
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong quá trình đọc dữ liệu
            }
        });*/



        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String searchText = editable.toString().trim().toLowerCase();
                performSearch(searchText);
            }
        });
        loadExpensesFromFirebase();

    }
    private void loadExpensesFromFirebase() {
        databaseExpenses.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                expenseList.clear(); // Xóa dữ liệu cũ

                // Lặp qua từng child node của "expenses"
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Expenses expense = snapshot.getValue(Expenses.class); // Chuyển đổi thành đối tượng Expense
                    String expenseInfo = expense.getTitle() + ": " + expense.getTotal(); // Tạo thông tin để hiển thị
                    expenseList.add(expenseInfo); // Thêm vào danh sách để hiển thị
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong quá trình đọc dữ liệu
            }
        });
    }


    private void performSearch(String searchText) {
        ArrayList<String> filteredList = new ArrayList<>();

        // Lặp qua danh sách hiện tại và thêm vào filteredList các mục phù hợp với từ khóa tìm kiếm
        for (String expenseInfo : expenseList) {
            if (expenseInfo.toLowerCase().contains(searchText)) {
                filteredList.add(expenseInfo);
            }
        }

        // Cập nhật adapter để hiển thị danh sách đã lọc
        adapter.clear();
        adapter.addAll(filteredList);
        adapter.notifyDataSetChanged();
    }

}
