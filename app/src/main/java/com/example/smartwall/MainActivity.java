package com.example.smartwall;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartwall.model.Expenses;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Khai báo TAG để sử dụng cho việc debug
    private static final String TAG = "MainActivity";

    // Khai báo các biến cho các thành phần giao diện
    private Button btnAddrevenue, btnAddIncome;
    private ListView listViewIncome, listViewExpense;
    private TextView totalIncomeTextView, totalExpenseTextView;
    private IncomeAdapter incomeAdapter;
    private ExpenseAdapter expenseAdapter;
    private ArrayList<Expenses> incomeList;
    private ArrayList<Expenses> expenseList;
    private DatabaseReference databaseReference;

    private int totalIncome = 0; // Tổng thu nhập
    private int totalExpense = 0; // Tổng chi phí

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home);

        // Liên kết các thành phần giao diện với mã Java
        btnAddIncome = findViewById(R.id.btnAddIncome);
        btnAddrevenue = findViewById(R.id.btnAddrevenue);
        listViewIncome = findViewById(R.id.listViewIncome);
        listViewExpense = findViewById(R.id.listViewExpense);
        totalIncomeTextView = findViewById(R.id.totalIncomeTextView);
        totalExpenseTextView = findViewById(R.id.totalExpenseTextView);

        // Khởi tạo danh sách và adapter cho thu nhập và chi phí
        incomeList = new ArrayList<>();
        expenseList = new ArrayList<>();
        incomeAdapter = new IncomeAdapter(this, incomeList);
        expenseAdapter = new ExpenseAdapter(this, expenseList);

        listViewIncome.setAdapter(incomeAdapter);
        listViewExpense.setAdapter(expenseAdapter);

        // Lấy tham chiếu đến Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Lấy dữ liệu thu nhập từ Firebase
        databaseReference.child("evenue").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                incomeList.clear();
                totalIncome = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Expenses income = dataSnapshot.getValue(Expenses.class);
                    if (income != null) {
                        try {
                            int incomeTotal = Integer.parseInt(income.getTotal());
                            incomeList.add(income);
                            totalIncome += incomeTotal;
                        } catch (NumberFormatException e) {
                            Log.e(TAG, "Lỗi khi chuyển đổi tổng thu nhập: " + income.getTotal(), e);
                        }
                    }
                }
                Log.d(TAG, "Income list size: " + incomeList.size());
                totalIncomeTextView.setText(totalIncome + " VND");
                incomeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Lỗi khi lấy dữ liệu thu nhập!", Toast.LENGTH_SHORT).show();
            }
        });

        // Lấy dữ liệu khoản chi từ Firebase
        databaseReference.child("expenses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                expenseList.clear();
                totalExpense = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Expenses expense = dataSnapshot.getValue(Expenses.class);
                    if (expense != null) {
                        try {
                            int expenseTotal = Integer.parseInt(expense.getTotal());
                            expenseList.add(expense);
                            totalExpense += expenseTotal;
                        } catch (NumberFormatException e) {
                            Log.e(TAG, "Lỗi khi chuyển đổi tổng khoản chi: " + expense.getTotal(), e);
                        }
                    }
                }
                Log.d(TAG, "Expense list size: " + expenseList.size());
                totalExpenseTextView.setText(totalExpense + " VND");
                expenseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Lỗi khi lấy dữ liệu khoản chi!", Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý sự kiện khi nhấn nút "Thêm thu nhập"
        btnAddIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EvenueActivity.class);
                startActivity(intent);
            }
        });

        // Xử lý sự kiện khi nhấn nút "Thêm khoản chi"
        btnAddrevenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExpensesActivity.class);
                startActivity(intent);
            }
        });
    }
}
